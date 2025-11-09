package com.tls.LiarWire;

public class Utils {

    private Utils() { /*private constructor*/ }


    public static String getKeyWithMethodAndEndpoint(String httpMethod, String endpoint){
        return httpMethod + ":::" + endpoint;
    }

}
