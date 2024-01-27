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

    public CommandBuilder setFlag(String flag){
        this.command.flag = flag;
        return this;
    }

    public CommandBuilder setExptime(Number exptime){
        this.command.exptime = exptime;
        return this;
    }

    public Command setSize(String size){
        this.command.size = size;
        return this.command;
    }

    public Command build(){
        return this.command;
    }

}
