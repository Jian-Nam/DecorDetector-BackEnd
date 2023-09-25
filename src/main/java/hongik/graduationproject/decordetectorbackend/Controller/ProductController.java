package hongik.graduationproject.decordetectorbackend.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    @GetMapping("/products")
    public String productList(Model model){
        List<String> products = new ArrayList<String>();
        products.add("hello product");
        products.add("Bye product");
        model.addAttribute("products", products);
        return "products/productList";
    }
}
