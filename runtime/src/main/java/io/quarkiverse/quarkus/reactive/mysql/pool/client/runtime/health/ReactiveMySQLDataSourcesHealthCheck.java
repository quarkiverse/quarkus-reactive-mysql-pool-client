package io.quarkiverse.quarkus.reactive.mysql.pool.client.runtime.health;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;

import org.eclipse.microprofile.health.Readiness;

import io.quarkus.arc.Arc;
import io.quarkus.arc.ArcContainer;
import io.quarkus.arc.InstanceHandle;
import io.quarkus.datasource.runtime.DataSourcesHealthSupport;
import io.quarkus.reactive.datasource.runtime.ReactiveDatasourceHealthCheck;
import io.vertx.mysqlclient.MySQLPool;

@Readiness
@ApplicationScoped
class ReactiveMySQLDataSourcesHealthCheck extends ReactiveDatasourceHealthCheck {

    public ReactiveMySQLDataSourcesHealthCheck() {
        super("Reactive MySQL connections health check", "SELECT 1");
    }

    @PostConstruct
    protected void init() {
        ArcContainer container = Arc.container();
        DataSourcesHealthSupport excluded = container.instance(DataSourcesHealthSupport.class).get();
        Set<String> excludedNames = excluded.getExcludedNames();
        for (InstanceHandle<MySQLPool> handle : container.select(MySQLPool.class, Any.Literal.INSTANCE).handles()) {
            String poolName = getPoolName(handle.getBean());
            if (!excludedNames.contains(poolName)) {
                addPool(poolName, handle.get());
            }
        }
    }

}
