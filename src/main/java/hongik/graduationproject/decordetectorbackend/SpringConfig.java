package hongik.graduationproject.decordetectorbackend;

import hongik.graduationproject.decordetectorbackend.repository.ProductRepository;
import hongik.graduationproject.decordetectorbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final ProductRepository productRepository;

    @Autowired
    public SpringConfig(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Bean
    public ProductService productService(){
        return new ProductService(productRepository);
    }


}
