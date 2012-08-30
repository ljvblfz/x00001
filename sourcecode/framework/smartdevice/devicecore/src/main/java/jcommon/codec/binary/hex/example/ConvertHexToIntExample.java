package jcommon.codec.binary.hex.example;

public class ConvertHexToIntExample {
	 
    public static void main(String[] args) {
           
            String i = "0x0101010101";
           
            int strHexNumber = convertHexToInt(i);
           
            System.out.println("Convert decimal number to hexadecimal number example");
            System.out.println("Hexadecimal value of " + i + " is " + strHexNumber);
                                                   
    }

    public static int convertHexToInt(String hexString){
		int hexInt = Long.decode(hexString).intValue();
		return hexInt;
	}
}
