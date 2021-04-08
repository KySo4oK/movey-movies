package micro.movie.service.movieservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class HelloController {

    @Autowired
    private UserService userService;

    @Autowired
    RabbitMqReceiver rabbitMqReceiver;

    @GetMapping("/hello")
    public String sayHello() {
        return userService.hello() +
                "Hello! Movie microservice is on Kubernetes now!";
    }

    @GetMapping("/rabbit")
    public String listenRabbit() {
        try {
            return rabbitMqReceiver.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "listener error";
        }
    }
}
