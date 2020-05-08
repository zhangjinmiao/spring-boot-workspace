package com.jimzhang.listener;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @className : LoggerConfig
 * @description: 监听日志变动
 * @author: zhangjm
 * @create: 2020-04-28 10:55
 **/
@Configuration
public class LoggerConfigListener {

    private static final Logger logger = LoggerFactory.getLogger(LoggerConfigListener.class);
    private static final String LOGGER_TAG = "logging.level.";

    @Autowired
    private LoggingSystem loggingSystem;

    @ApolloConfig // 将Apollo服务端的中的配置注入这个类中
    private Config config;

    @ApolloConfigChangeListener // 监听配置中心配置的更新事件，若该事件发生，则调用refreshLoggingLevels方法，处理该事件
    private void configChangeListter(ConfigChangeEvent changeEvent) { // ConfigChangeEvent参数:可以获取被修改配置项的key集合，以及被修改配置项的新值、旧值和修改类型等信息
        refreshLoggingLevels();
    }

    @PostConstruct
    private void refreshLoggingLevels() {
        // 获得 Apollo 所有配置项
        Set<String> keyNames = config.getPropertyNames();
        // 遍历配置集的每个配置项，判断是否是 logging.level 配置项
        for (String key : keyNames) {
            // 如果是 logging.level 配置项，则设置其对应的日志级别
            if (StringUtils.containsIgnoreCase(key, LOGGER_TAG)) {
                // 获得日志级别
                String strLevel = config.getProperty(key, "info");
                LogLevel level = LogLevel.valueOf(strLevel.toUpperCase());
                // 设置日志级别到 LoggingSystem 中
                loggingSystem.setLogLevel(key.replace(LOGGER_TAG, ""), level);
                logger.info("change key {}:value {}", key, strLevel);
            }
        }
    }
}
