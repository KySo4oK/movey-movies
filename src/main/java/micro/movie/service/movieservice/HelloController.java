package micro.movie.service.movieservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class HelloController {
    @Autowired
    UserService service;

    @GetMapping("/hello")
    public String sayHello() {
        String n;
        while (true) {
            try {
                n = service.hello();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                continue;
            }
            return n + "Hello! Movie microservice is on Kubernetes now!";
        }
    }
}
