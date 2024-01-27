package com.isaaccodekill.memcached;

import com.beust.jcommander.JCommander;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class Main {
    public static void main(String[] args){


        Vertx vertx = Vertx.vertx();
        Verticle mainVerticle = new MainVerticle();

        Args argsInternal = new Args();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(argsInternal)
                .build();

        jCommander.parse(args);

        if(argsInternal.help){
            jCommander.usage();
            return;
        }


        JsonObject config = new JsonObject().put("port", argsInternal.port);

        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setConfig(config);

        System.out.println("Starting server on port " + argsInternal.port);
        vertx.deployVerticle(mainVerticle, deploymentOptions)
        .onComplete(ar -> {
            if (ar.succeeded()) {
                System.out.println("Server is now listening!");
            } else {
                System.out.println("Failed to bind!" + ar.cause());
            }
        });

        if(argsInternal.quit){
            System.out.println("Shutting down");
            // shut down verticle
        }

    }
}