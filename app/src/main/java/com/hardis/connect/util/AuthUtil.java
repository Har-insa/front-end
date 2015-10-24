package com.hardis.connect.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by A605221 on 25/08/2015.
 */
public final class AuthUtil {
    private AuthUtil() {

    }

    public static Map<String, String> getBasicAuth(String login, String password) {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("Content-Type","application//x-www-form-urlencoded");
        requestParams.put("email",login);
        requestParams.put("password", password);
        return requestParams;
    }
}