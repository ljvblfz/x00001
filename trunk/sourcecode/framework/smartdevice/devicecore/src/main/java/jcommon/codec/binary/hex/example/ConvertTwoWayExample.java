package jcommon.codec.binary.hex.example;

public class ConvertTwoWayExample {
	
	public static String HEX_PREFIX="0x";
	 
    public static void main(String[] args) {
           
            String iorig = "255";
           
            String strHexNumber = convertIntToHex(iorig);
            
            System.out.println("Hexadecimal value of " + iorig + " is " + strHexNumber);
            int ival=convertHexToInt(strHexNumber);
            System.out.println("Intadecimal value of " + strHexNumber + " is " + ival);
             
    }

	private static String convertIntToHex(String i) {
		String strHexNumber = HEX_PREFIX+Integer.toHexString(Integer.valueOf(i));
		return strHexNumber;
	}
	
	public static int convertHexToInt(String hexString){
		int hexInt = Long.decode(hexString).intValue();
		return hexInt;
	}
}
