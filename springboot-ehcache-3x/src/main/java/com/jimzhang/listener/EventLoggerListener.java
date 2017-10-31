package com.jimzhang.listener;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jimzhang on 2017/10/30.
 */
public class EventLoggerListener implements CacheEventListener<Object, Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventLoggerListener.class);
    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        LOGGER.info("Event: " + event.getType() + " Key: " + event.getKey() + " old value: " + event.getOldValue()
                + " new value: " + event.getNewValue());

    }
}
