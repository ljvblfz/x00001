package jcommon.codec.binary.hex;

import java.io.UnsupportedEncodingException;

public class HexSupport {

	static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1', (byte) '2',
			(byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
			(byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c',
			(byte) 'd', (byte) 'e', (byte) 'f' };

	/**
	 * 
	 * @param ascii字符转为16进制数据
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String toHexString(String asciiString)
			 {
		byte[] bytes = asciiString.getBytes();
		String hexString = toHexString(bytes);
		return hexString;
	}

	//
	// /**
	// *
	// * @param ascii字符转为16进制数据
	// * @return
	// * @throws UnsupportedEncodingException
	// */
	// public static String toAsciiString(String hexString) throws
	// UnsupportedEncodingException {
	// byte[] bytes=asciiString.getBytes();
	// String hexString=toHexString(bytes);
	// return hexString;
	// }

	/**
	 * 十六进制转换字符串
	 * 
	 * @param String str Byte字符串(Byte之间无分隔符 如:[616C6B])
	 * @return String 对应的字符串
	 */
	public static String toAsciiString(String hexString) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexString.toCharArray();
		byte[] bytes = new byte[hexString.length() / 2];
		int n;

		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}//end of toAsciiString

	/**
	 * 字节数组转成16进制表示格式的字符串
	 * 
	 * @param byteArray
	 *            需要转换的字节数组
	 * @return 16进制表示格式的字符串
	 **/
	public static String toHexString(byte[] bytes)  {
		byte[] hex = new byte[2 * bytes.length];
		int index = 0;

		for (byte b : bytes) {
			int v = b & 0xFF;
			hex[index++] = HEX_CHAR_TABLE[v >>> 4];
			hex[index++] = HEX_CHAR_TABLE[v & 0xF];
		}
		try {
			return new String(hex, "ASCII");
		} catch (UnsupportedEncodingException e) { 
			throw new RuntimeException(e);
		} 
	}

	/**
	 * 16进制的字符串表示转成字节数组
	 * 
	 * @param hexString
	 *            16进制格式的字符串
	 * @return 转换后的字节数组
	 **/
	public static byte[] toByteArray(String hexString) {
		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {
			// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}

	/**
	 * 16进制的字符串表示转成10进制
	 * 
	 * @param hexString
	 * @return
	 */
	public static long toLongDecimal(String hexString) {
		long decimal = Long.parseLong(hexString, 16);
		return decimal;
	}

	/**
	 * 16进制的字符串表示转成10进制
	 * 
	 * @param hexString
	 * @return
	 */
	public static int toIntDecimal(String hexString) {
		int decimal = Integer.parseInt(hexString, 16);
		return decimal;
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		byte[] byteArray = { (byte) 255, (byte) 254, (byte) 253, (byte) 252,
				(byte) 251, (byte) 250 };

		byte[] baRead = new byte[] { (byte) 0xFF, (byte) 0xB0, (byte) 0x00,
				(byte) 0x34, (byte) 0x10 };
		System.out.println("--1---" + HexSupport.toHexString(byteArray));
		System.out.println("--2---" + HexSupport.toHexString(baRead));

		System.out.println("--3----" + HexSupport.toIntDecimal("3825f591"));// 942011793
		System.out.println("--3----" + HexSupport.toLongDecimal("3825f591"));// 942011793

		System.out.println("--4----" + HexSupport.toHexString("ABCDEFzzzzzx,.!!"));//  4142434445467a7a7a7a7a782c2e2121
		System.out.println("--4----" + HexSupport.toAsciiString("4142434445467a7a7a7a7a782c2e2121"));// ABCDEFzzzzzx,.!!
	}
}