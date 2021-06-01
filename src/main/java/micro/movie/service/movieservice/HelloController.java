package micro.movie.service.movieservice;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;

@RestController
@RequestMapping("/movie")
public class HelloController {
    Tmdb tmdb = new Tmdb("df42876914d18a938f38411b2d1042e5");
    MoviesService moviesService = tmdb.moviesService();

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

    @GetMapping("/fetch")
    public ResponseEntity<MovieResultsPage> getSmth() {
        try {
            Response<MovieResultsPage> response = moviesService.topRated((int)(Math.random() * 99) + 1, "", "").execute();
            if (response.isSuccessful()) {
                MovieResultsPage movies = response.body();
                movies.results.forEach(it -> it.poster_path = "https://image.tmdb.org/t/p/w500" + it.poster_path);
                return ResponseEntity.of(Optional.ofNullable(movies));
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
