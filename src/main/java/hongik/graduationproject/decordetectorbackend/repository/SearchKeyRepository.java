package hongik.graduationproject.decordetectorbackend.repository;

import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.domain.SearchKey;
import hongik.graduationproject.decordetectorbackend.domain.SimilarProduct;

import java.util.List;
import java.util.Optional;

public interface SearchKeyRepository {
    SearchKey save(SearchKey searchKey);
    void deleteById(Long id);
    List<SimilarProduct> findBySimilarity(Long id);
}
