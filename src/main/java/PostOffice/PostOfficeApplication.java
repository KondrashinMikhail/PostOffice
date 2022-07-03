package PostOffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class PostOfficeApplication{
    public static void main(String[] args) {
        SpringApplication.run(PostOfficeApplication.class, args);
    }

    @GetMapping("/")
    public String onStartUp() {
        return "redirect:/swagger-ui/index.html";
    }
}
