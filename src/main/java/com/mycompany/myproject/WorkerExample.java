package com.mycompany.myproject;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

/**
 * Comment: ??
 * User: DongWQ
 *
 * @version 0.1
 */
public class WorkerExample extends Verticle {

    @Override
    public void start() {
        final Logger logger = container.logger();

        logger.info("start WorkerExample");
        logger.info("loaded ");
        final EventBus eventBus = vertx.eventBus();

        Handler<Message<JsonObject>> resultHandler = new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> message) {
                System.out.println("" + message.body().getString("result"));
                System.out.println("" + message.body().getString("number"));
            }
        };

        eventBus.registerHandler("fib.response", resultHandler);

        container.deployWorkerVerticle("com.mycompany.myproject.FibonacciWorker",
                new JsonObject(), 1, true, new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> event) {

                logger.info("in deployWorkerVerticle");

                eventBus.send("fib.request", 20);
            }
        });
    }
}