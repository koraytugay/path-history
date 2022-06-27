package biz.tugay.pathHistory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.nio.file.Files.walkFileTree;
import static java.nio.file.Paths.get;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

public class PathHistoryJob {
     private void executeJob() {
        try (FileInputStream is = new FileInputStream("path-history.properties")) {
            Properties properties = new Properties();
            properties.load(is);
            String[] sourceDirs = properties.getProperty("source-paths").split(",");
            String targetDir = properties.getProperty("target-path");
            for (String sourceDir : sourceDirs) {
                PathHistoryWalker pathHistoryWalker = new PathHistoryWalker(get(sourceDir), get(targetDir), ofPattern("YYMMdd_HHmmss").format(now()));
                walkFileTree(get(sourceDir), pathHistoryWalker);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void startJob() {
        ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
        es.scheduleAtFixedRate(this::executeJob, 0, 2, TimeUnit.MINUTES);
    }
}
