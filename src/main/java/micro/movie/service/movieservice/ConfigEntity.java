package micro.movie.service.movieservice;

import lombok.Data;

@Data
public class ConfigEntity {
    private String name;
    private String[] profiles;
    private String label;
    private String version;
    private String state;
    private PropertySource[] propertySources;
}
