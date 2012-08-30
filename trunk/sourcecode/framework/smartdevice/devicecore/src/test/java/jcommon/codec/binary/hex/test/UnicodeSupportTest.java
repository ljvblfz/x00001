package jcommon.codec.binary.hex.test;

import jcommon.codec.binary.hex.HexSupport;
import jcommon.codec.binary.hex.UnicodeSupport;

import org.testng.annotations.Test;

/**
 * 
 * @author luzhen
 *
 */
public class UnicodeSupportTest {

	@Test
	public void testOne() {
		System.out.println("-----"+UnicodeSupport.stringToUnicode("你好!!"));
		

		System.out.println("--4----" + HexSupport.toHexString(UnicodeSupport.stringToUnicode("你好!!")));//  4142434445467a7a7a7a7a782c2e2121
		System.out.println("--4----" + UnicodeSupport.unicodeToString(HexSupport.toAsciiString("5c75346636305c75353937645c75303032315c7530303231")));// ABCDEFzzzzzx,.!!

	}
}
