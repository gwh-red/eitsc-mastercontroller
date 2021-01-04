package com.allimu.mastercontroller.netty.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.StringUtils;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author OuYang
 *	sn码与网关连接ChannelHandlerContext的映射
 */
public class SnMapChannelHandlerContext {
	private static Map<String, ChannelHandlerContext> snMapChannelHandlerContext = new ConcurrentHashMap<String, ChannelHandlerContext>();

	public static void setMapping(String sn, ChannelHandlerContext ctx) {
		snMapChannelHandlerContext.put(sn , ctx);
	}

	public static ChannelHandlerContext getMapping(String sn) { 
		return snMapChannelHandlerContext.get(sn);
	}

	public static void removeMapping(String sn) {
		if(!StringUtils.isEmpty(sn)) {
			snMapChannelHandlerContext.remove(sn);
		}
	}
	
	public static Map<String, ChannelHandlerContext> getMap() {
		return snMapChannelHandlerContext;
	}
}
