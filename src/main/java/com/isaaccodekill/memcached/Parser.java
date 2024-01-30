package com.isaaccodekill.memcached;

import com.isaaccodekill.memcached.command.Command;
import com.isaaccodekill.memcached.command.CommandBuilder;
import io.vertx.core.buffer.Buffer;

import java.util.Arrays;

public class Parser {


    public static Command parseBuffer(Buffer buffer){
        String bufferString = buffer.toString();
        String[] bufferParts = bufferString.split(" ");

        String commandEntry = bufferParts[0].trim();
        String[] commandArguments = Arrays.copyOfRange(bufferParts, 1, bufferParts.length);

        switch (commandEntry) {
            case "set":
                return handleSetCommand(commandEntry, commandArguments);
            case "get":
            case "gets":
                return handleRetrievalCommand(commandEntry, commandArguments);
            case "delete":
                return handleDeleteCommand(commandEntry, commandArguments);
            case "flush_all":
                return handleFlushCommand(commandEntry, commandArguments);
            case "quit":
                return handleQuitCommand(commandEntry, commandArguments);
            case "help":
                return handleHelpCommand(commandEntry, commandArguments);
            default:
                return handleUnknownCommand(commandEntry, commandArguments);
        }

    }

    private static Command handleSetCommand(String commandEntry, String[] commandArguments){
        String key = commandArguments[0];
        String flag = commandArguments[1];
        Number exptime = Integer.parseInt(commandArguments[2]);
        String size = commandArguments[3];
        Boolean noreply = commandArguments.length == 5 && commandArguments[4].equals("noreply") ;

        CommandBuilder builder = new CommandBuilder();
        builder.setCommand(commandEntry);
        builder.setKeys(new String[]{key});
        builder.setFlag(flag);
        builder.setExptime(exptime);
        builder.setSize(size);
        builder.setNoreply(noreply);
        return builder.build();
    }

    private static Command handleRetrievalCommand(String commandEntry, String[] commandArguments){
        CommandBuilder builder = new CommandBuilder();
        builder.setCommand(commandEntry);
        builder.setKeys(commandArguments);
        return builder.build();
    }

    private static Command handleDeleteCommand(String commandEntry, String[] commandArguments){
        String key = commandArguments[0];
        CommandBuilder builder = new CommandBuilder();
        builder.setCommand(commandEntry);
        builder.setKeys(new String[]{key});
        return builder.build();
    }

    private static Command handleFlushCommand(String commandEntry, String[] commandArguments){
        CommandBuilder builder = new CommandBuilder();
        builder.setCommand(commandEntry);
        return builder.build();
    }

    private static Command handleQuitCommand(String commandEntry, String[] commandArguments){
        CommandBuilder builder = new CommandBuilder();
        builder.setCommand(commandEntry);
        return builder.build();
    }

    private static Command handleHelpCommand(String commandEntry, String[] commandArguments){
        CommandBuilder builder = new CommandBuilder();
        builder.setCommand(commandEntry);
        return builder.build();
    }

    private static Command handleUnknownCommand(String commandEntry, String[] commandArguments){
        CommandBuilder builder = new CommandBuilder();
        builder.setCommand(commandEntry);
        return builder.build();
    }

    
}
