package hongik.graduationproject.decordetectorbackend.repository;

import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.domain.SimilarProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaProductRepository extends JpaRepository<Product, Long>, ProductRepository {
    @Override
    Optional<Product> findByName(String name);
    @Override
    Optional<Product> findByExternalId(String id);

}
