package io.tral909.employeemanager_vertx.verticle;

import io.tral909.employeemanager_vertx.model.Employee;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
public class ApiVerticle extends AbstractVerticle {

    private static final int API_PORT = 8888;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {

        Router router = Router.router(vertx);
        router.get("/employee").handler(this::getEmployee);
        router.get("/employee/:id").handler(this::getEmployeeById);
        router.post("/employee").handler(BodyHandler.create()).handler(this::addEmployee);
        router.put("/employee").handler(BodyHandler.create()).handler(this::updateEmployee);
        router.delete("/employee/:id").handler(this::deleteEmployee);

        router.errorHandler(500, ctx -> ctx.response().end("Sorry, not today!"));

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

    private void getEmployee(RoutingContext ctx) {
        vertx.eventBus().request("employee.get", null, ar -> {
            if (ar.succeeded()) {
                List<Employee> employees = (List<Employee>) ar.result().body();
                ctx.response().end(Json.encode(employees));
            } else {
                log.error("getEmployee api failed", ar.cause());
                ctx.fail(500);
            }
        });
    }

    private void getEmployeeById(RoutingContext ctx) {
        int id = Integer.valueOf(ctx.request().getParam("id"));
        vertx.eventBus().request("employee.get.by.id", id, ar -> {
            if (ar.succeeded()) {
                Employee employee = (Employee) ar.result().body();
                // вместо .response().end(Json.encode(pojo)) можно просто .json() с pojo
                ctx.json(employee);
            } else {
                log.error("getEmployeeById api failed", ar.cause());
                ctx.fail(500);
            }
        });
    }

    private void addEmployee(RoutingContext ctx) {
        JsonObject jsonRequest = ctx.getBodyAsJson();
        Employee newEmployee = jsonRequest.mapTo(Employee.class);
        newEmployee.setCode(UUID.randomUUID().toString());

        vertx.eventBus().request("employee.add", newEmployee);
    }

    private void updateEmployee(RoutingContext ctx) {
        JsonObject jsonRequest = ctx.getBodyAsJson();
        Employee updateEmployee = jsonRequest.mapTo(Employee.class);

        vertx.eventBus().request("employee.update", updateEmployee);
    }

    private void deleteEmployee(RoutingContext ctx) {
        int id = Integer.valueOf(ctx.request().getParam("id"));
        vertx.eventBus().send("employee.delete", id);
        ctx.end();
    }
}
