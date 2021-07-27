package io.tral909.employeemanager_vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class ApiVerticle extends AbstractVerticle {

    private static final int API_PORT = 8888;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {

        Router router = Router.router(vertx);
        router.get("employee").handler(this::getEmployee);
        router.get("employee/:id").handler(this::getEmployeeById);
        router.post("employee").handler(this::addEmployee);
        router.put("employee").handler(this::updateEmployee);
        router.delete("employee/:id").handler(this::deleteEmployee);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(API_PORT, http -> {
                if (http.succeeded()) {
                    startPromise.complete();
                    System.out.println("HTTP server started on port " + API_PORT);
                } else {
                    startPromise.fail(http.cause());
                }
            });
    }

    private void getEmployee(RoutingContext routingContext) {
    }

    private void getEmployeeById(RoutingContext routingContext) {
    }

    private void addEmployee(RoutingContext routingContext) {
    }

    private void updateEmployee(RoutingContext routingContext) {
    }

    private void deleteEmployee(RoutingContext routingContext) {
    }
}
