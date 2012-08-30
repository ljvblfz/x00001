package jcommon.codec.binary.hex.example;

public class ConvertIntToHexExample {
	 
    public static void main(String[] args) {
           
            String i = "32";
           
            String strHexNumber = getHexValueFromIntString(i);
           
            System.out.println("Convert decimal number to hexadecimal number example");
            System.out.println("Hexadecimal value of " + i + " is " + strHexNumber);
                                                   
    }

	private static String getHexValueFromIntString(String i) {
		String strHexNumber = Integer.toHexString(Integer.valueOf(i));
		return strHexNumber;
	}
}
