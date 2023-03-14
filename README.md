# Quarkus Reactive Mysql Pool Client

[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.quarkus-reactive-mysql-pool-client/quarkus-reactive-mysql-pool-client?logo=apache-maven&style=flat-square)](https://search.maven.org/artifact/io.quarkiverse.quarkus-reactive-mysql-pool-client/quarkus-reactive-mysql-pool-client)

## Welcome to Quarkiverse!

**ATTENTION: Only works on Quarkus 2**

This extension enable usage of reactive client on mysql/mariadb galera cluster with support of fatal tolerance and load balance.

There is built-in load balance support from MySQL Connector/J driver, so we can config load balance in application.properties with
`quarkus.datasource.jdbc.url=jdbc:mysql:loadbalance://192.168.32.101:3306,192.168.32.102:3306/dbname`
to take this advantage for non-reactive data source.

While switch to reactive data source, there is no build-in support for this style of load balance. According to
[Vert.x mysql client document](https://vertx.io/docs/vertx-mysql-client/java/#_advanced_pool_configuration),
vert.x mysql client not support the `loadbalance` keyword within url. But vert.x do support server load balance with
a vert.x pool of a list of servers. Given the pool only provide a round-robin load balancing to select different
servers when a connection is created rather when the connection is borrowed from the pool.

To add fatal tolerance and load balance support to reactive mysql client, we can just take usage of
`MySQLPool.pool(Vertx vertx, List<MySQLConnectOptions> databases, PoolOptions options)` to create the pool if the comma
is present within the url string. Then we can use `quarkus.datasource.reactive.url` as below:

`mysql:loadbalance://192.168.32.101:3306,192.168.32.102:3306/dbname`

`mysql://192.168.32.101:3306,192.168.32.102:3306/dbname`

The url is to minic mysql jdbc driver, so it's easy to maintain config for all services.

Related discuss is here [#31492](https://github.com/quarkusio/quarkus/issues/31492)

For Quarkus 3, we can write a similar driver once it's mature enough.

## Documentation

The documentation for this extension should be maintained as part of this repository and it is stored in the `docs/` directory.

The layout should follow the [Antora's Standard File and Directory Set](https://docs.antora.org/antora/2.3/standard-directories/).

Once the docs are ready to be published, please open a PR including this repository in the [Quarkiverse Docs Antora playbook](https://github.com/quarkiverse/quarkiverse-docs/blob/main/antora-playbook.yml#L7). See an example [here](https://github.com/quarkiverse/quarkiverse-docs/pull/1).
