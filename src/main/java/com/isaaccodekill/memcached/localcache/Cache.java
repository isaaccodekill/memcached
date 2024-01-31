package com.isaaccodekill.memcached.localcache;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    // in here we will implement the core logic of our cache

    private Cache(){}
    private static Cache instance;


    // initialization on demand holder idiom
    // classes are not initialized until they are needed
    // class initialization is synchronized without any special effort

    private static class Holder{
        private static final Cache INSTANCE = new Cache();
    }


    public static Cache getInstance(){
        return Holder.INSTANCE;
    }

    private ConcurrentHashMap<String, HashMap<String, String>> cache = new ConcurrentHashMap<String, HashMap<String, String>>();



    public String set(String key, String value, Number flag, Number exptime, Number size)  {
        try {

            if (key == null || value == null) {
                throw new IllegalArgumentException("Key or value cannot be null");
            }

            HashMap<String, String> valueMap = new HashMap<>();
            valueMap.put("value", value);
            valueMap.put("flag", flag.toString());
            valueMap.put("exptime", exptime.toString());
            valueMap.put("size", size.toString());
            cache.put(key, valueMap);

            return CacheResponse.getSetResponse(key, value, flag.toString(), exptime, size.toString());
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public String get(String key){
        try {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null");
            }
            HashMap<String, String> valueMap = cache.get(key);

            if(valueMap == null){
                return CacheResponse.endResponse();
            }

            String value = valueMap.get("value");
            String flag = valueMap.get("flag");
            String exptime = valueMap.get("exptime");
            String size = valueMap.get("size");
            return CacheResponse.getGetResponse(key, value, flag, Integer.parseInt(exptime), size);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public String delete(String key){

        HashMap<String, String> valueMap = cache.remove(key);

        if(valueMap == null){
            return CacheResponse.notFoundResponse();
        }

        return  CacheResponse.getDeleteResponse();
    }

    public String flush_all(){
        cache.clear();
        return  CacheResponse.getOkResponse();
    }

}
