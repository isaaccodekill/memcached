package com.isaaccodekill.memcached;

import java.util.HashMap;

public class Cache {
    // in here we will implement the core logic of our cache

    private Cache(){}
    private static Cache instance;


    public static Cache getInstance(){
        if(instance == null){
            instance = new Cache();
        }
        return instance;
    }

    private HashMap<String, Byte> cache = new HashMap<String, Byte>();


    public void set(String key, Byte value){
        cache.put(key, value);
    }
    public void get(String key){
        cache.get(key);
    }

    public void delete(String key){
        cache.remove(key);
    }

    public void flush(){
        cache.clear();
    }

}
