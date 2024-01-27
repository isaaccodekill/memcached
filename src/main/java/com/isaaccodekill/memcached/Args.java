package com.isaaccodekill.memcached;
import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(names = {"--port", "-p"}, description = "Port to listen on")
    public int port = 11211;

    @Parameter(names = {"--host", "-h"}, description = "Host to listen on")
    public String host = "localhost";

    @Parameter(names = {"--help", "-help"}, description = "Prints help", help = true)
    public boolean help = false;

    @Parameter(names = {"--quit", "-q"}, description = "Quit the server")
    public boolean quit = false;
}
