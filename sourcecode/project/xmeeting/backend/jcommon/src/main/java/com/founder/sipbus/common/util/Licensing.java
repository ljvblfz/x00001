package com.founder.sipbus.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random; 

/**
 * 系统授权 
 * 
 * @author xbg at 2007-4-23 17:47:52
 * 
 */
public final class Licensing {
	/* {验证码，如果验证码不正确，该类所有信息不可用，_ValidateKey='EGOSYSTEMISBESTBYWZFXBG'} */
	private String _ValidateKey;
  
	// 注册的公司名称
	private String _CompanyName;

	// 注册码中包含硬盘号的后五位
	private String _LicenseCode;

	// 注册的特征码
	private String _KeyCode;

	// 注册日期
	private String _LicenseDay;

	// 用户数
	private int _UserCount;

	// 限制天数
	private int _UseDays;

	// 应用数
	private int _AppCount;

	// 模块数
	private int _ModuleCount;

	// 可建表数
	private int _TableCount;

	// 流程数
	private int _FlowCount;

	// 注册码1
	private String _LicenseKey1;

	// 注册码2
	private String _LicenseKey2;

	// 注册码3
	private String _LicenseKey3;

	// 注册码4
	private String _LicenseKey4;

	// 扩展注册码解密，用以其它控制
	private String _ExLicenseCode;

	// 标识有加密码硬件 1,0
	private int _HasHardKey;

	private short Password1; // 加密锁的一级密码1

	private short Password2; // 加密锁的一级密码2

	// 验证
	private final String PrivateValidateKey = "EGOSYSTEMISBESTBYWZFXBG";

	public Licensing() {
		super();
		// 注册的公司名称
		_CompanyName = "";
		// 注册码中包含硬盘号的后五位
		_LicenseCode = "";
		// 注册的站点
		_KeyCode = "";
		// 用户数
		_UserCount = 0;
		// 限制天数
		_UseDays = 0;
		// 应用数
		_AppCount = 0;
		// 模块数
		_ModuleCount = 0;
		// 可建表数
		_TableCount = 0;
		// 流程数
		_FlowCount = 0;
	}

	// /////////////////
	// 私有
	// //////////////// /

	// 私有加解密
	private String Decode(String inpass1) {
		int offset, i, j, offset2;
		String c, outpass, temp1, temp2, sorce_string, dest_string, inpass;
		String ps;
		if (!_ValidateKey.equals(PrivateValidateKey)) {
			return "";
		}

		outpass = "";

		inpass = inpass1;
		sorce_string = "AOPQRdefghiBCDEFGHIJSTUVWXYZabcKLMNjuklmnopqrstvwxyz~!@#$%^&*()_+|{}:<>?`1234567/890-=\\[];',";
		dest_string = "AOPQRZabcKLMNju%^&*()_+|{}:<defghiBCDEFGHIJklmnopqrstvwxyz~!@#$STUV567/890-=\\WXY>?`1234[];',";
		if (inpass.equals("")) {
			return "";
		}

		offset = sorce_string.indexOf(inpass.substring(0, 1));

		offset2 = sorce_string.indexOf(inpass.substring(1, 2));
		temp1 = (sorce_string + sorce_string).substring(offset, offset + 93);
		temp2 = (dest_string + dest_string).substring(offset2, offset2 + 92);

		for (i = 2; i < inpass.length(); i++) {
			c = inpass.substring(i, i + 1);
			j = temp2.indexOf(c);
			if (j == -1) {
				outpass = outpass + c;
			} else {
				outpass = outpass + temp1.substring(j, j + 1);
			}
		}

		return outpass;

	}

	private String Encode(String inpass1) {
		int offset, i, j, offset2;
		String c, outpass, temp1, temp2, sorce_string, dest_string;
		Date tt;
		int Year, Month, Day, Hour, Min, Sec, MSec;
		String inpass;

		if (!_ValidateKey.equals(PrivateValidateKey)) {
			return "";
		}

		outpass = "";

		inpass = inpass1;
		sorce_string = "AOPQRdefghiBCDEFGHIJSTUVWXYZabcKLMNjuklmnopqrstvwxyz~!@#$%^&*()_+|{}:<>?`1234567/890-=\\[];',";
		dest_string = "AOPQRZabcKLMNju%^&*()_+|{}:<defghiBCDEFGHIJklmnopqrstvwxyz~!@#$STUV567/890-=\\WXY>?`1234[];',";

		if (inpass.equals("")) {
			return "";
		}

		tt = new Date();
		Hour = tt.getHours();
		Min = tt.getMinutes();
		Sec = tt.getSeconds();
		// MSec = tt.getTimezoneOffset()

		// DecodeTime(tt, Hour, Min, Sec, MSec);

		offset = Sec + 1;

		offset2 = (new Random()).nextInt(92);

		temp1 = (sorce_string + sorce_string).substring(offset, offset + 93);
		temp2 = (dest_string + dest_string).substring(offset2, offset2 + 92);

		for (i = 0; i < inpass.length(); i++) {
			c = inpass.substring(i, i + 1);
			j = temp1.indexOf(c);
			if (j == -1) {
				outpass = outpass + c;
			} else {
				outpass = outpass + temp2.substring(j, j + 1);
			}
		}

		outpass = sorce_string.substring(offset, offset + 1)
				+ sorce_string.substring(offset2, offset2 + 1) + outpass;
		return outpass;

	}

	private String strtoasc(String tmpStr) {
		int i;
		String str, ReAsc, returnStr;

		returnStr = "";

		if (!_ValidateKey.equals(PrivateValidateKey)) {
			return "";
		}

		str = tmpStr;
		if (str.equals("")) {
			return "";
		}

		byte[] Str = null;
		try {
			Str = str.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			Str = str.getBytes();
		}
		for (i = 0; i < Str.length; i++) {
			int chr = Str[i];
			ReAsc = Integer.toHexString(chr);
			ReAsc = ReAsc.substring(ReAsc.length() - 2);
			returnStr = returnStr + ReAsc;
		}
		return returnStr.toUpperCase();
	}

	private String asctostr(String tmpStr) {
		int i, j, ReAsc;
		String ReHex, ReChr, returnStr, str;

		returnStr = "";

		if (!_ValidateKey.equals(PrivateValidateKey)) {
			return "";
		}

		str = tmpStr;
		if (str.equals("")) {
			return "";
		}

		i = 0;
		j = 0;
		int partflg = 2;
		byte[] Str = new byte[(int) Math.ceil(str.length() / 2)];
		while (i < str.length()) {
			ReHex = str.substring(i, i + partflg);
			ReAsc = Integer.valueOf(ReHex, 16);
			Str[j] = (byte) ReAsc;
			i += partflg;
			j++;
		}

		try {
			returnStr = new String(Str, "GBK");
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
		}

		return returnStr;
	}

	// 解密
	public String Decode2(String inpass1) {
		return this.Decode(this.asctostr(inpass1));
	}

	// 加密
	public String Encode2(String inpass1) {
		return this.strtoasc(this.Encode(inpass1));
	}

	// 产生注册码
	private String GenLicenseKey(String LicenseKeyStr) {

		char tmpChar;
		int i, Flag, SplitInt;
		String ResultStr, tmpStr;
		int tmpInt;

		if (!_ValidateKey.equals(PrivateValidateKey)) {
			return "";
		}

		ResultStr = "";

		for (i = 0; i < LicenseKeyStr.length(); i++) {
			tmpChar = LicenseKeyStr.charAt(i);
			tmpInt = tmpChar;

			// 71-90(G-Z)
			// 48-57(0-9)是数字
			// 种子数

			if ((tmpInt > 47) && (tmpInt < 58)) {
				Flag = 1;
				Flag = (new Random()).nextInt(2);
				Flag = 23 + 10 * Flag;
				tmpInt = tmpInt + Flag;
				tmpChar = (char) tmpInt;
				tmpStr = String.valueOf(tmpChar);
			} else
				tmpStr = String.valueOf(tmpChar);

			ResultStr = ResultStr + tmpStr;
		}

		return ResultStr;

	}

	// 解注册码
	private String DenLicenseKey(String LicenseKeyStr) {
		char tmpChar;
		int i, Flag;
		String ResultStr, tmpStr;
		int tmpInt;

		if (!_ValidateKey.equals(PrivateValidateKey)) {
			return "";
		}

		ResultStr = "";

		for (i = 0; i < LicenseKeyStr.length(); i++) {
			tmpChar = LicenseKeyStr.charAt(i);
			tmpInt = tmpChar;
			// 71-90(G-Z)
			// 48-57(0-9)是数字
			// 种子数

			if ((tmpInt > 70) && (tmpInt < 91)) {
				int tmpFlgInt = (tmpInt - 71) / 10;
				Flag = 23 + 10 * tmpFlgInt;
				tmpInt = tmpInt - Flag;
				tmpChar = (char) tmpInt;
				tmpStr = Character.toString(tmpChar);// String.valueOf(tmpInt);
			} else
				tmpStr = String.valueOf(tmpChar);

			ResultStr = ResultStr + tmpStr;
		}

		return ResultStr;

	}

	// 解密注册码信息
	// 返回
	// 注册公司名称?硬盘号?站点名称?用户数?试用天数?应用数?模块数?表数?流程数
	private boolean InternalGetLicenseInfo(String LicenseKeyStr1,
			String LicenseKeyStr2, String LicenseKeyStr3, String LicenseKeyStr4) {
		String StrList, tmpStr;

		if (!_ValidateKey.equals(PrivateValidateKey)) {
			return false;
		}

		try {
			StrList = LicenseKeyStr2 + LicenseKeyStr3;
			StrList = StrList.replace("-", "");

			// 注册的公司名称
			_CompanyName = asctostr(DenLicenseKey(LicenseKeyStr1));
			// 注册日期
			// _LicenseDay = Decode2(LicenseKeyStr4);
			// 注册日期 固定就是2008-01-01
			_LicenseDay = "2008-01-01";
			// 扩展注册属性 可以为空，不为空则说明需要有控制，这里的控制由WEB程序控制
			_ExLicenseCode = "";
			if (!LicenseKeyStr4.equals("")) // and ( _HasHardKey = 1 ) then
				_ExLicenseCode = Decode2(LicenseKeyStr4);

			// 注册码中包含硬盘号的后五位
			tmpStr = Decode2(DenLicenseKey(StrList.substring(0, 16)));
			tmpStr = tmpStr.replace("*", "");
			_LicenseCode = tmpStr.substring(0, tmpStr.length() - 1);
			// 注册的特征码
			tmpStr = Decode2(DenLicenseKey(StrList.substring(16, 16 + 22)));
			tmpStr = tmpStr.replace("*", "");
			_KeyCode = tmpStr.substring(0, tmpStr.length() - 1);
			// 用户数
			tmpStr = Decode2(DenLicenseKey(StrList.substring(38, 38 + 12)));
			tmpStr = tmpStr.replace("*", "");
			_UserCount = Integer.parseInt(tmpStr.substring(0,
					tmpStr.length() - 1));
			// 限制天数
			tmpStr = Decode2(DenLicenseKey(StrList.substring(50, 50 + 10)));
			tmpStr = tmpStr.replace("*", "");
			_UseDays = Integer.parseInt(tmpStr
					.substring(0, tmpStr.length() - 1));
			// 应用数
			tmpStr = Decode2(DenLicenseKey(StrList.substring(60, 60 + 10)));
			tmpStr = tmpStr.replace("*", "");
			_AppCount = Integer.parseInt(tmpStr.substring(0,
					tmpStr.length() - 1));
			// 模块数
			tmpStr = Decode2(DenLicenseKey(StrList.substring(70, 70 + 10)));
			tmpStr = tmpStr.replace("*", "");
			_ModuleCount = Integer.parseInt(tmpStr.substring(0,
					tmpStr.length() - 1));
			// 可建表数
			tmpStr = Decode2(DenLicenseKey(StrList.substring(80, 80 + 10)));
			tmpStr = tmpStr.replace("*", "");
			_TableCount = Integer.parseInt(tmpStr.substring(0,
					tmpStr.length() - 1));
			// 流程数
			tmpStr = Decode2(DenLicenseKey(StrList.substring(90, 90 + 8)));
			tmpStr = tmpStr.replace("*", "");
			_FlowCount = Integer.parseInt(tmpStr.substring(0, tmpStr.length()));

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
   
	public String get_LicenseKey1() {
		return _LicenseKey1;
	}

	public void set_LicenseKey1(String licenseKey1) {
		_LicenseKey1 = licenseKey1;
	}

	public String get_LicenseKey2() {
		return _LicenseKey2;
	}

	public void set_LicenseKey2(String licenseKey2) {
		_LicenseKey2 = licenseKey2;
	}

	public String get_LicenseKey3() {
		return _LicenseKey3;
	}

	public void set_LicenseKey3(String licenseKey3) {
		_LicenseKey3 = licenseKey3;
	}

	public String get_LicenseKey4() {
		return _LicenseKey4;
	}

	public void set_LicenseKey4(String licenseKey4) {
		_LicenseKey4 = licenseKey4;
	}

	public int get_AppCount() {
		return _AppCount;
	}

	public String get_CompanyName() {
		return _CompanyName;
	}

	public int get_FlowCount() {
		return _FlowCount;
	}

	public String get_LicenseCode() {
		return _LicenseCode;
	}

	public String get_LicenseDay() {
		return _LicenseDay;
	}

	public int get_ModuleCount() {
		return _ModuleCount;
	}

	public int get_TableCount() {
		return _TableCount;
	}

	public int get_UseDays() {
		return _UseDays;
	}

	public int get_UserCount() {
		return _UserCount;
	}

	public String get_KeyCode() {
		return _KeyCode;
	}

	public void set_ValidateKey(String validateKey) {
		_ValidateKey = validateKey;
	}

	public void set_MP1(short Value) {
		Value = 0x06EF;
		Password1 = Value;
	}

	public short get_MP1() {
		return Password1;
	}

	public void set_MP2(short Value) {

		Value = 0x2F5A;
		Password2 = Value;
	}

	public short get_MP2() {
		return Password2;
	}

	public String Get_ExLicenseCode() {
		return _ExLicenseCode;
	}

	public int Get_HasHardKey() {
		return _HasHardKey;
	}

	/** *************************************************************************** */
 
	// public static long mul(long v1,long v2)
	// {
	// BigDecimal b1 = new BigDecimal(Long.toString(v1));
	// BigDecimal b2 = new BigDecimal(Long.toString(v2));
	// return b1.multiply(b2).longValue();
	// }
	public static void main(String[] args) {

		// (new Licensing()).test();
		// BigDecimal bg = new BigDecimal();
		// bg.multiply(24*60*60*1000*99);
		// long tmpLong = 24*60*60*1000*99;//147600000*99;//
		// System.out.println(mul(14760000,99));

		// long a = 24*60;
		// System.out.println(a);
		// a = a*60;
		// System.out.println(a);
		// a = a*1000;
		// System.out.println(a);
		// a = a*99;
		// System.out.println(a);
		//		
		// long tmpLong = 24*60*60*1000;
		// System.out.println(tmpLong);
		// tmpLong = tmpLong*99;
		// System.out.println(tmpLong);

	}

}
