package com.allimu.mastercontroller.netty.code;

import com.allimu.mastercontroller.dao.CodeReflectDao;
import com.allimu.mastercontroller.netty.model.InstructionCode;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;


/**
 * @author OuYang
 * 指令编码器
 */
@Component
@Sharable
public class MessageEncoder extends MessageToMessageEncoder<InstructionCode> {

    @Autowired
    CodeReflectDao codeReflectdao;

    @Override
    protected void encode(ChannelHandlerContext ctx, InstructionCode msg, List<Object> out) throws Exception {
        ByteBuf sendBuf = PooledByteBufAllocator.DEFAULT.buffer();
        instructionCodeToByte(sendBuf, msg);
        out.add(sendBuf);
    }

    //生成指令
    private void instructionCodeToByte(ByteBuf sendBuf, InstructionCode req) {
        byte type = req.getType();
        if (type == (byte) 0x81) {
            getAllDevice(sendBuf, req);
        } else if (type == (byte) 0x82 || type == (byte) 0xad) {
            setDeviceState(sendBuf, req);
        } else if (type == (byte) 0x85) {
            getOneDevice(sendBuf, req);
        } else if (type == (byte) 0x94) {
            changeDeviceName(sendBuf, req);
        } else if (type == (byte) 0xa3) {
            setPowerOutage(sendBuf, req);
        } else if (type == (byte) 0xa4) {
            getPowerOutage(sendBuf, req);
        } else if (type == (byte) 0xd1) {
            setCurtainsOrVolume(sendBuf, req);
        } else if (type == (byte) 0xd2) {
            getCurtainsOrVolume(sendBuf, req);
        } else if (type == (byte) 0xc9) {
            getGatewayDatetime(sendBuf, req);
        } else if (type == (byte) 0xca) {
            setGatewayDatetime(sendBuf, req);
        } else if (type == (byte) 0xa5) {
            getElectric(sendBuf, req);
        } else if (type == (byte) 0xa6) {
            clearElectric(sendBuf, req);
        } else if (type == (byte) 0x40) {
            sendLoginInfo(sendBuf, req);
        } else if (type == (byte) 0x1e) {
            sendHeartBeat(sendBuf, req);
        } else if (type == (byte) 0x8c) {
            sendRedCode(sendBuf, req);
        } else if (type == (byte) 0x9f) {
            addDevice(sendBuf, req);
        } else if (type == (byte) 0x9e) {
            setInterval(sendBuf, req);
        } else if (type == (byte) 0xa9) {// 获取红外控制器
            getAllInfrared(sendBuf, req);
        }
    }

    //设置设备数据上传间隔
    private void setInterval(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x0);
        sendBuf.writeByte(0x02);
        sendBuf.writeShortLE(req.getAddress());
        sendBuf.writeInt(0);
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getEndpoint());
        sendBuf.writeShort(0);
        sendBuf.writeShort((short) 0x0402);
        sendBuf.writeShort((short) 0x0000);
        sendBuf.writeByte((byte) 0x20);
        sendBuf.writeByte(req.getValue());
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
    }

    //添加设备
    private void addDevice(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
        sendBuf.setByte(10, sendBuf.readableBytes() - 11);
    }

    //网关心跳
    private void sendHeartBeat(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x00);
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
    }


    // 获取全部设备
    private void getAllDevice(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
    }

    // 获取红外控制器
    private void getAllInfrared(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
    }


    //设置指定设备的设备状态
    private void setDeviceState(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x0);
        sendBuf.writeByte(0x02);
        sendBuf.writeShortLE(req.getAddress());
        sendBuf.writeInt(0);
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getEndpoint());
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getState());
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
        sendBuf.setByte(10, sendBuf.readableBytes() - 11);
    }

    //获取指定设备的开关状态
    private void getOneDevice(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x0);
        sendBuf.writeByte(0x02);
        sendBuf.writeShortLE(req.getAddress());
        sendBuf.writeInt(0);
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getEndpoint());
        sendBuf.writeShort(0);
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
        sendBuf.setByte(10, sendBuf.readableBytes() - 11);
    }

    //更改指定设备名
    private void changeDeviceName(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x0);
        sendBuf.writeByte(0x02);
        sendBuf.writeShortLE(req.getAddress());
        sendBuf.writeByte(req.getEndpoint());
        sendBuf.writeByte(req.getEquipmentCode().getBytes().length);
        sendBuf.writeBytes(req.getEquipmentCode().getBytes());
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
        sendBuf.setByte(10, sendBuf.readableBytes() - 11);
    }

    //插座自动断电功率设置
    private void setPowerOutage(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x0);
        sendBuf.writeByte(0x02);
        sendBuf.writeShortLE(req.getAddress());
        sendBuf.writeInt(0);
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getEndpoint());
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getValue());
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
        sendBuf.setByte(10, sendBuf.readableBytes() - 11);
    }

    //查询插座自动断电功率
    private void getPowerOutage(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x0);
        sendBuf.writeByte(0x02);
        sendBuf.writeShortLE(req.getAddress());
        sendBuf.writeInt(0);
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getEndpoint());
        sendBuf.writeShort(0);
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
        sendBuf.setByte(10, sendBuf.readableBytes() - 11);
    }

    //设置窗帘位置或音乐音量
    private void setCurtainsOrVolume(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x0);
        sendBuf.writeByte(0x02);
        sendBuf.writeShortLE(req.getAddress());
        sendBuf.writeInt(0);
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getEndpoint());
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getValue());
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
        sendBuf.setByte(10, sendBuf.readableBytes() - 11);
    }

    //查询窗帘位置或音乐音量
    private void getCurtainsOrVolume(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x0);
        sendBuf.writeByte(0x02);
        sendBuf.writeShortLE(req.getAddress());
        sendBuf.writeInt(0);
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getEndpoint());
        sendBuf.writeShort(0);
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
        sendBuf.setByte(10, sendBuf.readableBytes() - 11);
    }

    //查询网关时间
    private void getGatewayDatetime(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
    }

    //同步网关时间
    private void setGatewayDatetime(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x0);
        String datetime = getDateTime();
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(datetime.substring(0, 2)));
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(datetime.substring(2, 4)));
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(datetime.substring(4, 6)));
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(datetime.substring(6, 8)));
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(datetime.substring(8, 12)));
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
        sendBuf.setByte(10, sendBuf.readableBytes() - 11);
    }

    //区间电量查询
    private void getElectric(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x0);
        sendBuf.writeByte(0x02);
        sendBuf.writeShortLE(req.getAddress());
        sendBuf.writeInt(0);
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getEndpoint());
        sendBuf.writeShort(0);
        sendBuf.writeBytes(TypeConverter.dateStrTobytes(req.getStartTime()));
        sendBuf.writeBytes(TypeConverter.dateStrTobytes(req.getEndTime()));
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
        sendBuf.setByte(10, sendBuf.readableBytes() - 11);
    }

    //电量清零
    private void clearElectric(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x0);
        sendBuf.writeByte(0x02);
        sendBuf.writeShortLE(req.getAddress());
        sendBuf.writeInt(0);
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getEndpoint());
        sendBuf.writeShort(0);
        sendBuf.writeBytes(TypeConverter.dateStrTobytes(req.getStartTime()));
        sendBuf.writeBytes(TypeConverter.dateStrTobytes(req.getEndTime()));
        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
        sendBuf.setByte(10, sendBuf.readableBytes() - 11);
    }

    //登陆、修改密码
    private void sendLoginInfo(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0);
        sendBuf.writeByte(req.getValue());
        sendBuf.writeByte(req.getIp().getBytes().length);
        sendBuf.writeBytes(req.getIp().getBytes());
        sendBuf.writeByte(Integer.BYTES);
        sendBuf.writeInt(req.getPort());
        sendBuf.setByte(1, (byte) (sendBuf.readableBytes() - 2));
    }

    //红外控制
    private void sendRedCode(ByteBuf sendBuf, InstructionCode req) {
        sendBuf.writeShort(0x0);
        sendBuf.writeBytes(TypeConverter.hexStrToBytes(req.getSn()));
        sendBuf.writeByte(0xfe);
        sendBuf.writeByte(req.getType());
        sendBuf.writeByte(0x0);
        sendBuf.writeByte(0x02);
        sendBuf.writeShortLE(req.getAddress());
        sendBuf.writeInt(0);
        sendBuf.writeShort(0);
        sendBuf.writeByte(req.getEndpoint());
        sendBuf.writeShort(0);

        sendBuf.writeByte((byte) req.getData().length);

        sendBuf.writeBytes(req.getData());

        sendBuf.setBytes(0, TypeConverter.shortToBytes((short) sendBuf.readableBytes()));
        sendBuf.setByte(10, sendBuf.readableBytes() - 11);

    }

    @Async
    public void printCode(ByteBuf sendBuf) {
        byte[] bytes = new byte[sendBuf.readableBytes()];
        sendBuf.readBytes(bytes);
        String str = TypeConverter.byteArrayToHexString(bytes);
    }

    private String getDateTime() {
        StringBuffer resultSb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.MINUTE) < 10) {
            resultSb.append("0");
            resultSb.append(cal.get(Calendar.MINUTE));
        } else {
            resultSb.append(cal.get(Calendar.MINUTE));
        }
        if (cal.get(Calendar.HOUR) < 10) {
            resultSb.append("0");
            resultSb.append(cal.get(Calendar.HOUR));
        } else {
            resultSb.append(cal.get(Calendar.HOUR));
        }
        if (cal.get(Calendar.DATE) < 10) {
            resultSb.append("0");
            resultSb.append(cal.get(Calendar.DATE));
        } else {
            resultSb.append(cal.get(Calendar.DATE));
        }
        if (cal.get(Calendar.MONTH) < 10) {
            resultSb.append("0");
            resultSb.append(cal.get(Calendar.MONTH));
        } else {
            resultSb.append(cal.get(Calendar.MONTH));
        }
        resultSb.append(cal.get(Calendar.YEAR));
        return resultSb.toString();
    }
}
