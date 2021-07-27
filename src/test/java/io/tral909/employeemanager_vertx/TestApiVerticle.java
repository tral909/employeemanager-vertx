package io.tral909.employeemanager_vertx;

import io.vertx.junit5.VertxExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class TestApiVerticle {

    // todo тесты сломались
//    @BeforeEach
//    void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
//        vertx.deployVerticle(new ApiVerticle(), testContext.succeeding(id -> testContext.completeNow()));
//    }
//
//    @Test
//    void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
//        testContext.completeNow();
//    }
}
