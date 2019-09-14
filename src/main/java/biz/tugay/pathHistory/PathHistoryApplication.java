package biz.tugay.pathHistory;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PathHistoryApplication extends Application<PathHistoryConfiguration> {

  public static void main(String[] args) throws Exception {
    new PathHistoryApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<PathHistoryConfiguration> bootstrap) {
    GuiceBundle<PathHistoryConfiguration> guiceBundle =
        GuiceBundle.<PathHistoryConfiguration>newBuilder()
            .addModule(new PathHistoryModule()).setConfigClass(PathHistoryConfiguration.class)
            .enableAutoConfig(getClass().getPackage().getName()).build();
    bootstrap.addBundle(guiceBundle);
  }

  @Override
  public void run(PathHistoryConfiguration pathHistoryConfiguration, Environment environment) {
  }
}
