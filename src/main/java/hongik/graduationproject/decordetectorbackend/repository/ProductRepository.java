package hongik.graduationproject.decordetectorbackend.repository;

import hongik.graduationproject.decordetectorbackend.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    Optional<Product> findByExternalId(String id);
    Optional<Product> findByProductName(String productName);
    List<Product> findAll();
    void deleteById(Long id);
}
