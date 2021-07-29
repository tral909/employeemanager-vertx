package io.tral909.employeemanager_vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgPool;

public class DatabaseVerticle extends AbstractVerticle {

    private PgPool pgPool;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        // можно явно указать настройки
//        pgPool = PgPool.pool(vertx, new PgConnectOptions()
//            .setHost("127.0.0.1")
//            .setUser("postgres")
//            .setDatabase("postgres")
//            .setPassword("12345"), new PoolOptions());

        // или через переменные окружения
        pgPool = PgPool.pool();

        //todo адреса вынести в переменные
        vertx.eventBus().consumer("employee.get", this::getEmployee);
        vertx.eventBus().consumer("employee.get.by.id", this::getEmployeeById);
        vertx.eventBus().consumer("employee.add", this::addEmployee);
        vertx.eventBus().consumer("employee.update", this::updateEmployee);
        vertx.eventBus().consumer("employee.delete", this::deleteEmployee);
    }

    private void getEmployee(Message<JsonObject> msg) {
    }

    private void getEmployeeById(Message<JsonObject> msg) {
    }

    private void addEmployee(Message<JsonObject> msg) {
    }

    private void updateEmployee(Message<JsonObject> msg) {
    }

    private void deleteEmployee(Message<JsonObject> msg) {
    }
}
