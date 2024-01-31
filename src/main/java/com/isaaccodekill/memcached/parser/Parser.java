package com.isaaccodekill.memcached.parser;

import com.isaaccodekill.memcached.command.Command;
import com.isaaccodekill.memcached.command.CommandBuilder;
import com.isaaccodekill.memcached.command.CommandType;
import io.vertx.core.buffer.Buffer;

import java.util.Arrays;

public class Parser {


    // not thread safe
    private static CommandBuilder savedCommandBuilder = null;
    private static void saveCommandBuilder (CommandBuilder uncompletedCommandBuilder){
        // save the command, and wait for the next line
        if(savedCommandBuilder == null){
            savedCommandBuilder = uncompletedCommandBuilder;
        }
    }
    public static Command parseBuffer(Buffer buffer){

        try {
            String bufferString = buffer.toString();
            String[] bufferParts = bufferString.split(" ");

            String commandEntry = bufferParts[0].trim();
            String[] commandArguments = Arrays.copyOfRange(bufferParts, 1, bufferParts.length);


            if(savedCommandBuilder != null){
               return handleValueCommand(commandEntry);
            }


            CommandType commandType = CommandType.valueOf(commandEntry.toUpperCase());


            switch (commandType) {
                case  SET:
                    return handleSetCommand(commandEntry, commandArguments);
                case GET:
                case GETS:
                    return handleRetrievalCommand(commandEntry, commandArguments);
                case DELETE:
                    return handleDeleteCommand(commandEntry, commandArguments);
                case FLUSH_ALL:
                    return handleFlushCommand(commandEntry, commandArguments);
                case QUIT:
                    return handleQuitCommand(commandEntry, commandArguments);
                case HELP:
                    return handleHelpCommand(commandEntry, commandArguments);
                default:
                    return handleUnknownCommand(commandEntry, commandArguments);
            }
        }catch (Exception e) {
            throw new IllegalArgumentException(ParserErrors.BadCommand);
        }
    }

    private static Command handleSetCommand(String commandEntry, String[] commandArguments){

        if(commandArguments.length < 4){
            throw new IllegalArgumentException(ParserErrors.BadCommand);
        }

        // things to check
        // if value to be stored is larger than the size specified throw client error
        // if the flag is not an integer throw client error
        // if the exptime is not an integer throw client error
        // if the size is not an integer throw client error

        String key = commandArguments[0];
        Number flag = Integer.parseInt(commandArguments[1]);
        Number exptime = Integer.parseInt(commandArguments[2]);
        Number size = Integer.parseInt(commandArguments[3]);
        Boolean noreply = commandArguments.length == 5 && commandArguments[4].equals("noreply") ;

        CommandBuilder builder = new CommandBuilder();
        builder.setCommand(commandEntry);
        builder.setKeys(new String[]{key});
        builder.setFlag(flag);
        builder.setExptime(exptime);
        builder.setSize(size);
        builder.setNoreply(noreply);

        saveCommandBuilder(builder);

        return null;
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

    private static Command handleValueCommand(String commandEntry){
        try {
            savedCommandBuilder.setValue(commandEntry);
            return savedCommandBuilder.build();
        }catch (Exception e) {
            throw new IllegalArgumentException(ParserErrors.BadDataChunk);
        }
    }

    
}
