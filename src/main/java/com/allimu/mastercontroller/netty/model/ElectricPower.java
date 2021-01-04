package com.allimu.mastercontroller.netty.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author OuYang
 *	智能插座断电功率类
 */
public class ElectricPower implements Serializable{
	
	private static final long serialVersionUID = 1L;
	// 流水号id
	private Long id;
	// 学校编号
	private Long schoolCode;
	// 网关sn号
	private String sn;
	// 短地址
	private Short address;
	// 端点地址
	private Byte endpoint;
	// 断电功率
	private Float state;
	// 是否上传到云端
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

	public Boolean getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Boolean isUpload) {
		this.isUpload = isUpload;
	}

	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}

	public Short getAddress() {
		return address;
	}
	public void setAddress(Short address) {
		this.address = address;
	}

	public Byte getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(Byte endpoint) {
		this.endpoint = endpoint;
	}

	public Float getState() {
		return state;
	}
	public void setState(Float state) {
		this.state = state;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return "ElectricPower [sn=" + sn + ", address=" + address + ", endpoint=" + endpoint + ", state=" + state + "]";
	}

}
