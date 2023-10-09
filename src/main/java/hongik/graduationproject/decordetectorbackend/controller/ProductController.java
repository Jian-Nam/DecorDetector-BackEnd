package hongik.graduationproject.decordetectorbackend.controller;

import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.domain.SearchResult;
import hongik.graduationproject.decordetectorbackend.service.ProductService;
import hongik.graduationproject.decordetectorbackend.service.SearchingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

@Controller
public class ProductController {

    private final ProductService productService;
    private final SearchingService searchingService;

    public ProductController(ProductService productService, SearchingService searchingService) {

        this.productService = productService;
        this.searchingService = searchingService;
    }
    @GetMapping("/products/new")
    public String createForm(){
        return "products/createForm";
    }

    @PostMapping("/products/new")
    public String createProduct(ProductForm productForm){
        Product product = new Product();
        product.setExternalId(productForm.getExternalId());
        product.setName(productForm.getName());
        product.setImage(productForm.getImage());
        product.setLink(productForm.getLink());
        productService.addProduct(product);
        return "redirect:/";
    }

    @DeleteMapping("/products/one")
    public String deleteProduct(IdForm idFrom){
        productService.delete(idFrom.getId());
        return "redirect:/";
    }

    @GetMapping("/products")
    public String productList(Model model){
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "products/productList";
    }

    @GetMapping("/products/similar")
    public String searchForm(){
        return "products/searchForm";
    }

    @PostMapping(value = "/products/similar", produces = "application/json; charset=UTF8")
    @ResponseBody
    public SearchResult searchProducts(SearchForm form, BindingResult errors, Model model){
        System.out.println("Multipart FormData Binding Result: " + errors);
        SearchResult searchResult = searchingService.searchProduct(form);
        return searchResult;
    }
}
