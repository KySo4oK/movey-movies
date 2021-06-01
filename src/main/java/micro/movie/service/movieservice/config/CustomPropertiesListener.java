package micro.movie.service.movieservice.config;

import micro.movie.service.movieservice.util.EncryptionUtils;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Map;
import java.util.Properties;

public class CustomPropertiesListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private final ConfigClient configClient = new ConfigClient();
    private Map<String, String> sources;
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        Properties props = new Properties();
        sources = configClient.getSources("user", "sccs");
        props.put("spring.rabbitmq.password", getSecret("spring.rabbitmq.password"));
        System.out.println(getSecret("spring.rabbitmq.password"));
        System.out.println(getSecret("spring.rabbitmq.username"));
        props.put("spring.rabbitmq.username", getSecret("spring.rabbitmq.username"));
        environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
    }

    private String getSecret(String key) {
        return new EncryptionUtils().decrypt(sources.get(key));
    }
}
