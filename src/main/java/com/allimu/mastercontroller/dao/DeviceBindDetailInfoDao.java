package com.allimu.mastercontroller.dao;

import com.allimu.mastercontroller.netty.model.DeviceBindDetailInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceBindDetailInfoDao {
    // 根据ieee和endpoint查找设备连接信息
    public DeviceBindDetailInfo getDeviceBindDetailInfoByFactorys(@Param("sn") String sn,
                                                                  @Param("address") Short address, @Param("endpoint") Byte endpoint);

    public List<DeviceBindDetailInfo> getDeviceBindDetailInfoByList(@Param("sn") String sn,
                                                                    @Param("address") Short address, @Param("endpoint") Byte endpoint);


    // 保存设备连接信息
    public int saveDeviceBindDetailInfo(DeviceBindDetailInfo deviceBindDetailInfo);

    // 根据设备编号获取设备连接信息
    public DeviceBindDetailInfo getDeviceBindDetailInfoByEquipmentCode(@Param("equipmentCode") String equipmentCode);

    // 根据主键id获取设备连接信息
    public DeviceBindDetailInfo getDeviceBindDetailInfoByTempId(@Param("id") Long tempId);

    // 获取电箱设备
    public List<DeviceBindDetailInfo> getEleDevice(Long schoolCode);

    // 更新设备连接信息 如添加位置信息 设备编号 将是否已上传更新为是
    public int updateDeviceBindDetailInfo(DeviceBindDetailInfo deviceBindDetailInfo);

    // 根据设备ieee地址和端点地址查询设备连接信息
    public List<DeviceBindDetailInfo> getDeviceBindDetailInfoListByIeee(@Param("sn") String sn, @Param("ieee") Long ieee,
                                                                        @Param("endpoint") Byte endpoint);

    // 根据sn码获取设备连接信息
    public List<DeviceBindDetailInfo> getDeviceBindDetailInfoBySn(@Param("sn") String sn);

    //根据ieee获取红外设备连接信息
    public List<DeviceBindDetailInfo> getReadDevice(@Param("ieee") Long ieee);

    //根据ieee+endpoint获取设备连接信息
    public DeviceBindDetailInfo getDeviceBindDetailInfoByIeee(@Param("ieee") Long ieee,
                                                              @Param("endpoint") Byte endpoint);

    //根据ieee+endpoint+rows+clos获取设备连接信息
    public DeviceBindDetailInfo getDeviceBindDetailInfoByClos(@Param("address") Short address,
                                                              @Param("endpoint") Byte endpoint, @Param("cols") Short cols, @Param("rows") Byte rows);

    //根据ieee+endpoint+deviceInfraredInfoId获取设备连接信息
    public DeviceBindDetailInfo getDeviceBindDetailInfoByDeviceInfraredInfoId(@Param("address") Short address,
                                                                              @Param("endpoint") Byte endpoint, @Param("deviceInfraredInfoId") Long deviceInfraredInfoId);


}
