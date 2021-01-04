package com.allimu.mastercontroller.netty.config;


import com.allimu.mastercontroller.netty.model.Message;
import com.allimu.mastercontroller.service.DeviceService;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author OuYang
 * 业务逻辑处理器
 */
@Component
@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private DeviceService deviceService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        byte tag = message.getTag();

        System.out.println("接收------------------------");

        if (tag == (byte) 0x07 || tag == (byte) 0xb2) {
            deviceService.saveDeviceTrueState(message);
        } else if (tag == (byte) 0xb6 || tag == (byte) 0xb5) {
            deviceService.saveElectricityConsumption(message);
        } else if (tag == (byte) 0x70) {
            deviceService.saveEnvironmentalData(message);
        } else if (tag == (byte) 0x01) {
            deviceService.saveDeviceBindInfo(message, ctx);
        } else if (tag == (byte) 0x1f) {
            System.out.println(">> 心跳检测消息：" + message);
        } else if (tag == (byte) 0x18) {
            System.out.println(">> 接收到客户端时间成功：" + message);
        } else if (tag == (byte) 0x19) {
            System.out.println(">> 接收到客户端同步时间操作成功：" + message);
        } else if (tag == (byte) 0x31) {
            System.out.println(">> 接收到客户端崩溃消息：" + message);
        } else if (tag == (byte) 0x71) {
            System.out.println(">> 设置红外指令成功  ：" + message);
        }
        ReferenceCountUtil.release(msg);
    }

}
