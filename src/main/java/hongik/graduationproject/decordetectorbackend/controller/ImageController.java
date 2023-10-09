package hongik.graduationproject.decordetectorbackend.controller;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
public class ImageController {
    @GetMapping("/images/segmented/{date}/{fileName}")
    public Resource downloadSegmentedImage(@PathVariable("date")String date, @PathVariable("fileName")String fileName)throws MalformedURLException {
        String rootPath = "D:/NJA/Project/hise_GraduationProject/DecorDetector-BackEnd/decordetector-backend/src/main/resources/static/";
        String filePath = rootPath + "downloads/"+ date + "/" + fileName;
        return new UrlResource("file:"+ filePath);

    }
    @GetMapping("/images/ikea/{fileName}")
    public Resource downloadIkeaImage(@PathVariable("fileName")String fileName)throws MalformedURLException{
        String rootPath = "https://www.ikea.com/kr/ko/images/products/";
        return new UrlResource(rootPath + fileName);
    }

}
