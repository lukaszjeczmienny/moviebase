package lj.moviebase;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class MovieBaseConfiguration extends Configuration {

    @NotEmpty
    private String initialMoviesDataSetPath;

    @JsonProperty
    public String getInitialMoviesDataSetPath() {
        return initialMoviesDataSetPath;
    }

    @JsonProperty
    public void setInitialMoviesDataSetPath(String initialMoviesDataSetPath) {
        this.initialMoviesDataSetPath = initialMoviesDataSetPath;
    }
}
