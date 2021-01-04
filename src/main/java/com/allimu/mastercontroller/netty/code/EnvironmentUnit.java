package com.allimu.mastercontroller.netty.code;
import java.util.Map;
import java.util.HashMap;
/**
 * 环境单位
 */
public class EnvironmentUnit {
	private static Map<String, String> environmentUnit=new HashMap<String,String>();
	
	static {
		environmentUnit.put("CO2", "PPM");
		environmentUnit.put("PM10", "mg/m³");
		environmentUnit.put("PM2.5", "μg/m³");
		environmentUnit.put("温度", "℃");
		environmentUnit.put("湿度", "%");
		environmentUnit.put("甲醛", "mg/m3");
		environmentUnit.put("PM1.0", "mg/m³");	
		environmentUnit.put("光照", "Lux");
	}
	
	public static String getUnit(String type) {
		return environmentUnit.get(type);
	}
}
