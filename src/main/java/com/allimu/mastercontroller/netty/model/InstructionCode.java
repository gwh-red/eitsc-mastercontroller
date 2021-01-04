package com.allimu.mastercontroller.netty.model;

import java.io.Serializable;

/**
 * 
 * @author OuYang
 *	网关标准指令类
 */
public class InstructionCode implements Serializable{
	private static final long serialVersionUID = 1L;
	
	// 流水号id
	private Integer id;
	// 学校编号
	private Long schoolCode;
	// 指令码类型
	private Byte type;
	// 被控制sn码
	private String sn;
	// 设备短地址
	private Short address;
	// 设备端点地址
	private Byte endpoint;
	// 设备状态 开/关
	private Byte state;
	// 空调，窗帘，音量设置的大小
	private Byte value;
	//数据上传间隔
	private Short time;
	// 查询电箱耗电量开始时间
	private String startTime;
	// 查询电箱耗电量结束时间
	private String endTime;
	// 构建登录应答消息时候的ip
	private String ip;
	// 构建登录应答消息时候的port
	private Integer port;
	// 设备名称
	private String equipmentCode;
	//
	private Boolean isUpload;
	//红外指令
	private String redCode;
	
	// 红外控制指令
	private byte[] Data;
	// 红外控制指令长度
	private Byte Data_len;


	public String getRedCode() {
		return redCode;
	}

	public void setRedCode(String redCode) {
		this.redCode = redCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
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

	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public Short getTime() {
		return time;
	}

	public void setTime(Short time) {
		this.time = time;
	}
	
	public byte[] getData() {
		return Data;
	}
	public void setData(byte[] data) {
		Data = data;
	}

	public Byte getData_len() {
		return Data_len;
	}
	public void setData_len(Byte data_len) {
		Data_len = data_len;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("InstructionCode :");
		if (type != null)
			sb.append(" type=" + type);
		if (sn != null)
			sb.append(" sn=" + sn);
		if (address != null)
			sb.append(" address=" + address);
		if (endpoint != null)
			sb.append(" endpoint=" + endpoint);
		if (state != null)
			sb.append(" state=" + state);
		if (value != null)
			sb.append(" value=" + value);
		if (startTime != null)
			sb.append(" startTime=" + startTime);
		if (endTime != null)
			sb.append(" endTime=" + endTime);
		if (ip != null)
			sb.append(" ip=" + ip);
		if (port != null)
			sb.append(" port=" + port);
		if (equipmentCode != null)
			sb.append(" name=" + equipmentCode);
		if(Data != null) {
			sb.append(" Data="+Data);
		}
		if(Data_len != null) {
			sb.append(" Data_len="+Data_len);
		}
		return sb.toString();
	}

}
