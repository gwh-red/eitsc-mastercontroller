package com.allimu.mastercontroller.netty.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author OuYang 集控测试指令类
 */
public class JkTestCode implements Serializable {
	private static final long serialVersionUID = 1L;
	//主键ID
	private Long id;
	//学校编码
	private Long schoolCode;
	//临时id
	private Long tempId;
	// 设备类型
	private String equipmentType;	
	// 操作类型(开,关)
	private String type;
	// 操作类型对应值(开=1/关=0)
	private Byte value;
	//该指令是否以调用
	private Boolean isUpload;
	//指令创建时间
	private Date createTime;
	
	// 下面字段只是为了满足远程接口,避免导致远程更新时字段被设置为空
	private String userName;		// 用户名
	private Long userNumber;		// 用户编码

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(Long schoolCode) {
		this.schoolCode = schoolCode;
	}

	public Long getTempId() {
		return tempId;
	}
	public void setTempId(Long tempId) {
		this.tempId = tempId;
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

	@Override
	public String toString() {
		return "JkTestCode [id=" + id + ", schoolCode=" + schoolCode + ", tempId=" + tempId + ", type=" + type
				+ ", value=" + value + ", isUpload=" + isUpload + ", createTime=" + createTime + "]";
	}

}
