package com.allimu.mastercontroller.netty.model;
/**
 * 
 * @author OuYang
 *	应答消息解码类
 */
public class Message {
	//应答消息类型 如 0x01 是获取设备连接信息的应答消息
	private Byte tag;
	//网关号
	private String sn;
	//解码成实体
	private Object object;
	//登录消息辨识
	private Byte type;
	//短地址
	private Short address;
	//端点地址
	private Byte endpoint;
	//设备状态
	private Byte state;
	//环境数据上传辨别码，如0x0402是温度数据
	private Short clusterId;
	//值
	private Float value;
	//应答消息结果
	private Byte result;
	//年
	private Integer year;
	//月
	private Byte month;
	//日
	private Byte day;
	//时
	private Byte hour;
	//分
	private Byte min;
	//登录账号 默认admin
	private String name;
	//登录密码 MD5加密 默认admin
	private String password;
	public Byte getTag() {
		return tag;
	}
	public void setTag(Byte tag) {
		this.tag = tag;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
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
	public Short getClusterId() {
		return clusterId;
	}
	public void setClusterId(Short clusterId) {
		this.clusterId = clusterId;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}
	public Byte getResult() {
		return result;
	}
	public void setResult(Byte result) {
		this.result = result;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Byte getMonth() {
		return month;
	}
	public void setMonth(Byte month) {
		this.month = month;
	}
	public Byte getDay() {
		return day;
	}
	public void setDay(Byte day) {
		this.day = day;
	}
	public Byte getHour() {
		return hour;
	}
	public void setHour(Byte hour) {
		this.hour = hour;
	}
	public Byte getMin() {
		return min;
	}
	public void setMin(Byte min) {
		this.min = min;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Message [tag=" + tag + ", sn=" + sn + ", object=" + object + ", type=" + type + ", address=" + address
				+ ", endpoint=" + endpoint + ", state=" + state + ", clusterId=" + clusterId + ", value=" + value
				+ ", result=" + result + ", year=" + year + ", month=" + month + ", day=" + day + ", hour=" + hour
				+ ", min=" + min + ", name=" + name + ", password=" + password + "]";
	}
	
	
}
