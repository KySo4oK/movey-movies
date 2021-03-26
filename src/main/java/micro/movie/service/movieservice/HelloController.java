package micro.movie.service.movieservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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

    @GetMapping("/hi")
    @HystrixCommand(fallbackMethod = "defaultMethod",
            threadPoolKey = "getProductThreadPool",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "20000"),
                    @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "5")
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "-1"),
            })
    public String sayHi() {
        return service.hello() + "HI! Movie microservice is on Kubernetes now!";
    }


    private String defaultMethod() {
        return "User unavailable";
    }
}
