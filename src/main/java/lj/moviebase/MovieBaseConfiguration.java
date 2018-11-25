package lj.moviebase;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

public class MovieBaseConfiguration extends Configuration {

    @NotEmpty
    private String initialMoviesDataSetPath;
    private SwaggerBundleConfiguration swaggerBundleConfiguration;

    @JsonProperty
    public String getInitialMoviesDataSetPath() {
        return initialMoviesDataSetPath;
    }

    @JsonProperty
    public void setInitialMoviesDataSetPath(String initialMoviesDataSetPath) {
        this.initialMoviesDataSetPath = initialMoviesDataSetPath;
    }

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }

    @JsonProperty("swagger")
    public void setSwaggerBundleConfiguration(SwaggerBundleConfiguration swaggerBundleConfiguration) {
        this.swaggerBundleConfiguration = swaggerBundleConfiguration;
    }
}
