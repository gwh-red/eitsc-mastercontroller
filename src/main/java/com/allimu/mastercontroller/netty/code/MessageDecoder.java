package com.allimu.mastercontroller.netty.code;


import com.allimu.mastercontroller.netty.config.ChannelHandlerContextMapSn;
import com.allimu.mastercontroller.netty.config.SnMapContextSchoolCode;
import com.allimu.mastercontroller.netty.model.DeviceBindDetailInfo;
import com.allimu.mastercontroller.netty.model.DeviceInfraredInfo;
import com.allimu.mastercontroller.netty.model.Message;
import com.allimu.mastercontroller.netty.model.RedCodeRes;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.ReferenceCountUtil;

/**
 * 解码器
 *
 * @author OuYang Netty的LengthFieldBasedFrameDecoder解码器，它支持自动的TCP粘包和半包处理，
 * 只需要给出标识消息长度的字段偏移量和消息长度自身所占的字节数，Netty就能自动实现对半包的处理。
 */

public class MessageDecoder extends LengthFieldBasedFrameDecoder {


    public MessageDecoder() {
        super(1024 * 1024, 1, 1, 0, 0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // byte[] bytes = BuffConvertor.GetBytes(in);
        // 对于业务解码器来说，调用父类LengthFieldBasedFrameDecoder的解码方法后，返回的就是整包消息或者为空，
        // 如果为空说明是个半包消息，直接返回继续由I/O线程读取后续的码流
        in = (ByteBuf) super.decode(ctx, in);
        if (in == null) {
            return null;
        }
        if (in.readableBytes() < 1) {
            throw new Exception("字节数不足");
        }
        Message message = new Message();
        byteToMessage(message, in, ctx.channel().remoteAddress().toString());
        ReferenceCountUtil.release(in);//释放
        return message;
    }

    // 根据协议类型解码
    public void byteToMessage(Message message, ByteBuf in, String nodeIndex) {
        // read tag
        byte tag = in.readByte();
        message.setTag(tag);
        message.setSn(ChannelHandlerContextMapSn.getMapping(nodeIndex));
        // read length
        in.readByte();
        if (tag != (byte) 0x70) {
            System.out.println("MessageDecoder-tag----:" + tag);
        }
        // 根据tag解码 0x01这些是16进制
        if (tag == (byte) 0x01) {
            reciveAllDevice(message, in);
        } else if (tag == (byte) 0x07 || tag == (byte) 0xb2) {
            reciveDeviceState(message, in);
        } else if (tag == (byte) 0x70) {
            reciveSensorData(message, in);
        } else if (tag == (byte) 0xb5) {
            reciveElectric(message, in);
        } else if (tag == (byte) 0xb6) {
            reciveAutoElectric(message, in);
        } else if (tag == (byte) 0x1f) {
            heartBeat(message, in);
        } else if (tag == (byte) 0x18) {
            reciveGatewayDatetime(message, in);
        } else if (tag == (byte) 0x19) {
            synresult(message, in);
        } else if (tag == (byte) 0x31) {
            getError(message, in);
        } else if (tag == (byte) 0x71) {
            getRedCodeRes(message, in);
        } else if (tag == (byte) 0x73) {// 遥控器
            getIngraredInfo(message, in);
        }
    }

    // 遥控器
    private void getIngraredInfo(Message message, ByteBuf in) {
        DeviceInfraredInfo deviceInfraredInfo = new DeviceInfraredInfo();
        // read address
        deviceInfraredInfo.setAddress(in.readShortLE());
        // read endpoint
        deviceInfraredInfo.setEndpoint(in.readByte());
        // Device_Type
        deviceInfraredInfo.setDeviceType(in.readShortLE());
        //Remote_Type
        deviceInfraredInfo.setRemoteType(in.readShortLE());
        // Cols
        deviceInfraredInfo.setCols(in.readShortLE());
        //Rows
        deviceInfraredInfo.setRows(in.readByte());
        //NameLen
        deviceInfraredInfo.setNameLen(in.readByte());
        //Name
        deviceInfraredInfo.setName(TypeConverter.byteBufToHexString(in.readBytes(TypeConverter.byteToHexInt(deviceInfraredInfo.getNameLen()))));
        //System.out.println("deviceInfraredInfo:" + deviceInfraredInfo);
        message.setObject(deviceInfraredInfo);
    }

    //红外控制-应答数据结构
    private void getRedCodeRes(Message message, ByteBuf in) {
        RedCodeRes redCodeRes = new RedCodeRes();
        // read address
        redCodeRes.setAddress(in.readShortLE());
        // read endpoint
        redCodeRes.setEndpoint(in.readByte());
        // read state
        redCodeRes.setState(in.readByte());
        // sn
        redCodeRes.setSn(TypeConverter.byteBufToHexString(in.readBytes(in.readByte())));
        // set schoolcode
        redCodeRes.setSchoolCode(SnMapContextSchoolCode.getMapping(redCodeRes.getSn()));
        message.setObject(redCodeRes);
    }

    //获取当前连接的设备-应答数据结构
    private void reciveAllDevice(Message message, ByteBuf in) {
        DeviceBindDetailInfo deviceBindDetailInfo = new DeviceBindDetailInfo();
        // read address
        deviceBindDetailInfo.setAddress(in.readShortLE());
        // read endpoint
        deviceBindDetailInfo.setEndpoint(in.readByte());
        // read profile
        in.readShort();
        // read device
        deviceBindDetailInfo.setDevice(in.readShortLE());
        // read areaid
        in.readByte();
        // read name(pcCode)
        in.readBytes(in.readByte()).release();
        // read state
        deviceBindDetailInfo.setState(in.readByte());
        // read ieee
        deviceBindDetailInfo.setIeee(in.readLong());
        // read sn
        deviceBindDetailInfo.setSn(TypeConverter.byteBufToHexString(in.readBytes(in.readByte())));
        // set isUpload

        deviceBindDetailInfo.setIsUpload(false);
        // set schoolcode
        deviceBindDetailInfo.setSchoolCode(SnMapContextSchoolCode.getMapping(deviceBindDetailInfo.getSn()));
        message.setObject(deviceBindDetailInfo);
    }

    //获取指定设备的开关状态 -应答数据结构
    private void reciveDeviceState(Message message, ByteBuf in) {
        // read address
        message.setAddress(in.readShortLE());
        // read endpoint
        message.setEndpoint(in.readByte());
        // read state
        message.setState(in.readByte());
    }

    //获取环境数据
    private void reciveSensorData(Message message, ByteBuf in) {
        // read address
        message.setAddress(in.readShortLE());
        // read endpoint
        message.setEndpoint(in.readByte());
        // read clusterid
        message.setClusterId(in.readShortLE());
        // read account
        in.readByte();
        // read attrid
        if (message.getClusterId() == (short) 0x0421) {
            message.setClusterId(in.readShortLE());
        } else {
            in.readShortLE();
        }
        // read type
        byte dataType = in.readByte();
        // read value
        if (dataType == (byte) 0x29) {
            message.setValue((in.readShortLE() & 0xffff) / 100f);
        } else if (dataType == (byte) 0x21) {
            message.setValue((in.readShortLE() & 0xffff) / 1f);
        } else if (dataType == (byte) 0x20) {
            message.setValue((in.readByte() & 0xff) / 1f);
        } else if (dataType == (byte) 0x23) {
            message.setValue((in.readShortLE() & 0xffff) / 1f);
        }
    }

    // 电箱或者移动插座耗电量解码
    private void reciveElectric(Message message, ByteBuf in) {
        // read address
        message.setAddress(in.readShortLE());
        // read endpoint
        message.setEndpoint(in.readByte());
        // read startTime
        in.readBytes(4).release();
        // read state ,保留2位小数
        message.setValue((in.readIntLE() & 0xffffffff) / 100f);
    }

    //区间电量返回协议
    //主机主动上传区间电量数据，无请求数据。插座打开和关闭期间产生的用电电量。
    //返回数据格式：
    private void reciveAutoElectric(Message message, ByteBuf in) {
        // read address
        message.setAddress(in.readShortLE());
        // read endpoint
        message.setEndpoint(in.readByte());
        // read state ,保留2位小数
        message.setValue((in.readIntLE() & 0xffffffff) / 100f);
    }

    //网关心跳
    private void heartBeat(Message message, ByteBuf in) {
        message.setResult(in.readByte());
    }

    //查询网关时间
    private void reciveGatewayDatetime(Message message, ByteBuf in) {
        // read min
        message.setMin(in.readByte());
        // read hour
        message.setHour(in.readByte());
        // read day
        message.setDay(in.readByte());
        // read month
        message.setMonth(in.readByte());
        // read year
        message.setYear((in.readShortLE() & 0xffff));
    }

    //同步网关时间
    private void synresult(Message message, ByteBuf in) {
        // read result
        message.setResult(in.readByte());
    }

    //网关崩溃自动返回信息
    private void getError(Message message, ByteBuf in) {
        // read result
        message.setResult(in.readByte());
    }
}
