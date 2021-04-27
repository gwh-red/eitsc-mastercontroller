package com.allimu.mastercontroller.util;

import com.allimu.mastercontroller.netty.code.TypeConverter;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


@Component
public class CommonUtil {
    /**
     * 学校编码
     */
    public static Long[] schoolCodes;
    /**
     * 学校名称
     */
    public static String[] schoolNames;
    /**
     * TCP服务端监听端口
     */
    public static Integer tcpServerPort;
    /**
     * 集控远程连接
     */
    public static String remoteServiceUrl;
    /**
     * 延迟时长
     */
    public static Long delay;

    static {

        Properties ps = new Properties();

        try {
            ps.load(new InputStreamReader(new FileInputStream("/usr/local/wangguan-common.properties"), "UTF-8"));
            schoolCodes = TypeConverter.strToLongs(ps.getProperty("schoolCode"));
            schoolNames = TypeConverter.strToStrs(ps.getProperty("schoolName"));
            tcpServerPort = Integer.parseInt(ps.getProperty("tcpServerPort"));
            remoteServiceUrl = ps.getProperty("remoteServiceUrl");
            delay = Long.parseLong(ps.getProperty("delay"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       /*try {
            ps.load(new InputStreamReader(CommonUtil.class.getClassLoader().getResourceAsStream("wangguan-common.properties"), "UTF-8"));
            schoolCodes = TypeConverter.strToLongs(ps.getProperty("schoolCode"));
            schoolNames = TypeConverter.strToStrs(ps.getProperty("schoolName"));
            tcpServerPort = Integer.parseInt(ps.getProperty("tcpServerPort"));
            remoteServiceUrl = ps.getProperty("remoteServiceUrl");
            delay = Long.parseLong(ps.getProperty("delay"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


}
