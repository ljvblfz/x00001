package demo2;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		Enumeration portList = CommPortIdentifier.getPortIdentifiers(); //得到当前连接上的端口
		
		CommUtil comm3 = new CommUtil(portList,"COM3");
		int i = 0;
		while(i<5)
		{
			Thread.sleep(3000);
			comm3.send("hello");
			i++;
		}
		comm3.closePort();
	}

}
