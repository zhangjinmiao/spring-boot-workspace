package com.jimzhang.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * Created by admin on 2017/8/2.
 */
//@Component
public class MyServerHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode != 0) {
            try {
                return Health.down()
                        .withDetail("status", errorCode)
                        .withDetail("message", new String("服务故障".getBytes(), "UTF-8"))
                        .build();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return Health.up().build();
    }

    private int check(){
        // 对监控对象的检测操作
        return HttpStatus.NOT_FOUND.value();
    }
}
