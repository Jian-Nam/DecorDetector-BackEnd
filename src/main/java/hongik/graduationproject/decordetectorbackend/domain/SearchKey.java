package hongik.graduationproject.decordetectorbackend.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SearchKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "key_Vector",
            joinColumns = @JoinColumn(name = "searchkey_id")
    )
    @OrderColumn(name = "vector_order")
    @Column(name = "vector_value")
    private List<Float> vector = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Float> getVector() {
        return vector;
    }

    public void setVector(List<Float> vector) {
        this.vector = vector;
    }
}
