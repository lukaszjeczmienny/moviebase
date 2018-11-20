package lj.moviebase;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class MovieBaseApplication extends Application<MovieBaseConfiguration> {

    public static void main(String[] args) throws Exception {
        new MovieBaseApplication().run(args);
    }

    @Override
    public void run(MovieBaseConfiguration configuration, Environment environment) throws Exception {

    }
}
