package com.allimu.mastercontroller.dao;

import com.allimu.mastercontroller.netty.model.DeviceInfraredCode;
import org.apache.ibatis.annotations.Param;

public interface DeviceInfraredCodeDao {

    /**
     * 根据 设备类型,品牌,型号,操作类型,学校编码
     * .获取指令
     *
     * @return
     */
    DeviceInfraredCode getDeviceInfraredCode(@Param("brand") String brand, @Param("brandType") String brandType, @Param("operationType") String operationType, @Param("equipmentType") String equipmentType, @Param("schoolCode") Long schoolCode, @Param("codeType") Long codeType);

}
