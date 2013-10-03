package com.mycompany.myproject;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

/**
 * Comment: ??
 * User: DongWQ
 * Date: 13-10-3，上午1:02
 *
 * @version 0.1
 */
public class FibonacciWorker extends Verticle {

    @Override
    public void start() {
        final Logger logger = container.logger();

        final EventBus eventBus = vertx.eventBus();

        eventBus.registerHandler("fib.request", new Handler<Message<Integer>>() {
            @Override
            public void handle(Message<Integer> msg) {
                Integer result = fib(msg.body().intValue());
                JsonObject resultMessage = new JsonObject();

                resultMessage.putString("number", msg.body().toString()).putString("result", result.toString());

                eventBus.send("fib.response", resultMessage);
            }
        });
    }

    private int fib(int number) {
        if (number == 0) return 0;
        else if (number == 1) return 1;
        else {
            return fib(number - 2) + fib(number - 1);
        }
    }
}