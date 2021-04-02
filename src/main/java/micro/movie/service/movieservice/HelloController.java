package micro.movie.service.movieservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class HelloController {

    @Autowired
    private UserService userService;

    @Autowired
    private SimpleKafkaService kafkaService;

    @GetMapping("/hello")
    public String sayHello() {
        return userService.hello() +
                "Hello! Movie microservice is on Kubernetes now!";
    }

    @GetMapping("/kafka/{message}")
    public String sendToKafka(@PathVariable(name = "message") String message) {
        try {
            kafkaService.send(message);
        } catch (Exception e) {
            return "was not sent to kafka - " + e.getMessage();
        }
        return message + " was sent to kafka";
    }
}
