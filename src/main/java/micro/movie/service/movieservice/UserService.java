package micro.movie.service.movieservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", url = "http://localhost:8080/")
public interface UserService {
    @GetMapping(value = "/user/hello")
    String hello();
}
