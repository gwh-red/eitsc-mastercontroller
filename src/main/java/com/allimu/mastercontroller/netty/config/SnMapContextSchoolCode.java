package com.allimu.mastercontroller.netty.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sn码和学校码的映射
 */
public class SnMapContextSchoolCode {
    private static Map<String, Long> snMapContextSchoolCode = new ConcurrentHashMap<String, Long>();

    public static void setMapping(String sn, Long schoolCode) {
        snMapContextSchoolCode.put(sn, schoolCode);
    }

    public static Long getMapping(String sn) {
        return snMapContextSchoolCode.get(sn);
    }

    public static void removeMapping(String sn) {
        snMapContextSchoolCode.remove(sn);
    }
}
