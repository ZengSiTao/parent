package com.linktop.cloud.commonutils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HashMapStore {
    public final static String KEY_DEVCFG_DEFAULT_FN_ON_SERVER = "keyDevCfgDefaultFnOnServer";

    private Map<String, String> mapStringString = new HashMap<String, String>();

    public void putString(String key, String value) {
        mapStringString.put(key, value);
    }

    public String getString(String key) {
        return mapStringString.get(key);
    }
}
