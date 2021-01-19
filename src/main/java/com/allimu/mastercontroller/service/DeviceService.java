package com.allimu.mastercontroller.service;

import com.allimu.mastercontroller.netty.model.Message;
import io.netty.channel.ChannelHandlerContext;

public interface DeviceService {
    // 保存设备连接信息
    public void saveDeviceBindInfo(Message message, ChannelHandlerContext ctx);

    // 保存环境数据
    public void saveEnvironmentalData(Message message);

    // 保存电箱耗电量
    public void saveElectricityConsumption(Message message);

    // 保存设备状态
    public void saveDeviceTrueState(Message message);

    // 异常处理，如果网关断线，设置全部网关底下的设备为离线状态
    public void exceptionHandling(String sn);

    public void saveDeviceInfraredInfo(Message message);
}
