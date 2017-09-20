package com.jimzhang.springbootredis.annotation;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

/**
 * Created by admin on 2017/8/30.
 */
@Caching(put = {
        @CachePut(value = "user", key = "'user' + #result.id"),
        @CachePut(value = "user", key = "'user' + #result.nickName"),
        @CachePut(value = "user", key = "'user' + #result.email"),
})
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UserSaveCache {
}
