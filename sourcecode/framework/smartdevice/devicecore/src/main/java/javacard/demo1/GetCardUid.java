package javacard.demo1;

import java.util.Arrays;
import javax.smartcardio.*;

/**
 * http://stackoverflow.com/questions/12011726/reading-block-from-mifare-classic-using-javax-smartcardio
 * http://www.pikopong.com/blog/2009/02/11/reading-mifare-1k-card-using-java-in-linux/
 * 
 * https://forums.oracle.com/forums/thread.jspa?threadID=1750644   --->Good Article
 * @author Administrator
 *
 *
 *javax.smartcardio is an API written to use ISO 7816-4 commands.
 *
 */
public class GetCardUid {
	public byte[] getCardUID() throws CardException {
	    CardTerminals terminals = TerminalFactory.getDefault().terminals();
	    CardTerminal terminal = terminals.list().get(0);
	    Card card = terminal.connect("*");
	    CardChannel channel = card.getBasicChannel();
	    CommandAPDU command = new CommandAPDU( new byte[] { (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0xD4, (byte) 0x4A, (byte) 0x01, (byte) 0x00 });
	    ResponseAPDU response = channel.transmit(command);
	    card.disconnect(true);
	    if (response.getSW1() == 0x90) {
	        byte[] data = response.getData();
	        data = Arrays.copyOfRange(data, 0x08, data.length);
	        return data;
	    }
	    return new byte[] {};
	}
}
