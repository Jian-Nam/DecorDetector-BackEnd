package hongik.graduationproject.decordetectorbackend.service;


import hongik.graduationproject.decordetectorbackend.client.AiApiClient;
import hongik.graduationproject.decordetectorbackend.client.IkeaClient;
import hongik.graduationproject.decordetectorbackend.controller.SearchForm;
import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.domain.SearchResult;
import hongik.graduationproject.decordetectorbackend.repository.ProductRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

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
        try {
            URL url = new URL(product.getImage());
            Resource resource = new UrlResource(url);
            product.setVector(aiApiClient.convertToVector(resource));
            productRepository.save(product);
        }catch (Exception e){
            System.out.println("이미지 벡터화 실패");
            e.printStackTrace();
        }
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

    public List<String> updateAllProducts(String category, String start, String end){
        List<Product> productList = ikeaClient.getProductData(category, start, end);
        List<String> updatedList = updateByList(productList);
        return updatedList;
    }
    private List<String> updateByList(List<Product> productList){
        List<String> updatedList = new ArrayList<>();

        for(Product product: productList){
            String externalId = product.getExternalId();

            if(productRepository.findByExternalId(externalId).isEmpty()){
                addProduct(product);
                updatedList.add(externalId);
            }
        }
        return updatedList;
    }



}
