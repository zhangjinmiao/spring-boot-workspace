package com.real.springboot.config.dbconfig.routing;

import com.real.springboot.config.dbconfig.DataSourceContextHolder;
import com.real.springboot.config.dbconfig.DataSourceType;

import javax.sql.DataSource;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinRoutingDataSouce extends AbstractRWRoutingDatasource {

    private AtomicInteger count = new AtomicInteger(0);

    private int readDsSize;

    public RoundRobinRoutingDataSouce(int size){
        this.readDsSize = size;
    }

    @Override
    protected int getReadDsSize() {
        return this.readDsSize;
    }

    @Override
    protected Object loadBalance() {
        //读库， 简单负载均衡
        int lookupKey = Math.abs(count.incrementAndGet()) % getReadDsSize();
        System.err.println("使用数据库read-"+(lookupKey));
        return lookupKey;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DataSourceContextHolder.getReadOrWrite();

        if(typeKey == null){
            throw new NullPointerException("数据库路由时，决定使用哪个数据库源类型不能为空...");
        }

        if (typeKey.equals(DataSourceType.write.getType())){
            System.err.println("使用数据库write.............");
            return DataSourceType.write.getType();
        }else{
            return loadBalance();
        }
    }
}
