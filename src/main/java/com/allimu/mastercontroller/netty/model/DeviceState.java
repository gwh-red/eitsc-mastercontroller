package com.allimu.mastercontroller.netty.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author Administrator
 *	设备状态类
 */
public class DeviceState implements Serializable{
	
	private static final long serialVersionUID = 1L;
	// 流水号id
	private Long id;
	// 学校编号
	private Long schoolCode;
	//学校名称
	private String schoolName;
	//教学楼编号
	private Long buildCode;
	//教学楼名称
	private String buildName;
	//课室编号
	private Long classRoomCode;
	//课室名称
	private String classRoomName;
	//设备编号
	private String equipmentCode;
	//设备名称
	private String equipmentName;	
	// 状态
	private Byte state;
	//是否上传到云端
	private Boolean isUpload;
	// 创建时间
	private Date createTime;
	
	
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
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Long getBuildCode() {
		return buildCode;
	}
	public void setBuildCode(Long buildCode) {
		this.buildCode = buildCode;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public Long getClassRoomCode() {
		return classRoomCode;
	}
	public void setClassRoomCode(Long classRoomCode) {
		this.classRoomCode = classRoomCode;
	}
	public String getClassRoomName() {
		return classRoomName;
	}
	public void setClassRoomName(String classRoomName) {
		this.classRoomName = classRoomName;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	public Boolean getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Boolean isUpload) {
		this.isUpload = isUpload;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return "DeviceState [id=" + id + ", schoolCode=" + schoolCode + ", schoolName=" + schoolName + ", buildCode="
				+ buildCode + ", buildName=" + buildName + ", classRoomCode=" + classRoomCode + ", classRoomName="
				+ classRoomName + ", equipmentCode=" + equipmentCode + ", equipmentName=" + equipmentName + ", state="
				+ state + ", isUpload=" + isUpload + "]";
	}
	
}
