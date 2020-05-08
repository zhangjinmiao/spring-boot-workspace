package com.real.springboot.config.dbconfig;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "mysql.datasource")
public class DatasourceProperties {

    private List<DruidDataSource> read = new ArrayList<>();

    private DruidDataSource write = new DruidDataSource();

    public DruidDataSource getWrite() {
        return write;
    }

    public void setWrite(DruidDataSource write) {
        this.write = write;
    }

    public List<DruidDataSource> getRead() {
        return read;
    }

    public void setRead(List<DruidDataSource> read) {
        this.read = read;
    }
}
