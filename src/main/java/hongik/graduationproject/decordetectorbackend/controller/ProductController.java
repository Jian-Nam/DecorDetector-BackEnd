package hongik.graduationproject.decordetectorbackend.controller;

import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.domain.SearchResult;
import hongik.graduationproject.decordetectorbackend.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
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

    @PostMapping("/products/similar")
    public String searchProducts(SearchForm form, BindingResult errors){
        System.out.println("Multipart FormData Binding Result: " + errors);
        SearchResult searchResult = productService.searchProduct(form);
        return "redirect:/";
    }
}
