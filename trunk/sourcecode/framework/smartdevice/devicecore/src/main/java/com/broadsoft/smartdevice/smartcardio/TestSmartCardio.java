package com.broadsoft.smartdevice.smartcardio;
 
import java.nio.ByteBuffer;
import java.util.Formatter;
import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;



/**
 *  https://forums.oracle.com/forums/thread.jspa?threadID=1750644 
 * @author Administrator
 *
 */
public class TestSmartCardio {
	
	public static void main(String[] args){
		String msg=doCard();
		System.out.println("msg is: "+msg);
	}
	public static String doCard() {
		String msg = "";  

		TerminalFactory factory = TerminalFactory.getDefault();
		try {
			List<CardTerminal> terminals = factory.terminals().list();
			msg += "Terminals: " + terminals + "\n";
			CardTerminal terminal = terminals.get(1);
			Card card = terminal.connect("T=0");
			CardChannel channel = card.getBasicChannel();
			msg += "Card Present: " + terminal.isCardPresent() + "\n";
			ResponseAPDU resp;
			// UID
			byte[] uid = new byte[] { (byte) 0xFF, (byte) 0xCA, (byte) 0x00,
					(byte) 0x00, (byte) 0x00 };
			CommandAPDU com = new CommandAPDU(uid);
			msg += "GetUID Command: " + convertBytesToHexString(com.getBytes())
					+ "\n";
			resp = channel.transmit(com);
			msg += "GetUID Response: "
					+ convertBytesToHexString(resp.getBytes()) + "\n";
			// Load Key

			byte[] baLoadkey = new byte[] { (byte) 0xFF, (byte) 0x82,
					(byte) 0x20, (byte) 0x1A, (byte) 0x06, (byte) 0xFF,
					(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
					(byte) 0xFF };
			CommandAPDU loadkey = new CommandAPDU(baLoadkey);
			msg += "LoadKey Loaded Apdu Command: "
					+ convertBytesToHexString(loadkey.getBytes()) + "\n";
			resp = channel.transmit(loadkey);
			msg += "LoadKey Response: "
					+ convertBytesToHexString(resp.getBytes()) + "\n";

			// Authenticate
			byte[] baAuth = new byte[] { (byte) 0xFF, (byte) 0x88, (byte) 0x00,
					(byte) 0x34, (byte) 0x60, (byte) 0x1A };
			CommandAPDU auth = new CommandAPDU(baAuth);
			msg += "Authenticate Apdu Command: "
					+ convertBytesToHexString(baAuth) + "\n";
			resp = channel.transmit(auth);
			msg += "Authenticate Response: "
					+ convertBytesToHexString(resp.getBytes()) + "\n";
			// Read
			byte[] baRead = new byte[] { (byte) 0xFF, (byte) 0xB0, (byte) 0x00,
					(byte) 0x34, (byte) 0x10 };
			CommandAPDU read = new CommandAPDU(baRead);
			msg += "Read APDU Command: "
					+ convertBytesToHexString(read.getBytes()) + "\n";
			resp = channel.transmit(read);
			msg += "Read Response: " + convertBytesToHexString(resp.getBytes())
					+ "\n";
			card.disconnect(false);
		} catch (CardException e) {
			msg += e;

		} 
		return msg;
	}
	
	public boolean auth(CardChannel cardChannel) throws CardException {
        byte[] baAuth = new byte[]{(byte)0xFF,(byte)0x88,(byte)0x00,(byte)0x04,(byte)0x60,(byte)0x00};
        byte[] baResp = new byte[258];
        ByteBuffer bufAuth = ByteBuffer.wrap(baAuth);
        ByteBuffer bufResp = ByteBuffer.wrap(baResp);
        //CommandAPDU auth = new CommandAPDU(baAuth);
        int output = cardChannel.transmit(bufAuth, bufResp);
 
        for (int i = 0; i < output; i++) {
            System.out.printf("%02X", baResp[i]);
        }
        System.out.println();
        
        return true;
    }
	
	public static String convertBytesToHexString(byte[] bytes) {
	    StringBuilder sb = new StringBuilder(bytes.length * 2);

	    Formatter formatter = new Formatter(sb);
	    for (byte b : bytes) {
	        formatter.format("%02x", b);
	    }

	    return sb.toString();
	}

}
