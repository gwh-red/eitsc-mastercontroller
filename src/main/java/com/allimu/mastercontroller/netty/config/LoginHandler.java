package com.allimu.mastercontroller.netty.config;

import com.allimu.mastercontroller.netty.code.MD5Tools;
import com.allimu.mastercontroller.netty.model.InstructionCode;
import com.allimu.mastercontroller.netty.model.Message;
import com.allimu.mastercontroller.remote.service.InstructionCodeRemoteService;
import com.allimu.mastercontroller.service.DeviceService;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Sharable
public class LoginHandler extends SimpleChannelInboundHandler<Message> {

    private static Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private InstructionCodeRemoteService instructionCodeRemoteService;

    //private Long schoolCode = CommonUtil.schoolCode;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {

        if (message.getType() != null) {
            String name = message.getName();
            String password = message.getPassword();
            String sn = message.getSn();
            System.out.println("客户端" + ctx.channel().remoteAddress() + "的登录消息 ：" + message);
            // 登录验证，账号密码验证
            if (name != null && password != null && "admin".equals(name)
                    && password.equals(MD5Tools.string2MD5("admin"))) {
                String nodeIndex = ctx.channel().remoteAddress().toString();
                // 重复登陆，拒绝
                if (nodeCheck.containsKey(nodeIndex)) {
                    System.out.println(">> 重复登录...");
                    buildResponse((byte) 0x04, ctx);
                } else {
                    boolean isOK = true;
                    if (isOK) {
                        buildResponse((byte) 0x00, ctx);
                    } else {
                        buildResponse((byte) 0x04, ctx);
                    }
                    if (isOK) {
                        // 设置ChannelHandlerContext与sn的映射
                        ChannelHandlerContextMapSn.setMapping(nodeIndex, sn);
                        // 设置ChannelHandlerContext与sn的映射
                        SnMapChannelHandlerContext.setMapping(sn, ctx);
                        System.out.println(sn + "    " + ctx);
                        System.out.println(sn + "    " + SnMapContextSchoolCode.getMapping(sn));
                        // 设置该ChannelHandlerContext与已经登录
                        nodeCheck.put(nodeIndex, true);
                        // 登录成功后发送指令获取红外设备
                        getAllInfrared(sn);

                        Thread.sleep(1000);
                        // 登录成功后发送指令获取该网关下的设备
                        getAllDevice(sn);

                        // 更新或保存物联网关状态
                        instructionCodeRemoteService.saveOrUpdateWgState(SnMapContextSchoolCode.getMapping(sn), sn, "1");
                    }
                }
            }
            ReferenceCountUtil.release(message);
        } else {
            ctx.fireChannelRead(message);
        }
    }

    private void buildResponse(byte result, ChannelHandlerContext ctx) {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().localAddress();
        // 构建登录应答消息
        InstructionCode loginResponse = new InstructionCode();
        loginResponse.setType((byte) 0x40);
        loginResponse.setIp(address.getAddress().getHostAddress());
        loginResponse.setPort(address.getPort());
        // 根据result设置type
        if (result == 0x04) {
            loginResponse.setValue((byte) 0x04);
            ctx.writeAndFlush(loginResponse);
            ctx.close();
        } else if (result == 0x00) {
            loginResponse.setValue((byte) 0x00);
            ctx.writeAndFlush(loginResponse);
        }
        System.out.println("客户端" + ctx.channel().remoteAddress() + "的登录应答消息为 : " + loginResponse);
    }

    //获取网关下的设备
    public void getAllDevice(String sn) {
        ChannelHandlerContext ctx = SnMapChannelHandlerContext.getMapping(sn);
        InstructionCode getAllDeviceCode = new InstructionCode();
        getAllDeviceCode.setType((byte) 0x81);
        getAllDeviceCode.setSn(sn);
        System.out.println("发送指令获取客户端" + ctx.channel().remoteAddress() + "的设备绑定信息 :" + getAllDeviceCode);
        ctx.writeAndFlush(getAllDeviceCode);
    }

    //获取遥控器
    private void getAllInfrared(String sn) {
        ChannelHandlerContext ctx = SnMapChannelHandlerContext.getMapping(sn);
        InstructionCode getAllDeviceCode = new InstructionCode();
        getAllDeviceCode.setType((byte) 0xa9);
        getAllDeviceCode.setSn(sn);
        System.out.println("发送指令获取客户端的遥控器" + ctx.channel().remoteAddress() + "的设备绑定信息 :" + getAllDeviceCode);
        ctx.writeAndFlush(getAllDeviceCode);
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " channelRegistered ");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println(" >> 通道关闭... " + ctx.channel().remoteAddress());

        // 更新或保存物联网关状态
        String sn = ChannelHandlerContextMapSn.getMapping(ctx.channel().remoteAddress().toString());
        instructionCodeRemoteService.saveOrUpdateWgState(SnMapContextSchoolCode.getMapping(sn), sn, "2");

        // 断网处理
        deviceService
                .exceptionHandling(ChannelHandlerContextMapSn.getMapping(ctx.channel().remoteAddress().toString()));
        // 删除缓存
        SnMapChannelHandlerContext
                .removeMapping(ChannelHandlerContextMapSn.getMapping(ctx.channel().remoteAddress().toString()));
        ChannelHandlerContextMapSn.removeMapping(ctx.channel().remoteAddress().toString());
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        // 移除静态组
        channels.remove(ctx.channel());
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive:" + ctx.channel().remoteAddress() + " channelActive ");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive:" + ctx.channel().remoteAddress() + " channelInactive ");
        super.channelInactive(ctx);
    }

    /**
     * 服务端感知到断连事件之后，需要清空缓存的登录认证注册信息，以保证后续客户端 能够正常重连。
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(">> 网关断开连接了...");

        // 更新或保存物联网关状态
        String sn = ChannelHandlerContextMapSn.getMapping(ctx.channel().remoteAddress().toString());
        instructionCodeRemoteService.saveOrUpdateWgState(SnMapContextSchoolCode.getMapping(sn), sn, "2");

        // 断网处理
        deviceService
                .exceptionHandling(ChannelHandlerContextMapSn.getMapping(ctx.channel().remoteAddress().toString()));
        // 删除缓存
        SnMapChannelHandlerContext
                .removeMapping(ChannelHandlerContextMapSn.getMapping(ctx.channel().remoteAddress().toString()));
        ChannelHandlerContextMapSn.removeMapping(ctx.channel().remoteAddress().toString());
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        // 移除静态组
        channels.remove(ctx.channel());
        cause.printStackTrace();
        // 关闭链接
        ctx.close();
        ctx.fireExceptionCaught(cause);

    }

    /**
     * 断网断线处理
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(">> 网关断网了...");

        // 更新或保存物联网关状态
        String sn = ChannelHandlerContextMapSn.getMapping(ctx.channel().remoteAddress().toString());
        instructionCodeRemoteService.saveOrUpdateWgState(SnMapContextSchoolCode.getMapping(sn), sn, "2");

        // 断网处理
        deviceService
                .exceptionHandling(ChannelHandlerContextMapSn.getMapping(ctx.channel().remoteAddress().toString()));
        // 删除缓存
        SnMapChannelHandlerContext
                .removeMapping(ChannelHandlerContextMapSn.getMapping(ctx.channel().remoteAddress().toString()));
        ChannelHandlerContextMapSn.removeMapping(ctx.channel().remoteAddress().toString());
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        // 移除静态组
        channels.remove(ctx.channel());
        super.handlerRemoved(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            System.out.println(">> 网关心跳超时...");
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                // 更新或保存物联网关状态
                String sn = ChannelHandlerContextMapSn.getMapping(ctx.channel().remoteAddress().toString());
                instructionCodeRemoteService.saveOrUpdateWgState(SnMapContextSchoolCode.getMapping(sn), sn, "2");

                // 断网处理
                deviceService
                        .exceptionHandling(ChannelHandlerContextMapSn.getMapping(ctx.channel().remoteAddress().toString()));
                // 删除缓存
                SnMapChannelHandlerContext
                        .removeMapping(ChannelHandlerContextMapSn.getMapping(ctx.channel().remoteAddress().toString()));
                ChannelHandlerContextMapSn.removeMapping(ctx.channel().remoteAddress().toString());
                nodeCheck.remove(ctx.channel().remoteAddress().toString());
                // 移除静态组
                channels.remove(ctx.channel());
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
