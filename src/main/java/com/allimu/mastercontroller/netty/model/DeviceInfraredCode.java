package com.allimu.mastercontroller.netty.model;

import java.util.Date;

public class DeviceInfraredCode {

    private static final long serialVersionUID = 1L;

    //id
    private Long id;

    //学校编码
    private Long schoolCode;

    //品牌
    private String brand;

    //型号
    private String brandType;

    //操作类型
    private String operationType;

    //设备类型
    private String equipmentType;

    //码
    private String infraredCode;

    //创建时间
    private Date createTime;

    //修改时间
    private Date updateTime;

    //逻辑删除
    private Long deleted;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandType() {
        return brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType = brandType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getInfraredCode() {
        return infraredCode;
    }

    public void setInfraredCode(String infraredCode) {
        this.infraredCode = infraredCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

}
