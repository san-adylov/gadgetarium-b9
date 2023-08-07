package peaksoft.house.gadgetariumb9;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@SpringBootApplication
public class GadgetariumB9Application {

    public static void main(String[] args) {
        SpringApplication.run(GadgetariumB9Application.class, args);
    }

    @GetMapping
    public String greetings() {
        return "index";
    }


}
