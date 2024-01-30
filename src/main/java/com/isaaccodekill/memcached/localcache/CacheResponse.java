package com.isaaccodekill.memcached.localcache;

public class CacheResponse {

    public static String getGetResponse(String key, String value, String flag, Number exptime, String size){
        return "VALUE " + key + " " + flag + " " + size + "\r\n" + value + "\r\n" + "END\r\n";
    }

    public static String getSetResponse(String key, String value, String flag, Number exptime, String size){
        return "STORED\r\n";
    }

    public static String getDeleteResponse(String key, String value, String flag, Number exptime, String size){
        return "DELETED\r\n";
    }


}
