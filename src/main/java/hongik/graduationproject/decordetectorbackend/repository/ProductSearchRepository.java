package hongik.graduationproject.decordetectorbackend.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import hongik.graduationproject.decordetectorbackend.domain.ProductAndSimilarity;
import hongik.graduationproject.decordetectorbackend.domain.ProductDocument;

import java.util.List;

public interface ProductSearchRepository {
    ProductDocument save(ProductDocument productDocument);
    List<ProductAndSimilarity> getByImageVector(List<Float> imageVector) throws Exception;
}
