package com.isaaccodekill.memcached;
import com.isaaccodekill.memcached.command.Command;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;


public class MainVerticle extends AbstractVerticle {

    private NetServer server;

  @Override
  public void start() throws Exception {
      int port = config().getInteger("port");
      System.out.println("Port is " + port);
    server = vertx.createNetServer();

      server.connectHandler(socket -> {
          System.out.println("A client has connected!");
          socket.handler(buffer -> {
              System.out.println("I received some bytes: " + buffer.length());
              Command command =  Parser.parseBuffer(buffer);
              String response = command.execute();
              Buffer responseBuffer = Buffer.buffer(response);
              socket.write(responseBuffer);
          });
      });

    server
        .listen(port, "localhost")
        .onComplete(ar -> {
          if (ar.succeeded()) {
            System.out.println("Server is now listening!");
          } else {
            System.out.println("Failed to bind!");
          }
        });

  }
}