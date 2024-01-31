package com.isaaccodekill.memcached.localcache;

import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    // in here we will implement the core logic of our cache

    // bruh sigh

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

            long exptimeUnix = System.currentTimeMillis() / 1000L + exptime.longValue();

            HashMap<String, String> valueMap = new HashMap<>();
            valueMap.put("value", value);
            valueMap.put("flag", flag.toString());
            valueMap.put("exptime", exptime.toString());
            valueMap.put("exptimeunix", Long.toString(exptimeUnix));
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

            if(checkIfKeyExpired(key)){
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


    private Boolean checkIfKeyExpired(String key){
        HashMap<String, String> valueMap = cache.get(key);

        // if the exptime is 0 then it never expires
        if(valueMap.get("exptime").equals("0")){
            return false;
        }

        // compare current time with exptime
        if(   System.currentTimeMillis() / 1000L > Long.parseLong(valueMap.get("exptimeunix")) ){
            cache.remove(key);
            return true;
        }

        return false;
    }







}
