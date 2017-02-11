package main.java.prototypes;

import java.util.ArrayList;
import java.util.Enumeration;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

public class UltrasonicHub {
	public int dist;
	private String serialport;
	static ArrayList<String> portlist;
	static ArrayList<SimpleRead> readerlist = null;
	private static ArrayList<Integer> portReadings;
	
	private SimpleRead reader;
	
	public static void main(String[] args){
		ArrayList<String> devices = addPort("/dev/ttyUSB0");
		System.out.println(devices);
		getReaders(devices);
		System.out.println(readerlist);
		SimpleRead reader = readerlist.get(0);
		System.out.println(reader);
		int dist = getDistancefromPort(reader);
		System.out.println(dist);
		ArrayList<Integer> portReadings = getDistancefromallPorts(readerlist);
		System.out.println(portReadings);
		
		
	}
	/*
	public UltrasonicHub(){
		this.dist = dist;
		this.portlist = portlist;
		this.reader = reader;
		this.readerlist = readerlist;
		this.serialport = serialport;
	}
	*/
	
	public static ArrayList<String> addPort(String serialport){
		ArrayList<String> portlist = new ArrayList<String>();
		portlist.add(serialport);
		return portlist;
		
	}
	
	public static int getDistancefromPort(SimpleRead reader){
		int dist = reader.getDistance();
		return dist;
	}
	
	public static ArrayList<Integer> getDistancefromallPorts(ArrayList<SimpleRead> readerlist){
		ArrayList<Integer> portReadings = new ArrayList<Integer>();
		portReadings = null;
		for (int i = 0; i < readerlist.size(); i++){
			SimpleRead reader = readerlist.get(i);
			int dist = getDistancefromPort(reader);
			portReadings.add(dist);
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
	public static ArrayList<SimpleRead> getReaders(ArrayList<String> portlist) {
		SimpleRead reader = null;
		if (readerlist == null){
			for (int i = 0; i < portlist.size(); i++){
				try {
					Enumeration portList = CommPortIdentifier.getPortIdentifiers();
					while (portList.hasMoreElements()) {
			            		CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
						if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
							System.out.println("Port: " + portId.getName());
			                		if (portId.getName().equals(portlist.get(i))) {
			                    			SerialPort serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
			                    			reader = new SimpleRead(serialPort);
			                    			readerlist.add(reader);
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
		
			     }
			return readerlist;
		} else {
			return readerlist;
		}
	}

}


