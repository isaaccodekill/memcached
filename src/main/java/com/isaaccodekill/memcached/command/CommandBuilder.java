package com.isaaccodekill.memcached.command;

public class CommandBuilder {

    private Command command;
    public CommandBuilder(){
        this.command = new Command();
    }

    public  CommandBuilder setCommand(String command){
        this.command.command = command;
        return this;
    }

    public CommandBuilder setKeys(String[] keys){
        this.command.keys = keys;
        return this;
    }

    public CommandBuilder setFlag(Number flag){
        this.command.flag = flag;
        return this;
    }

    public CommandBuilder setExptime(Number exptime){
        this.command.exptime = exptime;
        return this;
    }

    public Command setSize(Number size){
        this.command.size = size;
        return this.command;
    }

    public CommandBuilder setNoreply(Boolean noreply){
        this.command.noreply = noreply;
        return this;
    }

    public CommandBuilder setValue(String value){
        if(value.length() > this.command.size.intValue()){
            throw new IllegalArgumentException("Value cannot be greater than size");
        }
        this.command.value = value;
        return this;
    }

    public Command build(){
        return this.command;
    }

}
