package com.allimu.mastercontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.allimu.mastercontroller.netty.model.ElectricityConsumption;

public interface ElectricityConsumptionDao {
	// 保存耗电量信息到本地数据库
	public int saveElectricityConsumption(ElectricityConsumption electricityConsumption);

	// 获取未上传到云端的耗电量信息
	public List<ElectricityConsumption> getElectricityConsumption(@Param("schoolCode") Long schoolCode);

	// 更新设备耗电量信息，设置为已上传
	public int updateElectricityConsumptionList(@Param("electricityConsumptionList")
	List<ElectricityConsumption> electricityConsumptionList);
	
	
}
