package com.jimzhang.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Method;
import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

/**
 * 〈一句话功能简述〉<br> 〈redis 配置类〉
 *
 * @author zhangjinmiao
 * @create 2019/4/29 14:03
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

  @Nullable
  @Override
  public KeyGenerator keyGenerator() {
    return new KeyGenerator() {
      @Override
      public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName());
        sb.append(method.getName());
        for (Object obj : params) {
          sb.append(obj.toString());
        }
        return sb.toString();
      }
    };
  }

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
    return RedisCacheManager
        .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
        .cacheDefaults(redisCacheConfiguration).build();
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);

    template.setConnectionFactory(factory);
    // 开启事务支持
    template.setEnableTransactionSupport(true);
    //template.setEnableDefaultSerializer(false); // 将任何序列化器设置为null，并使用原始字节数组来使用RedisTemplate

    //key序列化方式 stringRedisSerializer
    template.setKeySerializer(stringRedisSerializer);
    template.setHashKeySerializer(stringRedisSerializer);
    //value序列化 jackson2JsonRedisSerializer
    template.setValueSerializer(jackson2JsonRedisSerializer);
    template.setHashValueSerializer(jackson2JsonRedisSerializer);
    template.afterPropertiesSet();

    return template;
  }
}
