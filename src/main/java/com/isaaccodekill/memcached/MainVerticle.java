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
          // this is a socket handler
          // lets try storing an unfinished command here

          // --- TODO: refactor this (can't work because only final variables can be used in lambda expressions
          Command savedCommand = null;
          socket.handler(buffer -> {

              // problem, some commands require more than one line
              // we need to buffer the input until we have a complete command

              Command command =  Parser.parseBuffer(buffer, savedCommand);

              // check if command is incomplete

              if(command.incomplete){
                  savedCommand = command;
                  return;
              }

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