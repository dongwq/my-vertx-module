package com.mycompany.myproject;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

/**
 * Comment: ??
 * User: DongWQ
 *
 * @version 0.1
 */
public class AWebServer extends Verticle {

    @Override
    public void start() {
        HttpServer httpServer = vertx.createHttpServer();

        RouteMatcher routeMatcher = new RouteMatcher();
        routeMatcher.get("user", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                event.response().end(new Buffer("path user"));
            }
        });

        routeMatcher.get("/:controller/:action", new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest event) {
                String controller = event.params().get("controller");
                String action = event.params().get("action");

                 container.deployVerticle("");

                event.response().end(new Buffer(String.format("c=%s, a=%s", controller, action)));
            }
        });

        httpServer.requestHandler(routeMatcher).listen(8081, "localhost");

        container.logger().info("----------------server started-----------------");
    }
}
