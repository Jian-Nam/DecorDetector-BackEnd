package hongik.graduationproject.decordetectorbackend.repository;

import hongik.graduationproject.decordetectorbackend.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaProductRepository extends JpaRepository<Product, Long>, ProductRepository {
    @Override
    Optional<Product> findByName(String name);

}
