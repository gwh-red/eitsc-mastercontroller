package com.allimu.mastercontroller.dao;



import org.apache.ibatis.annotations.Param;

import com.allimu.mastercontroller.netty.model.Equip;

public interface EquipDao {
	// 根据sn获取网关设备的详细信息
	public Equip getSnEquipBySn(@Param("sn") String sn);

	// 保存网关设备
	public int saveSnEquip(Equip EquipList);
	
	//根据设备编号获取网关设备
	public Equip getSnEquipByEquipmentCode(@Param("equipmentCode")String equipmentCode);
	
	// 根据sn码和学校编码获取网关信息
	public Equip getSnEquipByParams(@Param("sn") String sn,@Param("schoolCode") Long schoolCode);
	
}
