package src.main.java.prototypes;

import java.io.*;
import java.util.*;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * This class defines the Ultrasonic devices and reads their buffers converting the Ascii returns into a distance in mm.
 * @author Jack Rausch
 * @author Mr. Erickson
 * @author Some guy on the Internet
 *
 */
public class SimpleRead implements Runnable, SerialPortEventListener {
    private int BytesSize = 6;
    private InputStream inputStream;
    private SerialPort serialPort;
    private Thread readThread;
    private int alignment= 0;
    
    private int distance = 0;

    //UltrasonicHub handles these functions now
    /*
    public static void main(String[] args) {

    	String portIdentifierName = "/dev/ttyUSB0";

	SimpleRead reader;

	try {
		reader = SimpleRead.Create(portIdentifierName);
		reader.Listen();
		int dist;
		while (true)
		{
			dist = reader.getDistance();
			System.out.println("dist: " + dist);
			//sleep(250);
		}
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static SimpleRead Create(String portName) {
	SimpleRead reader = null;

	try {
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
            		CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println("Port: " + portId.getName());
                		if (portId.getName().equals(portName)) {
                    			SerialPort serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
                    			reader = new SimpleRead(serialPort);
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

        return reader;	
     }
    */

    //Simple Constructor
    public SimpleRead(SerialPort serialPort) {
        try {
            this.serialPort = serialPort;
            this.inputStream = this.serialPort.getInputStream();
            this.serialPort.addEventListener(this);
	    this.serialPort.notifyOnDataAvailable(true);
            this.serialPort.setSerialPortParams(57600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
            this.readThread = new Thread(this);
        } catch (IOException e) {
            System.out.println(e);
	} catch (TooManyListenersException e) {
            System.out.println(e);
        } catch (UnsupportedCommOperationException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

	public void Listen()
	{
		this.serialPort.notifyOnDataAvailable(true);
		System.out.println("Listening?");
	}

	public void Stop()
	{
		this.serialPort.notifyOnDataAvailable(false);
		//this.readThread.interrupt();
		System.out.println("Stop Listening?");
	}

    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
    public void serialEvent(SerialPortEvent event) {
	switch(event.getEventType()) {
	//Redundant cases for different data types
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        //If buffer is empty stop method
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        //Reads data available in buffer
        case SerialPortEvent.DATA_AVAILABLE:
	    int index = 0;
	    int read = 0;
	    int capacity = 6;
        alignment = 0; 
        byte[] bytes = new byte[6];

        try {
			while (index<capacity && ((read = inputStream.read(bytes, index, capacity-index)) != -1)) {
			   //System.out.printf("read: %d, capacity: %d\n", read, capacity); 
				index += read;
			}
			//System.out.printf("READ COMPLETE: %d", index);
	
	        int dist = -1;
	        //Searches for '82' which starts each datastream
			for (int i = 0; i < index; i++) 
			{
				if (bytes[i] == 82) {
					break;
				}
				alignment++; 
			}
			
			//Tells us if the datastream is unaligned
			if (alignment != 0)
			{
				for (int i = 0; i < index; i++) 
				{
				    System.out.printf("%d ", bytes[i]);
				}
				//System.out.println("");
				//System.out.printf("alignment: %d", alignment);
			}
	
			if (alignment < 2)
			{
				dist = (1000 * (bytes[1 + alignment] - 48)) + 
				       (100 * (bytes[2 + alignment] - 48)) + 
				       (10 * (bytes[3 + alignment] - 48)) + 
	                               (bytes[4 + alignment] - 48);
			}
	
			//System.out.println("dist: " + dist);
			this.distance = dist;

        } catch (IOException e) {
	    	System.out.println(e);
        } catch (Exception e) {
	    	System.out.println(e);
	    }
            break;
        }
        
    }
    
    public int getDistance(){
        return this.distance;
        	}
        	
    public void setDistance(int dist){
        this.distance = dist;
        	}
}
