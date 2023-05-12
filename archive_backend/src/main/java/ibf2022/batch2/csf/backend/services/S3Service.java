package ibf2022.batch2.csf.backend.services;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.CustomMultipartFile;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;


@Service
public class S3Service {

    @Autowired
    private ImageRepository imageRepo;
    
    public List<String> unzipAndUpload(MultipartFile file, String name, String title, String comments) throws IOException {
        List<String> imageList = new LinkedList<>();
        ZipInputStream zipIn = new ZipInputStream(file.getInputStream());
        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            System.out.println(entry);
            MultipartFile multipartFile = this.convert(entry, zipIn);
            String image = imageRepo.upload(multipartFile, name, title, comments);
            imageList.add(image);
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        return imageList;
    }

    public MultipartFile convert(ZipEntry zipEntry, ZipInputStream zipInputStream) throws IOException {
        String[] parts = zipEntry.getName().split("\\.");
        String extension = parts.length > 1 ? parts[parts.length - 1] : "";
        String contentType = "";
        switch (extension) {
            case "jpg":
            case "jpeg":
                contentType = "image/jpeg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "pdf":
                contentType = "image/gif";
                break;
        }
        byte[] bytes = new byte[(int) zipEntry.getSize()];
        return new CustomMultipartFile(zipEntry.getName(), zipEntry.getName(),
                contentType, bytes);
    }

}
