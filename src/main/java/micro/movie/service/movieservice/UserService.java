package micro.movie.service.movieservice;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="employee-producer")
public interface UserService {
    @RequestMapping(method= RequestMethod.GET, value="/user/hello")
    public String getData();
}
