package com.allimu.mastercontroller.netty.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Mark EITSC一期项目--设备运维服务系统1.0--设备信息表实体
 * @author QiuXL
 * @version 1.0.9
 * @date 2019-08-29
 * **/
public class Equip implements Serializable {
    
	private static final long serialVersionUID = 171L;
	
	private Long id;			// 主键ID
	private Long regionCode;	// 所属区域编码
	private Long schoolCode;	// 所属学校编码
	private Long buildCode;		// 所属教学楼编码
	private Long classCode;		// 所属课室编码
	
	private String regionName;	// 所属区域名称
	private String schoolName;	// 所属学校名称
	private String buildName;	// 所属教学楼名称
	private String className;	// 所属课室名称
	
	private String manufName;	// 所属厂商名称
	private String brandName;	// 所属厂商品牌名称
	private String modelName;	// 所属厂商品牌型号名称
	private String typeName;	// 所属设备类型名称
	
	private String code;		// 设备固定资产编号
	private String name;		// 设备名称
	private String sn;			// 设备SN码
	private Short state;		// 设备状态，0表示待使用，1表示使用中，2表示维修中，3表示待报废
	private String remark;		// 备注说明
	private Date createTime;	// 创建时间
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(Long regionCode) {
		this.regionCode = regionCode;
	}
	public Long getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(Long schoolCode) {
		this.schoolCode = schoolCode;
	}
	public Long getBuildCode() {
		return buildCode;
	}
	public void setBuildCode(Long buildCode) {
		this.buildCode = buildCode;
	}
	public Long getClassCode() {
		return classCode;
	}
	public void setClassCode(Long classCode) {
		this.classCode = classCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getManufName() {
		return manufName;
	}
	public void setManufName(String manufName) {
		this.manufName = manufName;
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Short getState() {
		return state;
	}
	public void setState(Short state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return "Equip [id=" + id + ", regionCode=" + regionCode + ", schoolCode=" + schoolCode + ", buildCode="
				+ buildCode + ", classCode=" + classCode + ", regionName=" + regionName
				+ ", schoolName=" + schoolName + ", buildName=" + buildName + ", className=" + className
				+ ", manufName=" + manufName + ", brandName=" + brandName + ", modelName=" + modelName + ", typeName="
				+ typeName + ", code=" + code + ", name=" + name + ", sn=" + sn + ", state=" + state + ", remark="
				+ remark + ", createTime=" + createTime + "]";
	}
	
	
	
}