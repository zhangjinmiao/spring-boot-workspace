package com.real.springboot.config.dbconfig.routing;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public abstract class AbstractRWRoutingDatasource extends AbstractRoutingDataSource {

    protected abstract int getReadDsSize();

    /**
     *
     * @return
     */
    protected abstract Object loadBalance();
}
