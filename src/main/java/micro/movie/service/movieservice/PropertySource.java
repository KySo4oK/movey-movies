package micro.movie.service.movieservice;

import lombok.Data;

import java.util.Map;

@Data
public class PropertySource {
    String name;
    Map<String, String> source;
}
