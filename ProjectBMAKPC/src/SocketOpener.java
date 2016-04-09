import java.io.IOException;
import java.util.Enumeration;
import java.io.OutputStream;
import javax.comm.*;

public class SocketOpener {
	
	static Enumeration portList;
	private static SerialPort serialPort = null;
	private static CommPortIdentifier portId = null;
	private static OutputStream outputStream = null;
	
	public static void main (String[] args){
		
		portList = CommPortIdentifier.getPortIdentifiers();
		
		while (portList.hasMoreElements()){
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL){
				if (portId.getName().equals("COM5")){				
					try{
						serialPort = (SerialPort) portId.open("TestApp",2000);
					}
					catch(PortInUseException e){
					}
					/*try{
						outputStream = serialPort.getOutputStream();
					}
					catch(IOException e){	
					}
					try{
						serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
						public SerialPort newSerialPort = new SerialPort("COM1", 19200, Parity.None, 8, StopBits.One);
					}
					catch(UnsupportedCommOperationException e){
					}
					try{
						outputStream.write(messageString.getBytes());
					}
					catch(IOException e){
					}*/
				}
			}
		}
	}
}
