package com.allimu.mastercontroller.netty.server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allimu.mastercontroller.netty.config.NettyServerInitializer;
import com.allimu.mastercontroller.util.CommonUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;
@Service
public class NettyServer implements Runnable {
//	private int port;
	private Channel channel;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private Thread nserver;
	
	@Autowired
	NettyServerInitializer nettyServerInitializer;

	public void init() {
		nserver = new Thread(this);
    	nserver.start();
    	
	}
	
	public void destory() {
		System.out.println("destroy server resources");
		if (null == channel) {
			System.out.println("server channel is null");
		}
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		channel.closeFuture().syncUninterruptibly();
		bossGroup = null;
		workerGroup = null;
		channel = null;
	}

//	public int getPort() {
//		return port;
//	}
//
//	public void setPort(int port) {
//		this.port = port;
//	}

	@Override
	public void run() { 
		bossGroup = new NioEventLoopGroup(2);
		workerGroup = new NioEventLoopGroup();
		System.out.println(Thread.currentThread().getName() + "----位置4");
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.option(ChannelOption.SO_BACKLOG, 512);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(nettyServerInitializer);
			ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
			// 服务器绑定端口监听
			ChannelFuture f = b.bind(CommonUtil.tcpServerPort).sync();
			f.channel().closeFuture().sync();
			channel = f.channel();
			// 可以简写为
			/* b.bind(portNumber).sync().channel().closeFuture().sync(); */
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
//	private static void addShutdownHook(final Thread checkThread) {
//		// 为了保证TaskThread不在中途退出，添加ShutdownHook
//		Runtime.getRuntime().addShutdownHook(new Thread() {
//			public void run() {
//				System.out.println("收到关闭信号，hook起动，开始检测线程状态 ...");
//				// 不断检测一次执行状态，如果线程一直没有执行完毕，超时后，放弃等待 \
//				for (int i = 0; i < 100; i++) {
//					if (checkThread.getState() == State.TERMINATED) {
//						System.out.println("检测到线程执行完毕，退出hook");
//						return;
//					}
//				}
//				System.out.println("检测超时，放弃等待，退出hook，此时线程会被强制关闭");
//			}
//		});
//	}
	
	
}


