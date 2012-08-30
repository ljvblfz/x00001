package jcommon.codec.binary.hex.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jcommon.codec.binary.hex.HexSupport;

public class FileHexExample {

	static String command2="02115b00912aa83825f5915d4caf6554446d767bfff01473e60a959d74ceb3b5fd611d1284a504533cb1d4f9279c7c3feff0bb3cdc3f0f25ee3d7c03";
	static String strDir="D:\\test\\io.bin";
	public static void main(String[] args) throws IOException{
//		write();
		read();
	}
	
	private static void read() throws IOException, FileNotFoundException {
		File file=new File(strDir);
	 
        FileInputStream fin = new FileInputStream(file);  
        int length=fin.available();
        byte[] bytes=new byte[length];
        fin.read(bytes);
        System.out.println(""+HexSupport.toHexString(bytes));
        fin.close();
	}
	
	
	private static void write() throws IOException, FileNotFoundException {
		File file=new File(strDir);
		if(file.exists()){
			file.delete();
			file.createNewFile(); 
		}else{ 
			file.createNewFile();
		}
//        FileInputStream fin = new FileInputStream(new File(strDir)); 
        FileOutputStream fout = new FileOutputStream(file);
        
        byte[] bytes=HexSupport.toByteArray(command2);
        fout.write(bytes);
        fout.close();
	}
	
	
	
	
}


