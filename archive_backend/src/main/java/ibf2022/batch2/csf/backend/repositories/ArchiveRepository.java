package ibf2022.batch2.csf.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.Bundle;

@Repository
public class ArchiveRepository {

	private static final String MONGO_COLLECTION = "archives";

	@Autowired
	private MongoTemplate mongoTemplate;

	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//	db.archives.insert({
	// 	bundleId: <bundleId>, 
	// 	date: <upload date>,
	//	title: <title>,
	//	name: <name>,
	//	comments: <comments>,
	// 	urls: [<URL of image>]
	// 	})
	public String recordBundle(Bundle bundle) {
		this.mongoTemplate.insert(bundle, MONGO_COLLECTION);
		return bundle.getBundleId();
	}

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	// db.archives.find({bundleId:"986a3e9d"})
	public Optional<Bundle> getBundleByBundleId(String bundleId) {
		Query query = Query.query(Criteria.where("bundleId").is(bundleId));
        List<Bundle> bundles = mongoTemplate.find(query, Bundle.class, MONGO_COLLECTION);
		if (bundles == null || bundles.size()<1) {
            return Optional.empty();
        }
		String date = bundles.get(0).getDate();
		bundles.get(0).setDate(date.substring(0, 10));
		System.out.println(bundles.get(0).getDate());
        return Optional.of(bundles.get(0));
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Object getBundles(/* any number of parameters here */) {
		return null;
	}


}
