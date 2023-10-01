package hongik.graduationproject.decordetectorbackend.service;


import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.repository.ProductRepository;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Long addProduct(Product product){
        product.setVector( convertToVector(product.getLink()));
        productRepository.save(product);
        return product.getId();
    }

    private List<Float> convertToVector(String imagelink){
        List<Float> vector1 = new ArrayList<Float>();
        vector1.add(3.14F);
        vector1.add(8.05F);
        vector1.add(5.29F);
        vector1.add(3.28F);
        return vector1;
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> findProduct(Long productId){
        return productRepository.findById(productId);
    }
}
