package com.allimu.mastercontroller.netty.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.allimu.mastercontroller.dao.*;
import com.allimu.mastercontroller.infrared.util.CreateCodeUtil;
import com.allimu.mastercontroller.netty.code.AirBrandMap;
import com.allimu.mastercontroller.netty.code.Constant;
import com.allimu.mastercontroller.netty.code.DateInter;
import com.allimu.mastercontroller.netty.code.TypeConverter;
import com.allimu.mastercontroller.netty.model.*;
import com.allimu.mastercontroller.remote.service.InstructionCodeRemoteService;
import com.allimu.mastercontroller.util.CommonUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator 定时任务
 */
@Service
@EnableScheduling
public class CodeTask {
    @Autowired
    private InstructionCodeRemoteService remoteService;
    @Autowired
    private EquipDao equipDao;
    @Autowired
    private DeviceBindInfoDao deviceBindInfoDao;
    @Autowired
    private DeviceStateDao deviceStateDao;
    @Autowired
    private ElectricityConsumptionDao electricityConsumptionDao;
    @Autowired
    private EnvironmentalDataDao environmentalDataDao;
    @Autowired
    private DeviceBindDetailInfoDao deviceBindDetailInfoDao;
    @Autowired
    private CodeReflectDao codeReflectDao;

    private ChannelHandlerContext ctx1;

    private ChannelHandlerContext ctx2;

    private ChannelHandlerContext ctx3;

    private Long schoolCode = CommonUtil.schoolCode;
    /*    int i = 0;*/

    /**
     * * 定时任务获取网关设备
     */
    @Scheduled(cron = "0/8 * * * * ?")
    public void getEquipBySchoolCode() {
        String str = remoteService.getWgEquipBySchoolCode(schoolCode);
        JSONObject jsonObject = JSONObject.parseObject(str);
        String res = jsonObject.getString("data");
        if (res != null && !res.equals("[]")) {
            List<Equip> snEquipList = JSON.parseObject(res, new TypeReference<ArrayList<Equip>>() {
            });
            for (Equip snEquip : snEquipList) {
                if (equipDao.getSnEquipBySn(snEquip.getSn()) == null) {
                    snEquip.setCreateTime(new Date());
                    equipDao.saveSnEquip(snEquip);
                }
            }
        }
    }

    /**
     * 定时任务上传状态数据，耗电量，设备状态，设备临时绑定信息到云端
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void svaeToCloud() {
       /* i++;
        System.out.println("---:" + i);*/
        int isUpload = 0;
        List<DeviceBindInfo> deviceBindInfoList = deviceBindInfoDao.getDeviceBindInfo(schoolCode);
        List<DeviceState> deviceStateList = deviceStateDao.getDeviceState(schoolCode);
        List<ElectricityConsumption> electricityConsumptionList = electricityConsumptionDao.getElectricityConsumption(schoolCode);
        List<EnvironmentalData> environmentalDataList = environmentalDataDao.getEnvironmentalData(schoolCode);
        if (deviceBindInfoList != null && deviceBindInfoList.size() > 0) {
            isUpload = remoteService.saveDeviceBindInfoByWg(deviceBindInfoList, schoolCode);
            if (isUpload != 0) {
                deviceBindInfoDao.updateDeviceBindInfoList(deviceBindInfoList);
            }
        }
        if (deviceStateList != null && deviceStateList.size() > 0) {
            isUpload = remoteService.saveDeviceState(deviceStateList, schoolCode);
            if (isUpload != 0) {
                deviceStateDao.updateDeviceStateList(deviceStateList);
            }
        }
        if (electricityConsumptionList != null && electricityConsumptionList.size() > 0) {
            isUpload = remoteService.saveElectricityConsumption(electricityConsumptionList, schoolCode);
            if (isUpload != 0) {
                electricityConsumptionDao.updateElectricityConsumptionList(electricityConsumptionList);
            }
        }
        if (environmentalDataList != null && environmentalDataList.size() > 0) {
            isUpload = remoteService.saveEnvironmentalData(environmentalDataList, schoolCode);
            if (isUpload != 0) {
                environmentalDataDao.updateEnvironmentalDataList(environmentalDataList);
            }
        }
    }

    /**
     * 定时任务获取设备编号与临时id的绑定
     */
    @Scheduled(cron = "*/2 * * * * ?")
    public void instructionCodeRemoteService() {
        try {
            List<TempReflect> tempReflectList = remoteService.getTempReflectByWg(schoolCode, Constant.MARK);
            if (tempReflectList != null && tempReflectList.size() > 0) {
                for (TempReflect tempReflect : tempReflectList) {
                    DeviceBindDetailInfo deviceBindDetailInfo = deviceBindDetailInfoDao
                            .getDeviceBindDetailInfoByTempId(tempReflect.getTempId());
                    if (deviceBindDetailInfo != null) {
                        deviceBindDetailInfo.setEquipmentCode(tempReflect.getEquipmentCode());
                        deviceBindDetailInfo.setEquipmentName(tempReflect.getEquipmentName());
                        deviceBindDetailInfoDao.updateDeviceBindDetailInfo(deviceBindDetailInfo);

                        remoteService.updateTempReflectByWg(tempReflect);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(">> 获取设备定义信息发生异常...");
        }
    }

    /**
     * 定时任务获取云端测试指令
     */
    @Scheduled(cron = "*/2 * * * * ?")
    public void getTestCode() {
        List<JkTestCode> jkTestCodeList = remoteService.getJkTestCode(schoolCode);
        if (jkTestCodeList != null && jkTestCodeList.size() > 0) {
            List<InstructionCode> instructionCodeList = new ArrayList<InstructionCode>();
            jkTestCodeToInstructionCode(jkTestCodeList, instructionCodeList);
            for (InstructionCode instructionCode : instructionCodeList) {
                ctx1 = SnMapChannelHandlerContext.getMapping(instructionCode.getSn());
                if (ctx1 != null) {
                    ctx1.writeAndFlush(instructionCode);
                }
            }
        }
    }

    /**
     * 定时任务获取正式指令
     */
    @Scheduled(cron = "*/2 * * * * ?")
    public void getJkCode() {
        List<JkCode> jkCodeList = remoteService.getJkCode(schoolCode);
        if (jkCodeList != null && jkCodeList.size() > 0) {
            List<InstructionCode> instructionCodeList = new ArrayList<InstructionCode>();
            jkCodeToInstructionCode(jkCodeList, instructionCodeList);
            for (InstructionCode instructionCode : instructionCodeList) {
                ctx3 = SnMapChannelHandlerContext.getMapping(instructionCode.getSn());
                if (ctx3 != null) {
                    System.out.println("发送正式指令成功 >>" + instructionCode);
                    ctx3.writeAndFlush(instructionCode);
                    //System.out.println("3."+instructionCode.getData());
                    //System.out.println("3."+instructionCode.getData_len());
                    //System.out.println("发送正式指令成功 >>" + instructionCode);
                } else {
                    System.out.println("该网关断线");
                }
            }
        }
    }

    /**
     * 定时任务更新时间
     */
    @Scheduled(cron = "*/600 * * * * ?")
    public void setDate() {
        DateInter.setDate(new Date());
    }

    /**
     * 定时任务获取当天用电量，每隔一个小时获取一次
     */
    @Scheduled(cron = "0 0 1/1 * * ?")
    public void electricityTask() {
        List<DeviceBindDetailInfo> eleDeviceList = deviceBindDetailInfoDao.getEleDevice(schoolCode);
        if (eleDeviceList != null && eleDeviceList.size() > 0)
            for (DeviceBindDetailInfo eleDevice : eleDeviceList) {
                ctx2 = SnMapChannelHandlerContext.getMapping(eleDevice.getSn());
                if (ctx2 != null) {
                    if (eleDevice.getEquipmentCode() != null) {
                        ctx2.writeAndFlush(buildEleCode(eleDevice));
                    }
                }
            }
    }

    /**
     * 定时心跳检测
     */
    @Scheduled(cron = "*/60 * * * * ?")
    public void heartBeat() {
        Map<String, ChannelHandlerContext> snMapChannelHandlerContext = SnMapChannelHandlerContext.getMap();
        InstructionCode heartBeatCode = new InstructionCode();
        heartBeatCode.setType((byte) 0x1e);
        System.out.println("当前连接数:" + snMapChannelHandlerContext.size());
        for (ChannelHandlerContext ctx : snMapChannelHandlerContext.values()) {
            if (!ctx.isRemoved()) {
                ctx.writeAndFlush(heartBeatCode);
                System.out.println("发送心跳检测 >>" + heartBeatCode);
            } else {
                ctx.close();
            }
        }
    }

    /**
     * 测试指令转为网关标准指令
     */
    private void jkTestCodeToInstructionCode(List<JkTestCode> jkTestCodeList,
                                             List<InstructionCode> instructionCodeList) {
        Date date = new Date();
        for (JkTestCode jtc : jkTestCodeList) {
            if (date.getTime() - jtc.getCreateTime().getTime() <= 20000) {
                InstructionCode instructionCode = new InstructionCode();
                DeviceBindDetailInfo ddi = deviceBindDetailInfoDao.getDeviceBindDetailInfoByTempId(jtc.getTempId());
                if (ddi != null) {
                    if (ddi.getDevice() == (short) 0x0006) {
                        instructionCode.setType((byte) 0x8c);
                        String hexStr = null;
                        if (jtc.getType().equals(Constant.OPEN)) {
                            hexStr = "3001001b1b010201010102308d71004d01b202f0030f040705f88d72004d01b202d60329040705f8064d07b208d609290a070bf88d73004d02d88dff97";
                        } else if (Constant.OFF.equals(jtc.getType())) {
                            hexStr = "3001001b1b010201000102308d71004d01b202f0030f040705f88d72004d01b202d60329040705f8064d07b208d609290a070bf88d73004d02d88dff96";
                        }
                        if (hexStr != null) {
                            byte[] data = TypeConverter.hexStrToBytes(hexStr);
                            instructionCode.setData(data);
                            instructionCode.setData_len((byte) data.length);
                        }
                    } else if (ddi.getDevice() == (short) 0x0000) {
                        instructionCode.setType((byte) 0xad);
                        if (jtc.getType().equals(Constant.OPEN)) {
                            instructionCode.setState((byte) 0x03);
                        } else {
                            instructionCode.setState((byte) 0x02);
                        }
                    } else { // 如果是开 设置为1，反之0
                        instructionCode.setType((byte) 0x82);
                        if (jtc.getType().equals(Constant.OPEN)) {
                            instructionCode.setState((byte) 0x01);
                        } else {
                            instructionCode.setState((byte) 0x00);
                        }
                    }
                    instructionCode.setSn(ddi.getSn());
                    instructionCode.setAddress(ddi.getAddress());
                    instructionCode.setEndpoint(ddi.getEndpoint());
                    instructionCodeList.add(instructionCode);
                }
            } else {
                System.out.println(">> 测试指令超时丢弃...");
            }
            jtc.setIsUpload(true);
            remoteService.updateJkTestCode(jtc);
        }
    }

    /**
     * 正式指令转为网关标准指令
     */
    private void jkCodeToInstructionCode(List<JkCode> jkCodeList, List<InstructionCode> instructionCodeList) {
        Date date = new Date();
        for (JkCode jc : jkCodeList) {
            if (date.getTime() - jc.getCreateTime().getTime() <= 20000) {
                Equip SnEquip = equipDao.getSnEquipByEquipmentCode(jc.getEquipmentCode());
                if (SnEquip != null) {
                    InstructionCode instructionCode = new InstructionCode();
                    instructionCode.setSn(SnEquip.getSn());
                    instructionCode.setType((byte) 0x9f);
                    instructionCodeList.add(instructionCode);
                } else {
                    DeviceBindDetailInfo ddi = deviceBindDetailInfoDao.getDeviceBindDetailInfoByEquipmentCode(jc.getEquipmentCode());
                    if (ddi != null) {
                        if (ddi.getDevice() == (short) 0x0000) {
                            jc.setType("传感器");
                            if (Constant.OPEN.equals(jc.getType())) {
                                jc.setValue((byte) 0x03);
                            } else if (Constant.OFF.equals(jc.getType())) {
                                jc.setValue((byte) 0x02);
                            }
                        }
                        byte codetype;
                        if (Constant.KT_TYPENAME.equals(jc.getEquipmentType()) ||
                                Constant.ZNCL_TYPENAME.equals(jc.getEquipmentType())) {
                            codetype = codeReflectDao.getCodeTypeByType(jc.getEquipmentType());
                        } else {
                            codetype = codeReflectDao.getCodeTypeByType(jc.getType());
                        }
                        InstructionCode instructionCode = new InstructionCode();
                        if (codetype == (byte) 0x8c) {
                            String hexStr = null;
                            if (Constant.OPEN.equals(jc.getType())) {
                                hexStr = CreateCodeUtil.createCode(AirBrandMap.getIndex(jc.getBrandName()), null,
                                        jc.getValue(), jc.getMode(), jc.getTemperature());
                                //	hexStr = "2800b0411d32e583fe8c1d028edd000000000000080000103001028719020301000102030000ffde";
                            } else {
                                hexStr = CreateCodeUtil.createCode(AirBrandMap.getIndex(jc.getBrandName()), null,
                                        jc.getValue(), jc.getMode(), jc.getTemperature());
                                //	hexStr = "2800b0411d32e583fe8c1d028edd000000000000080000103001028719020301010102030000ffdf";
                            }
                            if (hexStr != null) {
                                byte[] data = TypeConverter.hexStrToBytes(hexStr);
                                //System.out.println("1."+hexStr);
                                instructionCode.setData(data);
                                instructionCode.setData_len((byte) data.length);
                            }
                        }
                        instructionCode.setType(codetype);
                        instructionCode.setSn(ddi.getSn());
                        instructionCode.setAddress(ddi.getAddress());
                        instructionCode.setEndpoint(ddi.getEndpoint());
                        instructionCode.setState(jc.getValue());
                        instructionCode.setValue(jc.getValue());
                        instructionCodeList.add(instructionCode);
                        System.out.println("2." + instructionCode);
                    } else {
                        System.out.println(">> 该正式指令的设备未绑定...");
                    }
                }
            } else {
                System.out.println(">> 正式指令超时丢弃...");
            }
            jc.setIsUpload(true);
            remoteService.updateJkCode(jc);
        }
    }


    /**
     * 新建获取用电量指令
     */
    private InstructionCode buildEleCode(DeviceBindDetailInfo eleDevice) {
        InstructionCode instructionCode = new InstructionCode();
        instructionCode.setSn(eleDevice.getSn());
        instructionCode.setAddress(eleDevice.getAddress());
        instructionCode.setEndpoint(eleDevice.getEndpoint());
        instructionCode.setType((byte) 0xa5);
        instructionCode.setStartTime(new SimpleDateFormat("ddMMyyyy").format(new Date()));
        instructionCode.setEndTime(new SimpleDateFormat("ddMMyyyy").format(new Date()));
        return instructionCode;
    }


//	@Scheduled(cron = "*/10 * * * * ?")
//	public void sysoPrint() {
//		System.out.println("schoolCode==="+CommonUtil.schoolCode+"===schoolName=="+CommonUtil.schoolName+"===port==="+CommonUtil.tcpServerPort);
//	}

}
