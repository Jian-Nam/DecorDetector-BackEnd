package hongik.graduationproject.decordetectorbackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


public class SimilarProduct {


    private Long id;

    private String name;

    private String image;

    private String link;
    private Float cosineSimilarity;

    public SimilarProduct(Long id, String name, String image, String link, Float cosineSimilarity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.link = link;
        this.cosineSimilarity = cosineSimilarity;
    }

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
