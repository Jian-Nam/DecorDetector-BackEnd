package hongik.graduationproject.decordetectorbackend.domain;

public class ProductAndSimilarity {
    private Long id;
    private String name;
    private String image;
    private String link;
    private Float cosineSimilarity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Float getCosineSimilarity() {
        return cosineSimilarity;
    }

    public void setCosineSimilarity(Float cosineSimilarity) {
        this.cosineSimilarity = cosineSimilarity;
    }
}
