package biz.tugay.pathHistory;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class PathHistoryApplication extends Application<PathHistoryConfiguration> {

    public static void main(String[] args) throws Exception {
        new PathHistoryApplication().run(args);
    }

    @Override
    public void run(PathHistoryConfiguration pathHistoryConfiguration, Environment environment) throws Exception {
        new PathHistoryJob().startJob(pathHistoryConfiguration);
    }
}
