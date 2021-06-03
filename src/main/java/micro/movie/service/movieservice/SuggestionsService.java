package micro.movie.service.movieservice;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "suggestions-service", url = "notification-service-service.default.svc.cluster.local")
public interface SuggestionsService {
    @GetMapping(value = "/notification/savings/{userId}")
    List<Integer> getSavedMovieIds(@PathVariable Integer userId);

    @GetMapping(value = "/notification/hello")
    String hello();
}
