package com.broadsoft.smartdevice.rxtx;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class RxtxExample{
	
	public static void main(String[] args) {
		byte[] pacote  = new byte[7]; 

		pacote[0] = 0x02;
		pacote[1] = 0x04;
		pacote[2] = 0x00 ;
		pacote[3] = 0x1B ;
		pacote[4] = 0x06 ;
		pacote[5] = (byte) (pacote[3] + pacote[4]);
		pacote[6] = 0x00;
		
		try {
			CommPortIdentifier ci = CommPortIdentifier.getPortIdentifier("COM2");
			SerialPort port = (SerialPort) ci.open("tst", 2000);
			
			if(ci.getPortType() == CommPortIdentifier.PORT_SERIAL){
				
			
			port.setSerialPortParams(9600,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			
			port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);//FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
			
			OutputStream out = port.getOutputStream();
			InputStream in = port.getInputStream();
			
			out.write(pacote);	
			out.flush();
			port.setRTS(true);
			port.setDTR(true);
						
			
			long l = System.currentTimeMillis() + 5000;
			
			while(l > System.currentTimeMillis()){
				int i = in.read();

				if(i == -1){
					break;
				}
				
				System.out.print(i);
			}
			 
			
			out.close();
			port.close();
			
			}
			
			
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}



