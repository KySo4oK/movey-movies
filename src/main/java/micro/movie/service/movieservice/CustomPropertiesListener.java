package micro.movie.service.movieservice;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Map;
import java.util.Properties;

public class CustomPropertiesListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    ConfigClient configClient = new ConfigClient();
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        Properties props = new Properties();
        Map<String, String> sources = configClient.getSources("user", "sccs");
        props.put("spring.rabbitmq.password", sources.get("spring.rabbitmq.password"));
        props.put("spring.rabbitmq.username", sources.get("spring.rabbitmq.username"));
        environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
    }
}
