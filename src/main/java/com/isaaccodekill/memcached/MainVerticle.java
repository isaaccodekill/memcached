package com.isaaccodekill.memcached;
import com.isaaccodekill.memcached.command.Command;
import com.isaaccodekill.memcached.localcache.Cache;
import com.isaaccodekill.memcached.parser.Parser;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;


public class MainVerticle extends AbstractVerticle {

    @Override
  public void start() throws Exception {
      int port = config().getInteger("port");
      System.out.println("Port is " + port);
      NetServer server = vertx.createNetServer();

      server.connectHandler(socket -> {
          System.out.println("A client has connected!");
          socket.handler(buffer -> {

              // problem, some commands require more than one line
              // we need to buffer the input until we have a complete command

              Command command =  Parser.parseBuffer(buffer);

              if(command != null){
                  String response = command.execute();
                  if(command.noreply){
                      Buffer responseBuffer = Buffer.buffer(response);
                      socket.write(responseBuffer);
                  }
              }
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