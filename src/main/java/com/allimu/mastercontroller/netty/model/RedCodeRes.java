package com.allimu.mastercontroller.netty.model;

public class RedCodeRes {
	private Long id;
	// 学校编号
	private Long schoolCode;
	// 网关sn号
	private String sn;
	// 短地址
	private Short address;
	// 端点地址
	private Byte endpoint;
	// 状态
	private Byte state;
	
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
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	
	
}
