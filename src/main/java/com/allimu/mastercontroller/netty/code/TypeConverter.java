package com.allimu.mastercontroller.netty.code;


import java.nio.ByteBuffer;

import java.nio.ByteOrder;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;

/**
 * 
 * @author OuYang
 *	类型转换器
 */

public class TypeConverter {

	/**
	 * ByteBuf转整形
	 * 
	 * @author OuYang
	 * @date 2019-10-25
	 * @version 1.0
	 **/
	public static int byteBufToInt(ByteBuf in) {
		byte [] bytes=new byte [in.readableBytes()];
		in.readBytes(bytes);
		int mask = 0xff;
		int temp = 0;
		int res = 0;
		int byteslen = bytes.length;
		if (byteslen > 4) {
			return Integer.valueOf(0);
		}
		for (int i = byteslen - 1; i >= 0; i--) {
			res <<= 8;
			temp = bytes[i] & mask;
			res |= temp;
		}
		return res;
	}

	
	/**
	 * 字节数组转整型
	 * 
	 * @author OuYang
	 * @date 2019-10-25
	 * @version 1.0
	 **/
	public static Long bytesToLong(byte[] bytes) {
		int mask = 0xff;
		int temp = 0;
		long res = 0;
		int byteslen = bytes.length;
		if (byteslen > 8) {
			return Long.valueOf(0L);
		}
		for (int i = byteslen - 1; i >= 0; i--) {
			res <<= 8;
			temp = bytes[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * 字节数组转整型
	 * 
	 * @author OuYang
	 * @date 2019-10-25
	 * @version 1.0
	 **/
	public static int bytesToInt(byte[] bytes) {
		int mask = 0xff;
		int temp = 0;
		int res = 0;
		int byteslen = bytes.length;
		if (byteslen > 4) {
			return Integer.valueOf(0);
		}
		for (int i = byteslen - 1; i >= 0; i--) {
			res <<= 8;
			temp = bytes[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * 字节数组转整型
	 * 
	 * @author OuYang
	 * @date 2019-10-25
	 * @version 1.0
	 **/
	public static short bytesToShort(byte[] bytes) {
		int mask = 0xff;
		int temp = 0;
		short res = 0;
		int byteslen = bytes.length;
		if (byteslen > 2) {
			return Short.valueOf((short) 0);
		}
		for (int i = byteslen - 1; i >= 0; i--) {
			res <<= 8;
			temp = bytes[i] & mask;
			res |= temp;
		}
		return res;
	}

	/**
	 * 字节数组转十六进制字符串
	 * 
	 * @author OuYang
	 * @date 2019-10-25
	 * @version 1.0
	 **/
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	
	/**
	 * byteBuf转十六进制字符串
	 * 
	 * @author OuYang
	 * @date 2019-10-25
	 * @version 1.0
	 **/
	public static String byteBufToHexString(ByteBuf in) {
		byte[] b=new byte[in.readableBytes()];
		in.readBytes(b);
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		ReferenceCountUtil.release(in);
		return resultSb.toString();
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 十六进制字符串转字节数组
	 * 
	 * @author OuYang
	 * @date 2019-10-25
	 * @version 1.0
	 **/
	public static byte[] hexStrToBytes(String hexStr) {
		if (hexStr == null || hexStr.length() == 0) {
			return null;
		}
		if (hexStr.length() % 2 == 1) {
			hexStr = "0" + hexStr;
		}
		int len = hexStr.length() / 2;
		byte[] result = new byte[len];
		char[] chars = hexStr.toCharArray();
		for (int i = 0; i < len; i++) {
			result[i] = (byte) (charToByte(chars[i*2]) << 4 | charToByte(chars[i*2 + 1]));
		}
		return result;
	}

	public static byte charToByte(char c) {
		String chars = "0123456789abcdef";
		byte b = (byte) chars.indexOf(c);
		return b;
	}

	/**
	 * 整形转16进制字符串
	 * 
	 * @author OuYang
	 * @date 2019-10-25
	 * @version 1.0
	 **/
	public static String intToHex(int n) {
		// StringBuffer s = new StringBuffer();
		StringBuilder sb = new StringBuilder(8);
		String a;
		char[] b = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		while (n != 0) {
			sb = sb.append(b[n % 16]);
			n = n / 16;
		}
		a = sb.reverse().toString();
		return a;
	}

   public static byte[] shortToBytes(short value) {
       return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(value).array();
   }
   
   public static byte[] dateStrTobytes(String date) {
	   byte [] b=new byte[4];
	   b[0] = Byte.valueOf(date.substring(0, 2));
	   b[1] = Byte.valueOf(date.substring(2, 2));
	   byte [] shortb=new byte[2];
	   shortb=shortToBytes(Short.parseShort(date.substring(4, 4)));
	   b[2]=shortb[0];
	   b[3]=shortb[1];
	   return b;
   }
}
