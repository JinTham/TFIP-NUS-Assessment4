package ibf2022.batch2.csf.backend.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Bundle;
import ibf2022.batch2.csf.backend.services.MongoService;
import ibf2022.batch2.csf.backend.services.S3Service;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

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
	) throws IOException { 
		try {
			List<String> imageUrlList = s3Svc.unzipAndUpload(archive, name, title, comments);
			String bundleId = mongoSvc.archiveIntoMongo(imageUrlList, name, title, comments);
			JsonObject resp = Json.createObjectBuilder()
							.add("bundleId",bundleId)
							.build();
			return ResponseEntity.status(HttpStatus.CREATED)
								.contentType(MediaType.APPLICATION_JSON)
								.body(resp.toString());
		} catch (Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
		}
	}

	// TODO: Task 5
	@GetMapping(path="/bundle/{bundleId}")
	public ResponseEntity<String> getBundle(@PathVariable String bundleId) {
		try {
			Optional<Bundle> opt = mongoSvc.getBundle(bundleId);
			if (opt.isEmpty()){
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
									.contentType(MediaType.APPLICATION_JSON)
									.body("Bundle ID not found");
			}
			Bundle bundle = opt.get();
			return ResponseEntity.status(HttpStatus.OK)
								.contentType(MediaType.APPLICATION_JSON)
								.body(Bundle.toJSON(bundle).toString());
		} catch (Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
		}
	}

	// TODO: Task 6
	@GetMapping(path="/bundles")
	public ResponseEntity<String> getAllBundles() {
		try {
			Optional<List<Bundle>> opt = mongoSvc.getAllBundles();
			if (opt.isEmpty()){
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
									.contentType(MediaType.APPLICATION_JSON)
									.body("No bundle found");
			}
			List<Bundle> bundles = opt.get();
			JsonArrayBuilder jarrBuilder = Json.createArrayBuilder();
			for (Bundle bundle : bundles) {
				jarrBuilder.add(Bundle.toJSON(bundle));
			}
			JsonArray results = jarrBuilder.build();
			return ResponseEntity.status(HttpStatus.OK)
								.contentType(MediaType.APPLICATION_JSON)
								.body(results.toString());
		} catch (Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
		}
	}

}
