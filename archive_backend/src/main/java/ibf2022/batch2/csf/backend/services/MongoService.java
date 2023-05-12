package ibf2022.batch2.csf.backend.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.batch2.csf.backend.models.Bundle;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;

@Service
public class MongoService {
    
    @Autowired
    private ArchiveRepository archiveRepo;

    public String archiveIntoMongo(List<String> imageUrlList, String name, String title, String comments) {
        Bundle bundle = new Bundle();
        bundle.setBundleId(UUID.randomUUID().toString().substring(0, 8));
        bundle.setDate(LocalDateTime.now().toString());
        bundle.setTitle(title);
        bundle.setName(name);
        bundle.setComments(comments);
        bundle.setUrls(imageUrlList);
        return this.archiveRepo.recordBundle(bundle);
    }

    public Optional<Bundle> getBundle(String bundleId) {
        return this.archiveRepo.getBundleByBundleId(bundleId);
    }

}
