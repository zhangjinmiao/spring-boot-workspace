package com.jimzhang.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 自定义磁盘空间健康检测类
 * Created by admin on 2017/8/2.
 */
@Component
public class MyDiskSpaceHealthIndicator extends AbstractHealthIndicator {

    private final FileStore fileStore;
    private final long thresholdBytes;

    @Autowired
    public MyDiskSpaceHealthIndicator(@Value("${health.filestore.path:/}") String path,
                                      @Value("${health.filestore.threshold.bytes:10485760}")long thresholdBytes) throws IOException {
        fileStore = Files.getFileStore(Paths.get(path));
        this.thresholdBytes = thresholdBytes;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        long diskFreeInBytes = fileStore.getUnallocatedSpace();
        if (diskFreeInBytes >= thresholdBytes) {
            builder.up();
        }else {
            builder.down();
        }

        long totalSpace = fileStore.getTotalSpace();
        builder.withDetail("disk.free", diskFreeInBytes);
        builder.withDetail("disk.total", totalSpace);

    }
}
