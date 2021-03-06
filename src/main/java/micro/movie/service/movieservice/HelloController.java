package micro.movie.service.movieservice;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;

@RestController
@RequestMapping("/movie")
public class HelloController {
    Tmdb tmdb = new Tmdb("df42876914d18a938f38411b2d1042e5");
    MoviesService moviesService = tmdb.moviesService();
    private static final Logger log = Logger.getLogger(String.valueOf(HelloController.class));

    @Autowired
    private SuggestionsService suggestionsService;

    @Autowired
    RabbitMqReceiver rabbitMqReceiver;

    @GetMapping("/hello")
    public String sayHello() {
        return suggestionsService.hello() +
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
    public ResponseEntity<MovieResultsPage> fetchMovies() {
        log.info("fetching movies");
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

    @GetMapping("/saved/{userId}")
    public ResponseEntity<List<BaseMovie>> getSavedMoviesByUser(@PathVariable Integer userId) {
        log.info("get saved movies for - " + userId);
        List<Integer> ids = suggestionsService.getSavedMovieIds(userId);
        List<BaseMovie> savedMovies = ids.stream().map(it -> {
            try {
                BaseMovie movie = moviesService.summary(it, "").execute().body();
                movie.poster_path = "https://image.tmdb.org/t/p/w500" + movie.poster_path;
                return movie ;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(savedMovies);
    }
}
