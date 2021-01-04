package com.allimu.mastercontroller.netty.config;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allimu.mastercontroller.netty.code.LoginMessageDecoder;
import com.allimu.mastercontroller.netty.code.MessageEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;


@Component
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {		

	@Autowired
	LoginHandler loginHandler;
	@Autowired
	MessageEncoder messageEncoder;
	@Autowired
	ServerHandler serverHandler;
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast("messageEncoder",messageEncoder);	
//		ch.pipeline().addLast("idleStateHandler",new IdleStateHandler(10,0,0,TimeUnit.SECONDS));
		ch.pipeline().addLast("loginMessageDecoder",new LoginMessageDecoder());
		ch.pipeline().addLast("loginHandler",loginHandler);
		ch.pipeline().addLast("serverHandler",serverHandler);
	}
}



