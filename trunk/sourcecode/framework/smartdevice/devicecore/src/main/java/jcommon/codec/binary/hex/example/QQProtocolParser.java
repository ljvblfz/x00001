package jcommon.codec.binary.hex.example;

import java.io.UnsupportedEncodingException;

import jcommon.codec.binary.hex.HexSupport;

/**
 * http://sanbook.iteye.com/blog/301455
 * @author luzhen
 *
 */
public class QQProtocolParser {

	
	static String command="02 11 5b 00 91 2a a8 38 25 f5 91 5d 4c af 65 54 44 6d 76 7b ff f0 14 73 e6 0a 95 9d 74 ce b3 b5 fd 61 1d 12 84 a5 04 53 3c b1 d4 f9 27 9c 7c 3f ef f0 bb 3c dc 3f 0f 25 ee 3d 7c 03 ";
//02115b00912aa83825f5915d4caf6554446d767bfff01473e60a959d74ceb3b5fd611d1284a504533cb1d4f9279c7c3feff0bb3cdc3f0f25ee3d7c03

	static String command2="02115b00912aa83825f5915d4caf6554446d767bfff01473e60a959d74ceb3b5fd611d1284a504533cb1d4f9279c7c3feff0bb3cdc3f0f25ee3d7c03";
	
	public static void main(String[] args) throws UnsupportedEncodingException{
//		System.out.println("-----xxx----->"+Byte.parseByte("ff",16));
		String[] arrayOfCommand=command.split(" ");
		
		byte[] byteOfArray=new byte[arrayOfCommand.length];
		int i=0;
		for(String strCommand:arrayOfCommand){
//			System.out.println("---------->"+Integer.parseInt(strCommand,16));
			byte b=(byte)Integer.parseInt(strCommand,16);
			System.out.print("  "+b);
			byteOfArray[i]=b;
			i++;
			
		}
		
		System.out.println("\n==============>"+HexSupport.toHexString(byteOfArray));
		byte[] array=HexSupport.toByteArray(command2);
		for(byte bx:array){
			System.out.print("  "+bx);
		}
		
		
	}

}
