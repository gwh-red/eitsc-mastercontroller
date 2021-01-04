package com.allimu.mastercontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.allimu.mastercontroller.netty.model.DeviceState;

public interface DeviceStateDao {
	// 保存设备状态信息到本地数据库
	public int saveDeviceState(DeviceState deviceState);

	// 获取未上传到云端的设备状态信息
	public List<DeviceState> getDeviceState(@Param("schoolCode") Long schoolCode);

	// 更新设备状态信息，设置为已上传
	public int updateDeviceStateList(@Param("deviceStateList") List<DeviceState> deviceStateList);

	// 批量保存设备状态信息
	public int saveDeviceStateList(@Param("deviceStateList")List<DeviceState> deviceStateList);
	
	/**
	 * 获取某设备最新的一条状态记录
	 * @param schoolCode	学校编码
	 * @param equipmentCode	设备编码
	 * @param state			设备状态
	 * @return
	 */
	public DeviceState getNewestDeviceState(@Param("schoolCode") Long schoolCode,
			@Param("equipmentCode")String equipmentCode,@Param("state")Byte state);
}
