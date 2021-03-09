package com.allimu.mastercontroller.service.impl;

import com.allimu.mastercontroller.dao.*;
import com.allimu.mastercontroller.netty.code.Constant;
import com.allimu.mastercontroller.netty.code.EnvironmentUnit;
import com.allimu.mastercontroller.netty.config.SnMapContextSchoolCode;
import com.allimu.mastercontroller.netty.model.*;
import com.allimu.mastercontroller.remote.service.InstructionCodeRemoteService;
import com.allimu.mastercontroller.service.DeviceService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private CodeReflectDao codeReflectDao;
    @Autowired
    private DeviceBindInfoDao deviceBindInfoDao;
    @Autowired
    private DeviceBindDetailInfoDao deviceBindDetailInfoDao;
    @Autowired
    private DeviceInfraredInfoDao deviceInfraredInfoDao;
    @Autowired
    private EnvironmentalDataDao environmentalDataDao;
    @Autowired
    private ElectricityConsumptionDao electricityConsumptionDao;
    @Autowired
    private DeviceStateDao deviceStateDao;
    @Autowired
    private EquipDao equipDao;


    @Autowired
    InstructionCodeRemoteService instructionCodeRemoteService;


    //private Long schoolCode = CommonUtil.schoolCode;

    /**
     * 保存红外的遥控器
     *
     * @param message
     */
    @Override
    public void saveDeviceInfraredInfo(Message message) {

        DeviceInfraredInfo deviceInfraredInfo = (DeviceInfraredInfo) message.getObject();

        DeviceInfraredInfo dii = deviceInfraredInfoDao.getDeviceInfraredInfoByAddress(deviceInfraredInfo.getAddress(), deviceInfraredInfo.getEndpoint(), deviceInfraredInfo.getCols(), deviceInfraredInfo.getRows());
        if (dii == null) {

            deviceInfraredInfo.setCreateTime(new Date());
            deviceInfraredInfo.setSn(message.getSn());

            deviceInfraredInfoDao.saveDerviceInfraredInfo(deviceInfraredInfo);
        } else {
            System.out.println("遥控器指令已获取！");
        }


    }

    /**
     * 保存设备连接信息与设备绑定信息
     */
    @Transactional
    @Override
    public void saveDeviceBindInfo(Message message, ChannelHandlerContext ctx) {
        Equip snEquip = equipDao.getSnEquipBySn(message.getSn());
        DeviceBindDetailInfo deviceBindDetailInfo = (DeviceBindDetailInfo) message.getObject();
        System.out.println("项目启动获取网关的链接设备。");
        deviceBindDetailInfo.setCreateTime(new Date());

        if (snEquip != null) {
            deviceBindDetailInfo.setSchoolCode(snEquip.getSchoolCode());
            deviceBindDetailInfo.setSchoolName(snEquip.getSchoolName());
            deviceBindDetailInfo.setBuildCode(snEquip.getBuildCode());
            deviceBindDetailInfo.setBuildName(snEquip.getBuildName());

            //deviceBindDetailInfo.setClassRoomCode(snEquip.getClassCode());
            //deviceBindDetailInfo.setClassRoomName(snEquip.getClassName());
        }
        System.out.println(">> 已连接网关的设备信息 >>" + deviceBindDetailInfo);


        if (deviceBindDetailInfo.getDevice() != (short) 0x0006) {
            DeviceBindDetailInfo dbdi = deviceBindDetailInfoDao.getDeviceBindDetailInfoByIeee(deviceBindDetailInfo.getIeee(),
                    deviceBindDetailInfo.getEndpoint());
            if (dbdi == null) {
                deviceBindDetailInfoDao.saveDeviceBindDetailInfo(deviceBindDetailInfo);

                buildDeviceBindInfo(deviceBindDetailInfo);
            } else {
                // 更新设备连接信息
                deviceBindDetailInfoDao.updateDeviceBindDetailInfo(deviceBindDetailInfo);
            }

        } else {
            // 根据短地址端点地址查询红外控制
            List<DeviceBindDetailInfo> deviceBindDetailInfoListByIeee = deviceBindDetailInfoDao.getDeviceBindDetailInfoListByIeee(deviceBindDetailInfo.getSn(), deviceBindDetailInfo.getIeee(),
                    deviceBindDetailInfo.getEndpoint());

            //根据短地址端点地址查询红外控制器的遥控器
            List<DeviceInfraredInfo> deviceInfraredInfoList = deviceInfraredInfoDao.getDeviceInfraredInfo(deviceBindDetailInfo.getAddress(), deviceBindDetailInfo.getEndpoint());

            // 查看红外是否绑定设备
            if (deviceInfraredInfoList != null) {
                //如果红外设备一条都没
                if (deviceBindDetailInfoListByIeee == null) {
                    for (int i = 0; i < deviceInfraredInfoList.size(); i++) {

                        deviceBindDetailInfo.setCols(deviceInfraredInfoList.get(i).getCols());
                        deviceBindDetailInfo.setRows(deviceInfraredInfoList.get(i).getRows());
                        deviceBindDetailInfo.setDeviceInfraredInfoId(deviceInfraredInfoList.get(i).getId());
                        deviceBindDetailInfoDao.saveDeviceBindDetailInfo(deviceBindDetailInfo);
                        buildDeviceBindInfo(deviceBindDetailInfo);
                    }
                } else {
                    for (int i = 0; i < deviceInfraredInfoList.size(); i++) {
                        DeviceBindDetailInfo deviceBindDetailInfoByClos = deviceBindDetailInfoDao.getDeviceBindDetailInfoByClos(deviceInfraredInfoList.get(i).getAddress(), deviceInfraredInfoList.get(i).getEndpoint(), deviceInfraredInfoList.get(i).getCols(), deviceInfraredInfoList.get(i).getRows());
                        DeviceBindDetailInfo deviceBindDetailInfoByDeviceInfraredInfoId = deviceBindDetailInfoDao.getDeviceBindDetailInfoByDeviceInfraredInfoId(deviceInfraredInfoList.get(i).getAddress(), deviceInfraredInfoList.get(i).getEndpoint(), deviceInfraredInfoList.get(i).getId());
                        //如果根据Clos+rows 和 根据红外遥控器id 都等于null 就说明需要新增
                        if (deviceBindDetailInfoByClos == null && deviceBindDetailInfoByDeviceInfraredInfoId == null) {
                            deviceBindDetailInfo.setCols(deviceInfraredInfoList.get(i).getCols());
                            deviceBindDetailInfo.setRows(deviceInfraredInfoList.get(i).getRows());
                            deviceBindDetailInfo.setDeviceInfraredInfoId(deviceInfraredInfoList.get(i).getId());
                            deviceBindDetailInfoDao.saveDeviceBindDetailInfo(deviceBindDetailInfo);
                            buildDeviceBindInfo(deviceBindDetailInfo);
                        } else {
                            if (deviceBindDetailInfoByClos != null) {
                                deviceBindDetailInfoByClos.setCols(deviceInfraredInfoList.get(i).getCols());
                                deviceBindDetailInfoByClos.setRows(deviceInfraredInfoList.get(i).getRows());
                                deviceBindDetailInfoByClos.setDeviceInfraredInfoId(deviceInfraredInfoList.get(i).getId());
                                deviceBindDetailInfoDao.updateDeviceBindDetailInfo(deviceBindDetailInfoByClos);
                            } else {
                                deviceBindDetailInfoByDeviceInfraredInfoId.setCols(deviceInfraredInfoList.get(i).getCols());
                                deviceBindDetailInfoByDeviceInfraredInfoId.setRows(deviceInfraredInfoList.get(i).getRows());
                                deviceBindDetailInfoByDeviceInfraredInfoId.setDeviceInfraredInfoId(deviceInfraredInfoList.get(i).getId());
                                deviceBindDetailInfoDao.updateDeviceBindDetailInfo(deviceBindDetailInfoByDeviceInfraredInfoId);
                            }

                        }
                    }
                }
            } else {
                System.out.println("红外暂无绑定遥控器！");
            }

        }
    }

    private void buildDeviceBindInfo(DeviceBindDetailInfo dbdi) {
        DeviceBindInfo deviceBindInfo = new DeviceBindInfo();
        deviceBindInfo.setTempId(dbdi.getId());
        deviceBindInfo.setSchoolCode(dbdi.getSchoolCode());
        deviceBindInfo.setSchoolName(dbdi.getSchoolName());
        deviceBindInfo.setBuildCode(dbdi.getBuildCode());
        deviceBindInfo.setBuildName(dbdi.getBuildName());
        deviceBindInfo.setClassRoomCode(dbdi.getClassRoomCode());
        deviceBindInfo.setClassRoomName(dbdi.getClassRoomName());
        deviceBindInfo.setCreateTime(new Date());
        deviceBindInfo.setMark(Constant.MARK);
        if (codeReflectDao.getEquipmentType(dbdi.getDevice()) != null) {
            deviceBindInfo.setEquipmentType(codeReflectDao.getEquipmentType(dbdi.getDevice()));
            deviceBindInfoDao.saveDeviceBindInfo(deviceBindInfo);
            System.out.println(">> 保存临时绑定表成功------->   " + deviceBindInfo);
        }
    }

    /**
     * 保存环境数据
     */
    @Transactional
    @Override
    public void saveEnvironmentalData(Message message) {
        if (message != null) {
            String enviromentType = codeReflectDao.getEnviromentType(message.getClusterId());
            if (!Constant.ZHINENGCHAZUOGONGLV.equals(enviromentType)
                    && !Constant.CHUANGLIANWEIZHI.equals(enviromentType)) {
                Equip wgEquip = equipDao.getSnEquipByParams(message.getSn(), SnMapContextSchoolCode.getMapping(message.getSn()));
                if (wgEquip != null) {
                    EnvironmentalData ed = new EnvironmentalData();
                    ed.setSchoolCode(wgEquip.getSchoolCode());
                    ed.setSchoolName(wgEquip.getSchoolName());
                    ed.setBuildCode(wgEquip.getBuildCode());
                    ed.setBuildName(wgEquip.getBuildName());
                    ed.setClassRoomCode(wgEquip.getClassCode());
                    ed.setClassRoomName(wgEquip.getClassName());
                    ed.setType(enviromentType);
                    ed.setUnit(EnvironmentUnit.getUnit(ed.getType()));
                    if (Constant.SHIDU.equals(ed.getType())) {
                        DecimalFormat df = new DecimalFormat("0.0");
                        String value = df.format((float) message.getValue() / 100); // 保留一位小数
                        ed.setValue(Float.valueOf(value));
                    } else {
                        ed.setValue(message.getValue());
                    }
                    ed.setSchoolCode(SnMapContextSchoolCode.getMapping(message.getSn()));
                    ed.setIsUpload(false);
                    ed.setCreateTime(new Date());
                    environmentalDataDao.saveEnvironmentalData(ed);
                }
            } else {
                System.out.println(">> 数据库对应网关信息不存在,环境数据抛弃...");
            }
        }
    }

    /**
     * 保存用电量
     */
    @Transactional
    @Override
    public void saveElectricityConsumption(Message message) {
        DeviceBindDetailInfo deviceBindDetailInfo = deviceBindDetailInfoDao
                .getDeviceBindDetailInfoByFactorys(message.getSn(), message.getAddress(), message.getEndpoint());
        if (deviceBindDetailInfo != null) {
            ElectricityConsumption electricityConsumption = new ElectricityConsumption();
            electricityConsumption.setSchoolCode(deviceBindDetailInfo.getSchoolCode());
            electricityConsumption.setSchoolName(deviceBindDetailInfo.getSchoolName());
            electricityConsumption.setBuildCode(deviceBindDetailInfo.getBuildCode());
            electricityConsumption.setBuildName(deviceBindDetailInfo.getBuildName());
            electricityConsumption.setClassRoomCode(deviceBindDetailInfo.getClassRoomCode());
            electricityConsumption.setClassRoomName(deviceBindDetailInfo.getClassRoomName());
            electricityConsumption.setEquipmentCode(deviceBindDetailInfo.getEquipmentCode());
            electricityConsumption.setEquipmentName(deviceBindDetailInfo.getEquipmentName());
            electricityConsumption.setIsUpload(false);
            electricityConsumption.setState(message.getValue());
            electricityConsumption.setCreateTime(new Date());
            electricityConsumptionDao.saveElectricityConsumption(electricityConsumption);
            System.out.println(">> 接收到用电量信息----->  " + electricityConsumption);
        } else {
            System.out.println(">> 该设备未定义,电量数据抛弃...");
        }
    }

    /**
     * 保存设备状态
     */
    @Transactional
    @Override
    public void saveDeviceTrueState(Message message) {
        List<DeviceBindDetailInfo> deviceBindDetailInfoByList = deviceBindDetailInfoDao
                .getDeviceBindDetailInfoByList(message.getSn(), message.getAddress(), message.getEndpoint());
        System.out.println("saveDeviceTrueState:" + message);
        if (deviceBindDetailInfoByList != null) {
            for (int i = 0; i < deviceBindDetailInfoByList.size(); i++) {
                if (deviceBindDetailInfoByList.get(i).getEquipmentCode() != null) {
                    deviceBindDetailInfoByList.get(i).setState(message.getState());
                    buildDeviceState(deviceBindDetailInfoByList.get(i));
                } else {
                    System.out.println(">> 该设备未定义,设备状态数据抛弃...");
                }
            }

        } else {
            System.out.println(">> 该设备未定义,设备状态数据抛弃...");
        }
    }

    /**
     * 异常处理，如果网关断线，设置全部网关底下的设备为离线状态
     */
    public void exceptionHandling(String sn) {
        List<DeviceBindDetailInfo> deviceBindDetailInfoList = deviceBindDetailInfoDao.getDeviceBindDetailInfoBySn(sn);
        if (deviceBindDetailInfoList != null && deviceBindDetailInfoList.size() > 0) {
            List<DeviceState> deviceStateList = new ArrayList<DeviceState>();
            for (DeviceBindDetailInfo deviceBindDetailInfo : deviceBindDetailInfoList) {
                if (deviceBindDetailInfo.getEquipmentCode() != null) {
                    DeviceState deviceState = new DeviceState();
                    deviceState.setSchoolCode(deviceBindDetailInfo.getSchoolCode());
                    deviceState.setSchoolName(deviceBindDetailInfo.getSchoolName());
                    deviceState.setBuildCode(deviceBindDetailInfo.getBuildCode());
                    deviceState.setBuildName(deviceBindDetailInfo.getBuildName());
                    deviceState.setClassRoomCode(deviceBindDetailInfo.getClassRoomCode());
                    deviceState.setClassRoomName(deviceBindDetailInfo.getClassRoomName());
                    deviceState.setEquipmentCode(deviceBindDetailInfo.getEquipmentCode());
                    deviceState.setEquipmentName(deviceBindDetailInfo.getEquipmentName());
                    deviceState.setCreateTime(new Date());
                    deviceState.setState((byte) 2);
                    deviceStateList.add(deviceState);
                }
            }
            if (deviceStateList != null && deviceStateList.size() > 0) {
                System.out.println(">> 物联网关断线,将设备状态设置为离线...sn码=" + sn);
                for (DeviceState deviceState : deviceStateList) {
                    deviceStateDao.saveDeviceState(deviceState);
                }
            }

        } else {
            System.out.println(">> 物联网关断线,当前网关暂无已连接的设备信息...sn码=" + sn);
        }
    }


    /**
     * 设备状态改变，新增一条数据
     *
     * @param dbdi
     */
    private void buildDeviceState(DeviceBindDetailInfo dbdi) {
        int isUpload = 0;
        DeviceState ds = deviceStateDao.getNewestDeviceState(dbdi.getSchoolCode(), dbdi.getEquipmentCode(), dbdi.getState());
        if (ds == null || (new Date().getTime() - ds.getCreateTime().getTime() > 1000)) {
            DeviceState deviceState = new DeviceState();
            deviceState.setSchoolCode(dbdi.getSchoolCode());
            deviceState.setSchoolName(dbdi.getSchoolName());
            deviceState.setBuildCode(dbdi.getBuildCode());
            deviceState.setBuildName(dbdi.getBuildName());
            deviceState.setClassRoomCode(dbdi.getClassRoomCode());
            deviceState.setClassRoomName(dbdi.getClassRoomName());
            deviceState.setEquipmentCode(dbdi.getEquipmentCode());
            deviceState.setEquipmentName(dbdi.getEquipmentName());
            deviceState.setState(dbdi.getState());
            deviceState.setCreateTime(new Date());

            deviceStateDao.saveDeviceState(deviceState);

            List<DeviceState> deviceStateList = deviceStateDao.getDeviceState(SnMapContextSchoolCode.getMapping(dbdi.getSn()));

            if (deviceStateList != null && deviceStateList.size() > 0) {
                isUpload = instructionCodeRemoteService.saveDeviceState(deviceStateList, SnMapContextSchoolCode.getMapping(dbdi.getSn()));
                if (isUpload != 0) {
                    deviceStateDao.updateDeviceStateList(deviceStateList);
                }
            }

            System.out.println(">> 设备状态改变,保存设备状态信息" + deviceState);
        }
    }


}
