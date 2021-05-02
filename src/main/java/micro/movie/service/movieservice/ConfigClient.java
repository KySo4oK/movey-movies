package micro.movie.service.movieservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ConfigClient {
    String url = "http://sccs-service-service.default.svc.cluster.local";

    Map<String, String> getSources(String applicationName, String profile) {
        String forObject = new RestTemplate().getForObject(url + "/" + applicationName + "/" + profile, String.class);
        System.out.println(forObject);
        try {
            return new ObjectMapper().readValue(forObject, ConfigEntity.class).getPropertySources()[0].getSource();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
