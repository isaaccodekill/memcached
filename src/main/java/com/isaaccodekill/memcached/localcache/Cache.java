package com.isaaccodekill.memcached.localcache;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

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

    private ConcurrentHashMap<String, HashMap<String, String>> cache = new ConcurrentHashMap<String, HashMap<String, String>>();



    public String set(String key, String value, String flag, Number exptime, String size)  {
        try {

            if (key == null || value == null) {
                throw new IllegalArgumentException("Key or value cannot be null");
            }

            HashMap<String, String> valueMap = new HashMap<String, String>();
            valueMap.put("value", value);
            valueMap.put("flag", flag);
            valueMap.put("exptime", exptime.toString());
            valueMap.put("size", size);
            cache.put(key, valueMap);

            return CacheResponse.getSetResponse(key, value, flag, exptime, size);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
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
