//package com.jimzhang.config;
//
//import com.ctrip.framework.apollo.Config;
//import com.ctrip.framework.apollo.core.utils.StringUtils;
//import com.ctrip.framework.apollo.model.ConfigChangeEvent;
//import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
//import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.logging.LogLevel;
//import org.springframework.boot.logging.LoggerConfiguration;
//import org.springframework.boot.logging.LoggingSystem;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.lang.reflect.Method;
//import java.util.Set;
//
///**
// * @className : LoggingLevelRefresher
// * @description:
// * @author: zhangjm
// * @create: 2020-04-27 14:02
// **/
//@Component
//public class LoggingLevelRefresherTwo {
//    private final static Logger log = LoggerFactory.getLogger(LoggingLevelRefresherTwo.class);
//
//    private static final String PREFIX = "logging.level.";
//    private static final String ROOT = LoggingSystem.ROOT_LOGGER_NAME;
//    private static final String SPLIT = ".";
//
//    @Resource
//    private LoggingSystem loggingSystem;
//
//    @ApolloConfig
//    private Config config;
//
//    @PostConstruct
//    private void init() {
//        refreshLoggingLevels(config.getPropertyNames());
//    }
//
//    @ApolloConfigChangeListener(interestedKeyPrefixes = PREFIX)
//    private void onChange(ConfigChangeEvent changeEvent) {
//        refreshLoggingLevels(changeEvent.changedKeys());
//    }
//
//    private void refreshLoggingLevels(Set<String> changedKeys) {
//        for (String key : changedKeys) {
//            // key may be : logging.level.com.example.web
//            if (StringUtils.startsWithIgnoreCase(key, PREFIX)) {
//                String loggerName = PREFIX.equalsIgnoreCase(key) ? ROOT : key.substring(PREFIX.length());
//                String strLevel = config.getProperty(key, parentStrLevel(loggerName));
//                LogLevel level = LogLevel.valueOf(strLevel.toUpperCase());
//                loggingSystem.setLogLevel(loggerName, level);
//
//                log(loggerName, strLevel);
//            }
//        }
//    }
//
//
//    private String parentStrLevel(String loggerName) {
//        String parentLoggerName = loggerName.contains(SPLIT) ? loggerName.substring(0, loggerName.lastIndexOf(SPLIT)) : ROOT;
//        return loggingSystem.getLoggerConfiguration(parentLoggerName).getEffectiveLevel().name();
//    }
//
//    /**
//     * 获取当前类的Logger对象有效日志级别对应的方法，进行日志输出。举例：
//     * 如果当前类的EffectiveLevel为WARN，则获取的Method为 `org.slf4j.Logger#warn(java.lang.String, java.lang.Object, java.lang.Object)`
//     * 目的是为了输出`changed {} log level to:{}`这一行日志
//     */
//    private void log(String loggerName, String strLevel) {
//        try {
//            LoggerConfiguration loggerConfiguration = loggingSystem.getLoggerConfiguration(log.getName());
//            Method method = log.getClass().getMethod(loggerConfiguration.getEffectiveLevel().name().toLowerCase(), String.class, Object.class, Object.class);
//            method.invoke(log, "changed {} log level to:{}", loggerName, strLevel);
//        } catch (Exception e) {
//            log.error("changed {} log level to:{} error", loggerName, strLevel, e);
//        }
//    }
//}