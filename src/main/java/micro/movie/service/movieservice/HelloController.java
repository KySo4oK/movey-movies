package micro.movie.service.movieservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello! Movie microservice is on Kubernetes now!";
    }
}
