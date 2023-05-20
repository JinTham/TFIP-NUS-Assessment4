package ibf2022.batch2.csf.backend.repositories;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import ibf2022.batch2.csf.backend.repositories.ImageRepository;

@Repository
public class ImageRepository {

	@Autowired
    private AmazonS3 s3Client;

	@Value("${DO_STORAGE_BUCKETNAME}")
    private String bucketName;

	@Value("${DO_STORAGE_ENDPOINT}")
    private String endpoint;


	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
	public String upload(MultipartFile file, String name, String title, String comments) throws IOException {
        // prepare userData
        Map<String, String> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("uploadDateTime", LocalDateTime.now().toString());
        userData.put("originalFilename", file.getOriginalFilename());
        userData.put("title", title);
        userData.put("comments", comments);
        // prepare metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userData);  
        // Use metadata and key to create PutObjectRequest
        PutObjectRequest putRequest = new PutObjectRequest(bucketName, file.getOriginalFilename(), new ByteArrayInputStream(file.getBytes()), metadata);
        putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        s3Client.putObject(putRequest);
		return "https://"+bucketName+"."+endpoint+"/"+file.getOriginalFilename();
	}

}
