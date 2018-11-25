package lj.moviebase;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lj.moviebase.repository.MovieRepository;
import lj.moviebase.resource.MovieResource;
import lj.moviebase.resource.exception.mapper.EncodingExceptionMapper;
import lj.moviebase.resource.parameter.conventer.AdditionalParamConverterProvider;

import static lj.moviebase.repository.SimpleMovieRepository.initiallyPopulatedMovieRepository;

class MovieBaseApplication extends Application<MovieBaseConfiguration> {

    public static void main(String[] args) throws Exception {
        new MovieBaseApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<MovieBaseConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<MovieBaseConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(MovieBaseConfiguration configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });
    }

    @Override
    public void run(MovieBaseConfiguration configuration, Environment environment) {
        MovieRepository movieRepository = initiallyPopulatedMovieRepository(configuration.getInitialMoviesDataSetPath());
        environment.jersey().register(new MovieResource(movieRepository));
        environment.jersey().register(new AdditionalParamConverterProvider());
        environment.jersey().register(new EncodingExceptionMapper());
    }
}
