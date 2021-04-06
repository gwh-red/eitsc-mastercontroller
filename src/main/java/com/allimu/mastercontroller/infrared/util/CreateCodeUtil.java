package com.allimu.mastercontroller.infrared.util;

import com.allimu.mastercontroller.infrared.data.AirData;
import com.allimu.mastercontroller.infrared.data.KeyData;
import com.allimu.mastercontroller.infrared.value.Value;

public class CreateCodeUtil {

    /**
     * 生成空调红外控制码
     *
     * @param mAirRows 空调厂家品牌 (根据码库对应,从下标0开始)
     * @param mAirCols 空调型号 (根据码库对应,从下标0开始)
     * @param mPower   开关状态 (16进制,开=0x01 关=0x00)
     * @param mMode    模式
     * @param mTmp     温度
     * @return
     */
    public static String createCode(Integer mAirRows, Integer mAirCols, Byte mPower, Integer mMode, Integer mTmp) {
        KeyData kd = new KeyData();
        // 设置该键是空调还是其它电器类型，此处设置为空调，也可以从通信协议1.41返回值取remotetype
        int _type = Value.DeviceType.TYPE_AIR;
        // 设置我们的空调厂家，该数据本来应该从主机读取的，此处先写死一个固定值
        // 例如码库里面，美的排在数组第一位，从0开始计算，也可以从通信协议1.41返回值取brand或者rows，这两个值在我们app保存是一样的
        if (mAirRows == null) {
            KeyData.mAirRows = 0;
        } else {
            kd.mAirRows = mAirRows;
        }
        // 设置美的空调型号，例如取第一组数据，也可以从通信协议1.41返回值取local或者cols，这两个值在我们app保存是一样的

        if (mAirCols == null) {
            kd.mAirCols = 0;
        } else {
            kd.mAirCols = mAirCols;
        }
        /*
         * 最重要一步，获取指令,空调的keyvalue, 定义好以上值，接着界面也需要确定按的是那个键，本程序没有界面，假设用户按了关机键
         * 而在按关机键之前，假设软件界面是显示制冷、26度、风量中、风向下
         * 参照AirData对像的值，如果你把上述的参数设置为默认值，那么，如果你需要将空调打开，只需要设置
         * keyvalue=Value.AIR.POWER，参照Value类，可以知道各遥控器的键值
         * 如果你想把温度设置为27度，只需要将keyvalue设置为温度+ keyvalue=Value.AIR.TEMPADD;
         */
        // 设置默认为关闭，如要设置默认为开，则值为0x01 关闭为0x00
        if (mPower == null) {
            AirData.mPower = 0x00;
        } else {
            if (mPower == (byte) 1) {
                mPower = (byte) 0;
            } else {
                mPower = (byte) 1;
            }
            AirData.mPower = mPower;
        }
        // 模式，都有默认值对应的，可查询码库
        if (mMode == null) {
            AirData.mMode = (byte) 2;
        } else {
            AirData.mMode = (byte) mMode.intValue();
        }
        // 温度值
        if (mTmp == null) {
            AirData.mTmp = (byte) 26;
        } else {
            AirData.mTmp = (byte) mTmp.intValue();
        }

        byte[] b = kd.Write(Value.AIR.POWER);    // 此处输出指令，可以跟踪调试
        kd.Write(Value.AIR.TEMPADD);            // 如果想温度+1，刚执行该指令就可以了，如当前温度为26度，执行后则为27度

        String infraredCode = bytesToHexString(b);
        //System.out.println("infraredCode:" + infraredCode);
        return infraredCode;
    }

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


}
