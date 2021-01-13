package com.allimu.mastercontroller.dao;

import com.allimu.mastercontroller.netty.model.DeviceBindInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceBindInfoDao {
    // 保存设备绑定信息到本地数据库
    public int saveDeviceBindInfo(DeviceBindInfo deviceBindInfo);

    // 获取未上传到云端的设备绑定信息
    public List<DeviceBindInfo> getDeviceBindInfo(@Param("schoolCode") Long schoolCode);

    // 更新设备绑定信息，设置为已上传
    public int updateDeviceBindInfoList(@Param("deviceBindInfoList") List<DeviceBindInfo> deviceBindInfoList);

    //根据temp_id查询
    public DeviceBindInfo getDeviceBindInfoByTempId(@Param("tempId") Long tempId);

    //根据temp_id修改
    public int updateDeviceBindInfoId(DeviceBindInfo deviceBindInfo);

}
