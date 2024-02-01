package com.isaaccodekill.memcached.parser;

import com.isaaccodekill.memcached.command.Command;
import com.isaaccodekill.memcached.command.CommandBuilder;
import com.isaaccodekill.memcached.command.CommandType;
import io.vertx.core.buffer.Buffer;

import java.util.Arrays;


// the goal of this is to handle multi line commands
// a normal command and then a value command
public class Parser {


    // not thread safe
    // TODO make this parser thread safe
    
    public static Command parseBuffer(Buffer buffer, Command savedCommand){

        try {
            String bufferString = buffer.toString();
            String[] bufferParts = bufferString.split(" ");

            // this handles multi line commands
            if(savedCommand != null){
                return handleValueCommand(bufferString, savedCommand);
            }

            String commandEntry = bufferParts[0].trim();
            String[] commandArguments = Arrays.copyOfRange(bufferParts, 1, bufferParts.length);



            CommandType commandType = CommandType.valueOf(commandEntry.toUpperCase());

            System.out.println("commandType: " + commandType + " " + commandEntry);

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
            System.out.println("Exception: " + e.getMessage());
            throw new IllegalArgumentException(ParserErrors.BadCommand);
        }
    }

    private static Command handleSetCommand(String commandEntry, String[] commandArguments){

        if(commandArguments.length < 4){
            throw new IllegalArgumentException(ParserErrors.BadCommand);
        }

        // things to check
        // key should not be more than 250 characters
        // if value to be stored is larger than the size specified throw client error
        // if the flag is not an integer throw client error
        // if the exptime is not an integer throw client error
        // if the size is not an integer throw client error

        String key = commandArguments[0];
        CommandBuilder builder = getMultiLineCommandBuilder(commandEntry, commandArguments, key);

        return saveCommand(builder);
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

    private static Command handleValueCommand(String valueEntry, Command savedCommand){
        try {

            if(valueEntry.length() > savedCommand.size.intValue()){
                throw new IllegalArgumentException(ParserErrors.BadDataChunk);
            }

            CommandBuilder builder = new CommandBuilder(savedCommand);
            builder.setValue(valueEntry);
            return builder.build();

        }catch (Exception e) {
            throw new IllegalArgumentException(ParserErrors.BadDataChunk);
        }
    }

    private static CommandBuilder getMultiLineCommandBuilder(String commandEntry, String[] commandArguments, String key) {
        Number flag = Integer.parseInt(commandArguments[1].trim());
        Number exptime = Integer.parseInt(commandArguments[2].trim());
        Number size = Integer.parseInt(commandArguments[3].trim());
        Boolean noreply = commandArguments.length == 5 && commandArguments[4].equals("noreply") ;

        CommandBuilder builder = new CommandBuilder();
        builder.setCommand(commandEntry);
        builder.setKeys(new String[]{key});
        builder.setFlag(flag);
        builder.setExptime(exptime);
        builder.setSize(size);
        builder.setNoreply(noreply);
        return builder;
    }
    private static Command saveCommand (CommandBuilder commandBuilder){
        commandBuilder.setIncomplete(true);
        return commandBuilder.build();
    }

    
}
