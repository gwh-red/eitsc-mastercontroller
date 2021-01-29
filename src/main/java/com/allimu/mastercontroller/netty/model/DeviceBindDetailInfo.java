package com.allimu.mastercontroller.netty.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author OuYang
 * 设备连接信息类
 */
public class DeviceBindDetailInfo implements Serializable {

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
    //设备编号
    private String equipmentCode;
    //设备名称
    private String equipmentName;
    // 设备类型
    private String equipmentType;
    // 是否上传到云端
    private Boolean isUpload;
    // 短地址
    private Short address;
    // 端点地址
    private Byte endpoint;
    // sn码
    private String sn;
    // 设备类型
    private Short device;
    // ieee地址
    private Long ieee;
    //型号 低位在前
    private Short cols;
    //品牌
    private Byte rows;
    // 状态
    private Byte state;
    // 创建时间
    private Date createTime;

    public Short getCols() {
        return cols;
    }

    public void setCols(Short cols) {
        this.cols = cols;
    }

    public Byte getRows() {
        return rows;
    }

    public void setRows(Byte rows) {
        this.rows = rows;
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Short getDevice() {
        return device;
    }

    public void setDevice(Short device) {
        this.device = device;
    }

    public Long getIeee() {
        return ieee;
    }

    public void setIeee(Long ieee) {
        this.ieee = ieee;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public DeviceBindDetailInfo clone() {
        DeviceBindDetailInfo dbi = new DeviceBindDetailInfo();
        dbi.setSchoolCode(this.getSchoolCode());
        dbi.setSchoolName(this.getSchoolName());
        dbi.setBuildCode(this.getBuildCode());
        dbi.setBuildName(this.getBuildName());
        dbi.setClassRoomCode(this.getClassRoomCode());
        dbi.setClassRoomName(this.getClassRoomName());
        dbi.setAddress(this.address);
        dbi.setEndpoint(this.endpoint);
        dbi.setDevice(this.device);
        dbi.setSn(this.sn);
        dbi.setIeee(this.ieee);
        dbi.setState(this.state);
        dbi.setIsUpload(this.isUpload);
        dbi.setCreateTime(new Date());
        return dbi;
    }

    @Override
    public String toString() {
        return "DeviceBindDetailInfo{" +
                "id=" + id +
                ", schoolCode=" + schoolCode +
                ", schoolName='" + schoolName + '\'' +
                ", buildCode=" + buildCode +
                ", buildName='" + buildName + '\'' +
                ", classRoomCode=" + classRoomCode +
                ", classRoomName='" + classRoomName + '\'' +
                ", equipmentCode='" + equipmentCode + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", equipmentType='" + equipmentType + '\'' +
                ", isUpload=" + isUpload +
                ", address=" + address +
                ", endpoint=" + endpoint +
                ", sn='" + sn + '\'' +
                ", device=" + device +
                ", ieee=" + ieee +
                ", cols=" + cols +
                ", rows=" + rows +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
