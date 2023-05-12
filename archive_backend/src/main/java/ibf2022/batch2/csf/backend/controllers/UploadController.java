package ibf2022.batch2.csf.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.services.MongoService;
import ibf2022.batch2.csf.backend.services.S3Service;

@Controller
@RequestMapping
public class UploadController {

	@Autowired
	private S3Service s3Svc;

	@Autowired
	private MongoService mongoSvc;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path="/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> upload(
		@RequestPart String name,
		@RequestPart String title,
		@RequestPart String comments,
		@RequestPart MultipartFile archive
	) { 
		try {
			List<String> imageList = s3Svc.unzipAndUpload(archive, name, title, comments);
			mongoSvc.archiveIntoMongo(imageList, name, title, comments);
		} catch (Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Good");
	}

	// TODO: Task 5
	

	// TODO: Task 6

}
