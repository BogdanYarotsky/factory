package factory.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockController {
    @GetMapping("/mock")
    public void mockMethod() {
    }
}
