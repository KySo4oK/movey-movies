package micro.movie.service.movieservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqReceiver implements RabbitListenerConfigurer {

    private String message;

    public String getMessage() {
        return message;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(String message) {
        this.message = message;
    }

    @Override
    public void configureRabbitListeners(
            RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}