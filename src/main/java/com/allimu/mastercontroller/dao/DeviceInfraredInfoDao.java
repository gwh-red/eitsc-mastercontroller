package com.allimu.mastercontroller.dao;


import com.allimu.mastercontroller.netty.model.DeviceInfraredInfo;
import org.apache.ibatis.annotations.Param;

public interface DeviceInfraredInfoDao {

    // 保存设备连接信息
    public int saveDerviceInfraredInfo(DeviceInfraredInfo deviceInfraredInfo);

    public DeviceInfraredInfo getDeviceInfraredInfoByAddress(@Param("address") Short address,
                                                             @Param("rows") Byte rows);
}
