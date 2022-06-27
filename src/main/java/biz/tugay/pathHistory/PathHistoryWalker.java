package biz.tugay.pathHistory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;
import static java.security.MessageDigest.getInstance;

public class PathHistoryWalker extends SimpleFileVisitor<Path> {

    Path source, target;
    String timestamp;

    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        createDirectories(get(target + File.separator + dir));
        return super.preVisitDirectory(dir, attrs);
    }


    public PathHistoryWalker(Path source, Path target, String timestamp) {
        this.source = source;
        this.target = target;
        this.timestamp = timestamp;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        if (path.getFileName().toString().startsWith(".")) {
            return FileVisitResult.CONTINUE;
        }

        Path targetPath = get(target + File.separator + path);
        if (exists(targetPath)) {
            try {
                MessageDigest md5 = getInstance("MD5");
                if (!Arrays.equals(md5.digest(readAllBytes(targetPath)), md5.digest(readAllBytes(path)))) {
                    String timestamped = timestamped(targetPath, timestamp);
                    move(targetPath, get(timestamped));
                    copy(path, targetPath);
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else {
            copy(path, targetPath);
        }

        return super.visitFile(path, attrs);
    }    
    
    private String timestamped(Path targetPath, String timestamp) {
        int dotIndex = targetPath.toString().lastIndexOf(".");
        if (dotIndex == -1)
            return targetPath.toString().concat("-").concat(timestamp);
        return targetPath.toString().substring(0, dotIndex).concat("-").concat(timestamp).concat(targetPath.toString().substring(dotIndex));
    }
}
