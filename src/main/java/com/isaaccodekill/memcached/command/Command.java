package com.isaaccodekill.memcached.command;


import com.isaaccodekill.memcached.localcache.Cache;

// utilize the builder pattern to create a command object
public class Command {

    public String command;
    public String[] keys;
    public String flag;
    public Number exptime;
    public String size;
    public Boolean noreply;





    public String execute(){
        switch(command){
            case "set":
                return handleSetCommand("", "", flag, exptime, size);
            case "get":
                return handleGetCommand("");
            case "delete":
               return handleDeleteCommand("");
            case "flush_all":
                return handleFlushCommand();
            case "quit":
               return handleQuitCommand();
            case "help":
                return handleHelpCommand();
            default:
                return handleUnknownCommand();
        }
    }
    private String handleSetCommand(String key, String value,  String flag, Number exptime, String size){
        // handle set command
        Cache cache =  Cache.getInstance();
        return cache.set(key, value, flag, exptime, size);
    }

    private String handleGetCommand(String key){

        return "get command";
    }

    private String handleDeleteCommand(String key){
        // handle delete command
        return "delete command";
    }

    private String handleFlushCommand(){
        // handle flush command
        return "flush command";
    }

    private String handleQuitCommand(){
        // handle quit command
        return "quit command";
    }

    private String handleHelpCommand(){
        // handle help command
        String response = "This is a list of commands\r\n";

        return response.concat("set <key> <flags> <exptime> <bytes>\r\n" +
                "Add this data to the cache under the specified key. If the key already exists, it will overwrite the existing value.\r\n" +
                "\r\n" +
                "get <key>*\r\n" +
                "Retrieve the data associated with the specified key/keys.\r\n" +
                "\r\n" +
                "delete <key>\r\n" +
                "Remove the specified key and associated data from the cache.\r\n" +
                "\r\n" +
                "flush_all\r\n" +
                "Remove all keys and associated data from the cache.\r\n" +
                "\r\n" +
                "quit\r\n" +
                "Close the connection.");
    }

    private String handleUnknownCommand(){
        // handle unknown command
        return "unknown command";
    }
}
