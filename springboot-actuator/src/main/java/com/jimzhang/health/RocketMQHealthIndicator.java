package com.jimzhang.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 实现对RocketMQ的监控
 * Created by admin on 2017/8/2.
 */
@Component
public class RocketMQHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode != 0) {
            return Health.down().withDetail("Error COde", errorCode).build();
        }
        return Health.up().build();
    }

    private int check() {
        // 对监控对象的检测操作
        return 0;
    }
}
