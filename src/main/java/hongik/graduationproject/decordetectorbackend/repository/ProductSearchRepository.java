package hongik.graduationproject.decordetectorbackend.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import hongik.graduationproject.decordetectorbackend.domain.ProductAndSimilarity;
import hongik.graduationproject.decordetectorbackend.domain.ProductDocument;

import java.util.List;
import java.util.Optional;

public interface ProductSearchRepository {
    ProductDocument save(ProductDocument productDocument);
    Optional<ProductDocument> findById(Long Id);
    void delete(Long id);
    List<ProductAndSimilarity> getByImageVector(List<Float> imageVector) throws Exception;
}
