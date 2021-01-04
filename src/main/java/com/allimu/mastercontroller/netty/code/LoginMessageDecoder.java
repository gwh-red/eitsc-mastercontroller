package com.allimu.mastercontroller.netty.code;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import com.allimu.mastercontroller.netty.model.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.ReferenceCountUtil;

/**
 *
 * @author OuYang 客户端登录信息解码器
 */
public class LoginMessageDecoder extends LengthFieldBasedFrameDecoder {

	MessageDecoder messageDecoder = new MessageDecoder();

	public LoginMessageDecoder() {
		super(ByteOrder.LITTLE_ENDIAN, 1024 * 1024, 0, 2, -2, 0, true);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		// 对于业务解码器来说，调用父类LengthFieldBasedFrameDecoder的解码方法后，返回的就是整包消息或者为空，
		// 如果为空说明是个半包消息，直接返回继续由I/O线程读取后续的码流
		ByteBuf copyin = in.slice(0, in.readableBytes());
		copyin = (ByteBuf) super.decode(ctx, copyin);
		Message message = new Message();
		// 如果copyin为空，则说明没有读取到半包或者是其他信息
		if (copyin == null) {
			// 调用普通信息的解码器解码，返回res
			message = (Message) messageDecoder.decode(ctx, in);
			return message;
		}
		if (in.readableBytes() < 1) {
			throw new Exception("字节数不足");
		}
		copyin = in.slice(0, in.readableBytes());
		copyin = (ByteBuf) super.decode(ctx, copyin);
		copyin.readBytes(8);
		byte flag = copyin.readByte();
		byte type = copyin.readByte();
		// 二次判断是否是登录信息
		if (flag == (byte) 0xfe && (type == (byte) 0xaf || type == (byte) 0xbf || type == (byte) 0xae)) {
			in = (ByteBuf) super.decode(ctx, in);
			readIn(in, message);
			ReferenceCountUtil.release(copyin);
			return message;
		} else {
			return null;
		}
	}

	// 登录信息解码，获取该网关的sn 登录账号 密码
	public void readIn(ByteBuf in, Message message) {
		// read length
		in.readBytes(2).release();
		message.setSn(TypeConverter.byteBufToHexString(in.readBytes(6)));
		// read flag
		in.readByte();
		// read type
		message.setType(in.readByte());
		// read param_len
		in.readByte();
		// read name
		ByteBuf str = in.readBytes(in.readByte());
		message.setName(str.toString(Charset.forName("UTF-8")));
		// read pass
		str = in.readBytes(in.readByte());
		message.setPassword(str.toString(Charset.forName("UTF-8")));
		ReferenceCountUtil.release(in);
	}

}
