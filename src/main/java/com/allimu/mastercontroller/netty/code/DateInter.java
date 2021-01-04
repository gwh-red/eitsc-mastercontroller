package com.allimu.mastercontroller.netty.code;

import java.util.Date;



/*
 * 时间间隔
 */
public class DateInter {
	private static Date date;
	static {
		date=new Date();
	}

	public static Date getDate() {
		return date;
	}

	public static void setDate(Date date) {
		DateInter.date = date;
	}
	
}
