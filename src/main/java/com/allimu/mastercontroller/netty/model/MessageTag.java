package com.allimu.mastercontroller.netty.model;



public enum MessageTag {
	DEVICE_BIND((byte)0X01),
	S_DEVICE_STATE((byte)0Xb2),
	N_DEVICE_STATE((byte)0x07),
	ElECTRIC_POWER((byte)0xb4),
	SENSOR_DATA((byte)0x70),
	ElECTRIC_CONSUMPTION((byte)0xb5),
	HEARTBEAT_RES((byte)0X1f),
	
    LOGIN_REQ((byte)3),
    LOGIN_RESP((byte)4),
    HEARTBEAT_REQ((byte)5),
    HEARTBEAT_RESP((byte)6),
    ;

    private byte value;

    MessageTag(byte v){
        this.value = v;
    }

    public byte value(){
        return value;
    }

}
