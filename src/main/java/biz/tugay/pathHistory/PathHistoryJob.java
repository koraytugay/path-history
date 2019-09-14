package biz.tugay.pathHistory;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.nio.file.Files.walkFileTree;
import static java.nio.file.Paths.get;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

public class PathHistoryJob {
    public void startJob(PathHistoryConfiguration pathHistoryConfiguration) {
        ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
        es.scheduleAtFixedRate(() -> this.executeJob(pathHistoryConfiguration), 0, pathHistoryConfiguration.getBackupInterval(), TimeUnit.MINUTES);
    }

    private void executeJob(PathHistoryConfiguration pathHistoryConfiguration) {
        String sourceDir = pathHistoryConfiguration.getSourcePath();
        String targetDir = pathHistoryConfiguration.getTargetPath();
        PathHistoryWalker pathHistoryWalker = new PathHistoryWalker(get(sourceDir), get(targetDir), ofPattern("YYMMdd_HHmmss").format(now()));
        try {
            walkFileTree(get(sourceDir), pathHistoryWalker);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
