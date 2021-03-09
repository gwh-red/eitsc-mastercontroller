package com.allimu.mastercontroller.netty.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HongWG
 * @Mark 红外映射信息
 * @date 2021-1-14
 **/
public class DeviceInfraredInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    // id
    private Long id;
    // 短地址
    private Short address;
    // 端点地址
    private Byte endpoint;
    // 设备类型
    private Short deviceType;
    //遥控器类型
    private Short remoteType;
    //型号 低位在前
    private Short cols;
    //品牌
    private Byte rows;
    //0-32名字长度
    private Byte nameLen;
    //names
    private String name;
    // sn码
    private String sn;
    //红外学码
    private String infraredCode;
    // 红外学码 是1不是0
    private Long isInfraredCode;
    // 创建时间
    private Date createTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Short getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Short deviceType) {
        this.deviceType = deviceType;
    }

    public Short getRemoteType() {
        return remoteType;
    }

    public void setRemoteType(Short remoteType) {
        this.remoteType = remoteType;
    }

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

    public Byte getNameLen() {
        return nameLen;
    }

    public void setNameLen(Byte nameLen) {
        this.nameLen = nameLen;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getInfraredCode() {
        return infraredCode;
    }

    public void setInfraredCode(String infraredCode) {
        this.infraredCode = infraredCode;
    }

    public Long getIsInfraredCode() {
        return isInfraredCode;
    }

    public void setIsInfraredCode(Long isInfraredCode) {
        this.isInfraredCode = isInfraredCode;
    }

    @Override
    public String toString() {
        return "DeviceInfraredInfo{" +
                "id=" + id +
                ", address=" + address +
                ", endpoint=" + endpoint +
                ", deviceType=" + deviceType +
                ", remoteType=" + remoteType +
                ", cols=" + cols +
                ", rows=" + rows +
                ", nameLen=" + nameLen +
                ", name='" + name + '\'' +
                ", sn='" + sn + '\'' +
                ", infraredCode='" + infraredCode + '\'' +
                ", isInfraredCode=" + isInfraredCode +
                ", createTime=" + createTime +
                '}';
    }
}
