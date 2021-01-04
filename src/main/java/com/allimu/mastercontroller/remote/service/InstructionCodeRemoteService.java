package com.allimu.mastercontroller.remote.service;

import java.util.List;

import com.allimu.mastercontroller.netty.model.DeviceBindInfo;
import com.allimu.mastercontroller.netty.model.DeviceState;
import com.allimu.mastercontroller.netty.model.ElectricityConsumption;
import com.allimu.mastercontroller.netty.model.EnvironmentalData;
import com.allimu.mastercontroller.netty.model.JkCode;
import com.allimu.mastercontroller.netty.model.JkTestCode;
import com.allimu.mastercontroller.netty.model.TempReflect;


/**
  * 供网关调用远程接口
 * @author ymsn
 * @date  2020年1月7日
 */
public interface InstructionCodeRemoteService {
	
	// 根据学校编码获取网关设备
	String getWgEquipBySchoolCode(Long schoolCode);
	// 获取临时id与设备编号的绑定
	List<TempReflect> getTempReflectByWg(Long schoolCode,String mark);
	// 更新临时绑定表
	void updateTempReflectByWg(TempReflect tempReflect);
	
	// 保存设备与网关的绑定信息到云端
	int saveDeviceBindInfoByWg(List<DeviceBindInfo> deviceBindInfoList,Long schoolCode);
	// 保存设备状态信息
	int saveDeviceState(List<DeviceState> deviceStateList,Long schoolCode);
	// 保存电箱耗电量
	int saveElectricityConsumption(List<ElectricityConsumption> electricityConsumption,Long schoolCode);	
	// 保存环境数据
	int saveEnvironmentalData (List<EnvironmentalData> environmentalData,Long schoolCode);
	
	
	// 根据学校编号获取指令
	List<JkCode> getJkCode(Long schoolCode);
	// 指令发送给网关后，设置该指令为已调用
	int updateJkCode(JkCode jkCode);	
		
	// 根据学校编号获取测试指令
	List<JkTestCode> getJkTestCode(Long schoolCode);
	// 指令发送给网关后，设置该指令为已调用
	int updateJkTestCode(JkTestCode jkTestCode);
		
	// 获取红外设备的数量
	int getReadDeviceCount(Long schoolCode, Long buildCode, Long classRoomCode);
	// 根据设备编号获取空调设备
	String getAirEquipByEquipmentCode(Long schoolCode,String equipmentCode);
	
	/**
	 * 保存或更新网关状态
	 * @param schoolCode
	 * @param sn
	 * @param state 1=开启 2=离线
	 * @return
	 */
	int saveOrUpdateWgState(Long schoolCode,String sn,String state);
	
}
