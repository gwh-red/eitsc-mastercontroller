package com.allimu.mastercontroller.dao;



import org.apache.ibatis.annotations.Param;




public interface CodeReflectDao {	
	// 根据环境类型的clusterid查询相应的类型 如0x0402 是温度数据
	public String getEnviromentType(@Param("clusterid") Short clusterId);	
	//根据开关设备的类型，查询相应的开关码 普通开关设备 1对应开 0对应关 传感器设备 2代表关 3代表开 
	byte getSwitchReflect(@Param("type") Byte type, @Param("state") Byte state);
	//根据设备连接信息的device获取设备的类型 如0x0002是电灯
	public String getEquipmentType(@Param("device") Short device);
	//str指令与具体的指令映射  如开关设备开关指令 对应0x82 
	public byte getCodeTypeByType(@Param("type")String type);

}
