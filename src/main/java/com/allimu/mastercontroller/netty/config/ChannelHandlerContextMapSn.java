package com.allimu.mastercontroller.netty.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 *	网关连接的ip+端口与sn码的映射
 */
public class ChannelHandlerContextMapSn {
	private static Map<String, String> channelHandlerContextMapSn = new ConcurrentHashMap<String, String>();

	public static void setMapping(String nodeIndex, String sn) {
		channelHandlerContextMapSn.put(nodeIndex, sn);
	}

	public static String getMapping(String nodeIndex) { 
		return channelHandlerContextMapSn.get(nodeIndex);
	}

	public static void removeMapping(String nodeIndex) {
		channelHandlerContextMapSn.remove(nodeIndex);
	}
}
