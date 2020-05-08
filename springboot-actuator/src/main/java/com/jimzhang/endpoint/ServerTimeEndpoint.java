package com.jimzhang.endpoint;

import org.joda.time.DateTime;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 查看服务器的当前时间
 * Created by admin on 2017/8/2.
 */
@ConfigurationProperties(prefix = "endpoints.servertime")
public class ServerTimeEndpoint extends AbstractEndpoint<Map<String, Object>> {

    public ServerTimeEndpoint() {
        super("servertime", false);
    }

    @Override
    public Map<String, Object> invoke() {
        Map<String, Object> result = new HashMap<String, Object>();
        DateTime dateTime = DateTime.now();
        result.put("server_time", dateTime.toString());
        result.put("ms_format", dateTime.getMillis());
        return result;
    }
}
