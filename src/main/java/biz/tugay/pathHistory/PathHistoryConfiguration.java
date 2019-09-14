package biz.tugay.pathHistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class PathHistoryConfiguration extends Configuration {

    int backupInterval = 2;
    String sourcePath;
    String targetPath;

    @JsonProperty
    public int getBackupInterval() {
        return backupInterval;
    }

    @JsonProperty
    public void setBackupInterval(int backupInterval) {
        this.backupInterval = backupInterval;
    }

    @JsonProperty
    public String getSourcePath() {
        return sourcePath;
    }

    @JsonProperty
    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @JsonProperty
    public String getTargetPath() {
        return targetPath;
    }

    @JsonProperty
    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }
}
