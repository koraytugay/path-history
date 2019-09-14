package biz.tugay.pathHistory;

import static java.nio.file.Paths.get;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class PathHistoryJob {

  PathHistoryConfiguration pathHistoryConfiguration;

  ScheduledExecutorService es;

  @Inject
  public PathHistoryJob(PathHistoryConfiguration pathHistoryConfiguration) {
    this.pathHistoryConfiguration = pathHistoryConfiguration;
  }

  public void startJob() {
    es = Executors.newScheduledThreadPool(1);
    es.scheduleAtFixedRate(this::executeJob, 0, pathHistoryConfiguration.getBackupInterval(),
        TimeUnit.MINUTES);
  }

  public void stopJob() {
    es.shutdown();
    es = null;
  }

  private void executeJob() {
    Path target = Paths.get(pathHistoryConfiguration.getTargetPath());
    PathHistoryWalker pathHistoryWalker = new PathHistoryWalker(target);
    try {
      Path source = get(pathHistoryConfiguration.getSourcePath());
      Files.walkFileTree(source, pathHistoryWalker);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
