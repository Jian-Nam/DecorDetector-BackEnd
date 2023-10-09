package hongik.graduationproject.decordetectorbackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


public interface SimilarProduct {
    Long getId();
    String getName();
    String getImage();
    String getLink();
    String getCosineSimilarity();
}
