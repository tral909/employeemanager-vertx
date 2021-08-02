package io.tral909.employeemanager_vertx;

import io.tral909.employeemanager_vertx.verticle.ApiVerticle;
import io.tral909.employeemanager_vertx.verticle.DatabaseVerticle;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        // вертикали в одном процессе
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ApiVerticle());
        vertx.deployVerticle(new DatabaseVerticle());
        //vertx.deployVerticle("io.tral909.employeemanager_vertx.verticle.ApiVerticle", new DeploymentOptions().setInstances(1));
        //vertx.deployVerticle("io.tral909.employeemanager_vertx.verticle.DatabaseVerticle", new DeploymentOptions().setInstances(1));

        // распределенные вертиклы (в кластере на разных нодах)
//        Vertx.clusteredVertx(new VertxOptions())
//            .onSuccess(vertx -> {
//                vertx.deployVerticle(new ApiVerticle());
//                vertx.deployVerticle(new DatabaseVerticle());
//            })
//            .onFailure(failure -> {
//                log.error("Error while deploying verticles!", failure);
//            });
    }
}
