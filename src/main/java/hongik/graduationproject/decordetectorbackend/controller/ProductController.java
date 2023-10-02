package hongik.graduationproject.decordetectorbackend.controller;

import hongik.graduationproject.decordetectorbackend.domain.Product;
import hongik.graduationproject.decordetectorbackend.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/products/new")
    public String createForm(){
        return "products/createProductForm";
    }

    @PostMapping("/products/new")
    public String createProduct(ProductForm productForm){
        Product product = new Product();
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
}
