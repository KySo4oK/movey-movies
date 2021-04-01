package micro.movie.service.movieservice;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleKafkaService {
    private final KafkaTemplate<Long, String> kafkaTemplate;

    public void send(String message) {
        kafkaTemplate.send("server.movie", message);
    }
}
