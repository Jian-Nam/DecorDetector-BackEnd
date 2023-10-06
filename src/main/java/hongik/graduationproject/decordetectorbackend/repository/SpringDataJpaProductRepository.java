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

    @Query(
            value = "with input_vector as(\n" +
                    "    select * from vector WHERE product_id=5\n" +
                    ")\n" +
                    "SELECT\n" +
                    "    A.product_id as id,\n" +
                    "    P.name as name,\n" +
                    "    P.image as image,\n" +
                    "    P.link as link,\n" +
                    "    SUM(A.vector_value * B.vector_value) / (SQRT(SUM(A.vector_value * A.vector_value)) * SQRT(SUM(B.vector_value * B.vector_value))) AS cosine_similarity\n" +
                    "FROM vector as A\n" +
                    "JOIN input_vector as B\n" +
                    "    on (A.vector_order = B.vector_order)\n" +
                    "JOIN product as P\n" +
                    "    on (A.product_id = P.id)\n" +
                    "group by A.product_id\n" +
                    "order by cosine_similarity desc",
            nativeQuery = true)
    List<SimilarProduct> findBySimilarity();

}
