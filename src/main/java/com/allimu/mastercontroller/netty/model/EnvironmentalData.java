package com.allimu.mastercontroller.netty.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author OuYang
 *	环境数据类
 */
public class EnvironmentalData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	// 流水号id
	private Long id;
	// 学校编号
	private Long schoolCode;
	// 学校名称
	private String schoolName;
	// 教学楼编号
	private Long buildCode;
	// 教学楼名称
	private String buildName;
	// 课室编号
	private Long classRoomCode;
	// 课室名称
	private String classRoomName;
	// 环境数据类型
	private String type;
	// 数值
	private Float value;
	// 是否上传到云端
	private Boolean isUpload;
	// 单位
	private String unit;
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
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}
	
	public Boolean getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Boolean isUpload) {
		this.isUpload = isUpload;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return "EnvironmentalData [id=" + id + ", schoolCode=" + schoolCode + ", schoolName=" + schoolName
				+ ", buildCode=" + buildCode + ", buildName=" + buildName + ", classRoomCode=" + classRoomCode
				+ ", classRoomName=" + classRoomName + ", type=" + type + ", value=" + value + ", isUpload=" + isUpload
				+ ", unit=" + unit + "]";
	}
	
	

}
