package com.allimu.mastercontroller.netty.code;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 常用空调品牌对应码库
 * @author ymsn
 */
public class AirBrandMap {
	
	
	public static void main(String[] args) {
		DecimalFormat df=new DecimalFormat("0.0");
		String format = df.format((float)5490/100);
		System.out.println(format);
	}
	
	private static Map<String, Integer> brandMap = new HashMap<String,Integer>();
	
	static {
		brandMap.put("美的", 0);
		brandMap.put("东芝", 1);
		brandMap.put("海尔", 2);
		brandMap.put("格力", 3);
		brandMap.put("志高", 4);
		brandMap.put("奥克斯", 5);
		brandMap.put("春兰", 6);
		brandMap.put("LG", 7);
		brandMap.put("TCL", 8);
		brandMap.put("三星", 9);
		brandMap.put("小三星", 10);
		brandMap.put("三洋", 11);
		brandMap.put("三凌重工", 12);
		brandMap.put("三凌机电", 13);
		brandMap.put("长虹", 14);
		brandMap.put("松下", 15);
		brandMap.put("乐声", 16);
		brandMap.put("内田", 17);
		brandMap.put("海信", 18);
		brandMap.put("新科", 19);
		brandMap.put("日立", 20);
		brandMap.put("华凌", 21);
		brandMap.put("科龙", 22);
		brandMap.put("华宝", 23);
		brandMap.put("威力", 24);
		brandMap.put("长岭", 25);
		brandMap.put("迎燕", 26);
		brandMap.put("蓝波", 27);
		brandMap.put("玉兔", 28);
		brandMap.put("开利", 29);
		brandMap.put("东宝", 30);
		brandMap.put("高路华", 31);
		brandMap.put("澳柯玛", 32);
		brandMap.put("胜风1", 33);
		brandMap.put("扬子", 34);
		brandMap.put("惠而浦", 35);
		brandMap.put("大金", 36);
	}
	
	public static Integer getIndex(String brandName) {
		return brandMap.get(brandName);
	}

}
