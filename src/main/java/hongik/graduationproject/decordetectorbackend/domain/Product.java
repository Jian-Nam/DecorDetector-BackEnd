package hongik.graduationproject.decordetectorbackend.domain;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "external_id")
    private Long externalId;
    @Column
    private String name;
    @Column
    private String image;
    @Column
    private String link;
    @ElementCollection
    @CollectionTable(
            name = "vector",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @OrderColumn
    @Column(name = "vector_value")
    private List<Float> vector = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExternalId() { return externalId; }

    public void setExternalId(Long externalId) { this.externalId = externalId; }

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

    public List<Float> getVector() {
        return vector;
    }

    public void setVector(List<Float> vector) {
        this.vector = vector;
    }
}
