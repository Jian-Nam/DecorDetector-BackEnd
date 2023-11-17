package hongik.graduationproject.decordetectorbackend.service;


import hongik.graduationproject.decordetectorbackend.client.AiApiClient;
import hongik.graduationproject.decordetectorbackend.client.IkeaClient;
import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.domain.ProductDocument;
import hongik.graduationproject.decordetectorbackend.exception.ExternalApiBadConnectionException;
import hongik.graduationproject.decordetectorbackend.repository.ProductRepository;
import hongik.graduationproject.decordetectorbackend.repository.ProductSearchRepository;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;
    private final IkeaClient ikeaClient;
    private final AiApiClient aiApiClient;

    public ProductService(ProductRepository productRepository, ProductSearchRepository productSearchRepository, IkeaClient ikeaClient, AiApiClient aiApiClient) {

        this.productRepository = productRepository;
        this.ikeaClient = ikeaClient;
        this.aiApiClient = aiApiClient;
        this.productSearchRepository = productSearchRepository;
    }

    public Long addProduct(Product product) throws ExternalApiBadConnectionException, ParseException, MalformedURLException{
        URL url = new URL(product.getImage());
        Resource resource = new UrlResource(url);
        Product savedProduct = productRepository.save(product);

        ProductDocument productDocument = new ProductDocument();
        productDocument.setId(savedProduct.getId());

        productDocument.setImageVector(aiApiClient.convertToVector(resource));
        ProductDocument savedDocument = productSearchRepository.save(productDocument);
        System.out.println(savedDocument.getId());

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
        productSearchRepository.delete(id);
    }
    public List<Long> cleanIfDataNotExist(Long maxIndex){
        List<Long> cleanedId = new ArrayList<>();
        for(long i= 0L; i<maxIndex; i++){
            if(productRepository.findById(i).isEmpty()) {
                if(productRepository.findById(i).isPresent()) {
                    productSearchRepository.delete(i);
                    cleanedId.add(i);
                }
            }
        }
        return cleanedId;
    }

    public List<String> updateAllProducts(String category, String start, String end)throws MalformedURLException, ParseException, ExternalApiBadConnectionException{
        List<Product> productList = ikeaClient.getProductData(category, start, end);
        List<String> updatedList = updateByList(productList);
        return updatedList;
    }
    private List<String> updateByList(List<Product> productList)throws MalformedURLException, ParseException, ExternalApiBadConnectionException {
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
