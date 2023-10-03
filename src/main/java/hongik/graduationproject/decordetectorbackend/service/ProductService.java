package hongik.graduationproject.decordetectorbackend.service;


import hongik.graduationproject.decordetectorbackend.client.AiApiClient;
import hongik.graduationproject.decordetectorbackend.client.IkeaClient;
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
    private final IkeaClient ikeaClient;
    private final AiApiClient aiApiClient;

    public ProductService(ProductRepository productRepository, IkeaClient ikeaClient, AiApiClient aiApiClient) {

        this.productRepository = productRepository;
        this.ikeaClient = ikeaClient;
        this.aiApiClient = aiApiClient;
    }

    public Long addProduct(Product product){
        product.setVector( aiApiClient.convertToVector(product.getLink()));
        productRepository.save(product);
        return product.getId();
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> findProduct(Long productId){
        return productRepository.findById(productId);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }

    public List<Long> updateAllProducts(String category, String start, String end){
        List<Product> productList = ikeaClient.getProductData(category, start, end);
        List<Long> updatedList = updateByList(productList);
        return updatedList;
    }
    public List<Long> updateByList(List<Product> productList){
        List<Long> updatedList = new ArrayList<>();

        for(Product product: productList){
            Long externalId = product.getExternalId();

            if(productRepository.findByExternalId(externalId).isEmpty()){
                addProduct(product);
                updatedList.add(externalId);
            }
        }
        return updatedList;
    }
}
