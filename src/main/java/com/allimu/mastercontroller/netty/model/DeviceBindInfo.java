package com.allimu.mastercontroller.netty.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author OuYang
 *	临时绑定类
 */
public class DeviceBindInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long tempId;			// 临时id(等于详情id)
	private Long schoolCode;		// 学校编号
	private String schoolName;		// 学校名称
	private Long buildCode;			// 教学楼编号
	private String buildName;		// 教学楼名称
	private Long classRoomCode;		// 课室编号
	private String classRoomName;	// 课室名称
	private String equipmentType;	// 设备类型
	private Boolean isUpload;		// 是否上传到云端
	private String mark;			// mark=2,表示数据为网关产生
	private Date createTime;		// 创建时间

	
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

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public Boolean getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(Boolean isUpload) {
		this.isUpload = isUpload;
	}

	public Long getTempId() {
		return tempId;
	}

	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "DeviceBindInfo{" +
				"id=" + id +
				", tempId=" + tempId +
				", schoolCode=" + schoolCode +
				", schoolName='" + schoolName + '\'' +
				", buildCode=" + buildCode +
				", buildName='" + buildName + '\'' +
				", classRoomCode=" + classRoomCode +
				", classRoomName='" + classRoomName + '\'' +
				", equipmentType='" + equipmentType + '\'' +
				", isUpload=" + isUpload +
				", mark='" + mark + '\'' +
				", createTime=" + createTime +
				'}';
	}
}
