package ibf2022.batch2.csf.backend.models;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Bundle {
    
    private String bundleId;
    private String date;
    private String title;
    private String name;
    private String comments;
    private List<String> urls;
    
    public Bundle() {
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> ulrs) {
        this.urls = ulrs;
    }

    public static JsonObject toJSON(Bundle bundle) {
        JsonArrayBuilder jarrBuilder = Json.createArrayBuilder();
        for (String url : bundle.getUrls()) {
            jarrBuilder.add(url);
        }
        JsonArray urls = jarrBuilder.build();
        return Json.createObjectBuilder()
                    .add("bundleId",bundle.getBundleId())
                    .add("date",bundle.getDate().substring(0, 10))
                    .add("title",bundle.getTitle())
                    .add("name",bundle.getName())
                    .add("comments",bundle.getComments())
                    .add("urls",urls)
                    .build();
    }
}
