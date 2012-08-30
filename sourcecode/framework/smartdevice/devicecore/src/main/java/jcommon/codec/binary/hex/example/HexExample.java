package jcommon.codec.binary.hex.example;

import java.io.UnsupportedEncodingException;

public class HexExample {

	public static void main(String[] args) throws UnsupportedEncodingException {
//		System.out.println(toHexString("ff".getBytes()));
//		System.out.println(fromHexString("ff"));
//		System.out.println(getValueFromHexStr("0xfff7931e"));
		System.out.println(getIntValueFromHexStr("0x0101010101"));
//		System.out.println(hexadecimal("ff",null));
		byte[] decValue=new byte[5];
		decValue[0]=1;
		decValue[1]=1;
		decValue[2]=1;
		decValue[3]=1;
		decValue[4]=1;
		System.out.println(asHex(decValue));
	}

	public static String toHexString(byte[] ba) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < ba.length; i++)
			str.append(String.format("%x", ba[i]));
		return str.toString();
	}

	public static String fromHexString(String hex) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < hex.length(); i += 2) {
			str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
		}
		return str.toString();
	}
	
	
	public static int getIntValueFromHexStr(String hexString){
		int hexInt = Long.decode(hexString).intValue();
		return hexInt;
	}
	
//	public static int getHexValueFromIntStr(String hexString){
//		Integer.parseInt(s, radix)
//		return hexInt;
//	}
	
	public static String hexadecimal(String input, String charsetName) throws UnsupportedEncodingException {
		if(null==charsetName){
			charsetName="UTF-8";
		}
	    if (input == null) throw new NullPointerException();
	    return asHex(input.getBytes(charsetName));
	}

	private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

	public static String asHex(byte[] buf)
	{
	    char[] chars = new char[2 * buf.length];
	    for (int i = 0; i < buf.length; ++i)
	    {
	        chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
	        chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
	    }
	    return new String(chars);
	}
}
