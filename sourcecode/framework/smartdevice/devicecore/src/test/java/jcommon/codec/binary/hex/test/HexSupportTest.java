package jcommon.codec.binary.hex.test;

import jcommon.codec.binary.hex.HexSupport;

import org.testng.annotations.Test;

public class HexSupportTest {

	@Test
	public void testOne(){
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
	
	
}//end of HexSupportTest
