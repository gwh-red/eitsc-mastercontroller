package com.allimu.mastercontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.allimu.mastercontroller.netty.model.DeviceBindInfo;

public interface DeviceBindInfoDao {
	// 保存设备绑定信息到本地数据库
	public int saveDeviceBindInfo(DeviceBindInfo deviceBindInfo);

	// 获取未上传到云端的设备绑定信息
	public List<DeviceBindInfo> getDeviceBindInfo(@Param("schoolCode")Long schoolCode);

	// 更新设备绑定信息，设置为已上传
	public int updateDeviceBindInfoList(@Param("deviceBindInfoList") List<DeviceBindInfo> deviceBindInfoList);
}
