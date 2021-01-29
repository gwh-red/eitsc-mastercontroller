package com.allimu.mastercontroller.dao;


import com.allimu.mastercontroller.netty.model.DeviceInfraredInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceInfraredInfoDao {

    // 保存设备连接信息
    public int saveDerviceInfraredInfo(DeviceInfraredInfo deviceInfraredInfo);

    public DeviceInfraredInfo getDeviceInfraredInfoByAddress(@Param("address") Short address, @Param("endpoint") Byte endpoint, @Param("cols") Short cols,
                                                             @Param("rows") Byte rows);

    public List<DeviceInfraredInfo> getDeviceInfraredInfo(@Param("address") Short address, @Param("endpoint") Byte endpoint);

}
