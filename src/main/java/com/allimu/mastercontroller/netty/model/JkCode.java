package com.allimu.mastercontroller.netty.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author OuYang 集控正式指令类
 */
public class JkCode implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long schoolCode;		// 学校编号
	private String equipmentCode;	// 设备编号
	private String equipmentType;	// 设备类型
	private String type;			// 操作类型 (例如:开,关,温度,模式,窗帘控制)
	private Byte value;				// 操作类型对应值 (关为0,开为1, 窗帘控制,音量等为设置值的大小,如50)
	private Boolean isUpload;		// 是否已调用
	private Date createTime; 		// 指令创建时间
	
	private String brandName; 		// 厂商品牌
	private String modelName;		// 品牌型号
	private Integer temperature; 	// 温度
	private Integer mode;			// 模式
	
	// 下面字段只是为了满足远程接口,避免导致远程更新时字段被设置为空
	private String userName;		// 用户名
	private Long userNumber;		// 用户编码
	private Long buildCode;			// 教学楼编码
	private Long classRoomCode;		// 课室编码

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(Long schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Byte getValue() {
		return value;
	}
	public void setValue(Byte value) {
		this.value = value;
	}

	public Boolean getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Boolean isUpload) {
		this.isUpload = isUpload;
	}
	
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Integer getTemperature() {
		return temperature;
	}
	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	
	

}
