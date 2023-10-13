package hongik.graduationproject.decordetectorbackend.domain;

import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.util.List;


@Document(indexName = "product")
//@Mapping(mappingPath = "elastic/product-mapping.json")
public class ProductDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Dense_Vector)
    private List<Float> imageVector;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Float> getImageVector() {
        return imageVector;
    }

    public void setImageVector(List<Float> imageVector) {
        this.imageVector = imageVector;
    }
}
