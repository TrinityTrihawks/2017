package src.main.java.prototypes;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

/**
 * This class encapsulates all the Ultrasonic devices defined by SimpleRead and allows us to iterate over them.
 * @author Jack Rausch
 *
 */
public class UltrasonicHub {
	public int dist;
	//private String serialport;
	private ArrayList<String> portlist;
	private ArrayList<SimpleRead> readerlist = null;
	private HashMap<String,SimpleRead> serialMap = new HashMap<String,SimpleRead>();
	//private static ArrayList<Integer> portReadings;
	
	private SimpleRead reader;
	
	
	
	
	public UltrasonicHub(){
		//this.dist = dist;
		this.portlist = new ArrayList<String>();;
		//this.reader = reader;
		this.readerlist = new ArrayList<SimpleRead>();
		//this.serialport = serialport;
		this.serialMap = new HashMap<String,SimpleRead>();
	}
	
	
	public ArrayList<String> addReader(String serialport){
		try {
			Enumeration portList = CommPortIdentifier.getPortIdentifiers();
			while (portList.hasMoreElements()) {
        		CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					System.out.println("Port: " + portId.getName());
                		if (portId.getName().equals(serialport)) {
                    			SerialPort serialPort = (SerialPort) portId.open("SimpleReadApp"+portlist.size(), 2000);
                    			reader = new SimpleRead(serialPort);
                    			readerlist.add(reader); 
                    			portlist.add(serialport);
                		}
                	}
            	}
	        } catch (PortInUseException e) {
	            System.out.println(e);
	            return null;
	        } catch (Exception e) {
	            System.out.println(e);
	            return null;
	        }
		return portlist;
		
	}
	
	public int getDistancefromPort(String serialport){
		for (int i = 0; i < portlist.size(); i++){
			if (serialport == portlist.get(i)){
				return readerlist.get(i).getDistance();
			}
		}
		return -1;
			
	}
	
	public ArrayList<Integer> getDistancefromallPorts(){
		ArrayList<Integer> portReadings = new ArrayList<Integer>();
		for (int i = 0; i < readerlist.size(); i++){
			portReadings.add(readerlist.get(i).getDistance());
		}
		return portReadings;
		
	}
	
	/*
	public ArrayList<SimpleRead> getReaderList(){
		readerlist = CreateReaders(portlist);
		return readerlist;
	}
	*/
	/*public static ArrayList<SimpleRead> createReaders(ArrayList<String> portlist){
		ArrayList<SimpleRead> readerlist = new ArrayList<SimpleRead>();
		try {
				for (int i = 0; i < portlist.size(); i++){
					reader = SimpleRead.Create(portlist.get(i)); 
					readerlist.add(reader);
			}
		} catch (NullPointerException e){
			System.err.println("No Devices Found");
		}
		return readerlist;
		
	}
	*/

}


