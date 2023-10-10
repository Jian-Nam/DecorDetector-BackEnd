package hongik.graduationproject.decordetectorbackend.controller;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
public class ImageController {
    @GetMapping("/images/segmented/{date}/{fileName}")
    public Resource downloadSegmentedImage(@PathVariable("date")String date, @PathVariable("fileName")String fileName)throws MalformedURLException {
        String rootPath = "src/main/resources/static/";
        String filePath = rootPath + "downloads/"+ date + "/" + fileName;
        return new UrlResource("file:"+ filePath);

    }
    @GetMapping("/images/ikea")
    public Resource downloadIkeaImage(@RequestParam("url")String url)throws MalformedURLException{
        return new UrlResource(url);
    }

}
