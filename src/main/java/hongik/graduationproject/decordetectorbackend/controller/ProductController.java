package hongik.graduationproject.decordetectorbackend.controller;

import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.domain.SearchResult;
import hongik.graduationproject.decordetectorbackend.exception.ExternalApiBadConnectionException;
import hongik.graduationproject.decordetectorbackend.form.AddIkeaProductForm;
import hongik.graduationproject.decordetectorbackend.form.IdForm;
import hongik.graduationproject.decordetectorbackend.form.ProductForm;
import hongik.graduationproject.decordetectorbackend.form.SearchForm;
import hongik.graduationproject.decordetectorbackend.service.ProductService;
import hongik.graduationproject.decordetectorbackend.service.SearchingService;
import org.json.simple.parser.ParseException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ProductController {

    private final ProductService productService;
    private final SearchingService searchingService;

    public ProductController(ProductService productService, SearchingService searchingService) {

        this.productService = productService;
        this.searchingService = searchingService;
    }

    @PostMapping("/products/new")
    public Product createProduct(ProductForm productForm){
        Product product = new Product();
        product.setExternalId(productForm.getExternalId());
        product.setProductName(productForm.getProductName());
        product.setImage(productForm.getImage());
        product.setLink(productForm.getLink());
        try {
            productService.addProduct(product);
        }
        catch (MalformedURLException m){
            m.printStackTrace();
        }
        catch (ParseException p){
            p.printStackTrace();
        }
        catch (ExternalApiBadConnectionException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return product;
    }

    @PostMapping("/products/new/ikea")
    public List<String> addIkeaProducts(AddIkeaProductForm addIkeaProductForm){
        List<String> updatedList = new ArrayList<>();
        try {
            String category = addIkeaProductForm.getCategory();
            String start = addIkeaProductForm.getStart();
            String end = addIkeaProductForm.getEnd();

            updatedList = productService.updateAllProducts(category, start, end);
            return updatedList;
        }
        catch (MalformedURLException m){
            m.printStackTrace();
        }
        catch (ParseException p){
            p.printStackTrace();
        }
        catch (ExternalApiBadConnectionException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return updatedList;
    }

    @DeleteMapping("/products/one")
    public String deleteProduct(IdForm idFrom){
        productService.delete(idFrom.getId());
        return "삭제 성공";
    }

    @GetMapping("/products")
    public List<Product> productList(Model model){
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return products;
    }

    @PostMapping(value = "/products/similar", produces = "application/json; charset=UTF8")
    public SearchResult searchProducts(SearchForm form, BindingResult errors, Model model){
        System.out.println("Multipart FormData Binding Result: " + errors);
        SearchResult searchResult = searchingService.searchProduct(form);
        return searchResult;
    }

    @PostMapping("/products/clean")
    public List<Long> cleanSearchEngine(@RequestParam Long maxIndex){
        return productService.cleanIfDataNotExist(maxIndex);
    }
}
