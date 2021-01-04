package com.allimu.mastercontroller.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.allimu.mastercontroller.netty.model.EnvironmentalData;

public interface EnvironmentalDataDao {
	// 保存环境数据到本地数据库
	public int saveEnvironmentalData(EnvironmentalData environmentalData);

	// 获取未上传到云端的环境数据
	public List<EnvironmentalData> getEnvironmentalData(@Param("schoolCode") Long schoolCode);

	// 更新环境数据，设置为已上传
	public int updateEnvironmentalDataList(@Param("environmentalDataList")List<EnvironmentalData> environmentalDataList);
}
