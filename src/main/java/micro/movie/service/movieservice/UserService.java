package micro.movie.service.movieservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", url = "user-service-service.default.svc.cluster.local")
public interface UserService {
    @GetMapping(value = "/user/hello")
    String hello();
    }
}
