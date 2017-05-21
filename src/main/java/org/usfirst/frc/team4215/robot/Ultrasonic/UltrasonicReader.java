package org.usfirst.frc.team4215.robot.ultrasonic;

import java.io.*;
import java.util.*;

//from RXTX
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * This class defines an Ultrasonic device and reads its buffer converting the Ascii returns into a distance in mm.
 * @author Jack Rausch
 * @author Mr. Erickson
 *
 */
public class UltrasonicReader implements Runnable, SerialPortEventListener {

	public static const int MAX_DISTANCE = 5000;
	public static const int MIN_DISTANCE = 300;
	public static const int ERR_DISTANCE = -1;
	
	private static HashMap<string,UltrasonicReader> ReaderMap = new HashMap<string,UltrasonicReader>();
	
    //length of the data you hope to get
    private int BytesSize = 6;
    private InputStream inputStream;
    private SerialPort serialPort;
    private Thread readThread;

    //Sometimes the bytes come in an unexpected order so this alignment allows us to still read that output
    private int alignment = 0;
    private int distance = 0;

    private string Name;
    
    // UltrasonicReader Constructor
    private UltrasonicReader() {    	
    }

    // UltrasonicReader Constructor
    private UltrasonicReader(SerialPort serialPort, string name) {
        try {
            //defines port
            this.serialPort = serialPort;
            //defines inputStream from buffer
            this.inputStream = this.serialPort.getInputStream();
            //listens for data
            this.serialPort.addEventListener(this);
            //sets the port to constantly read data
            this.serialPort.notifyOnDataAvailable(true);
            //sets the parameters for class
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

        this.Name = name;
    }

    public static UltrasonicReader Create(String portName)
    {
        try 
        {
        	String extendedPortName = "UltrasonicReader_" + portName;
        	
        	if (UltrasonicReader.ReaderMap == null) 
        	{
        		UltrasonicReader.ReaderMap = new HashMap<string,UltrasonicReader>();
        	}
        	else if (UltrasonicReader.ReaderMap.containsKey(extendedPortName))
        	{
        		return UltrasonicReader.ReaderMap[extendedPortName];
        	}
        	
            //creates portlist to parse for given port name
            Enumeration portList = CommPortIdentifier.getPortIdentifiers();
            //continues loop for as long port list contains unchecked elements
            while (portList.hasMoreElements()) 
            {
                //creates port ID and sets it to the next element in portList
                CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
                //checks if the port is a serial port
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) 
                {
                    // DEBUG only prints out port name
                    // System.out.println("Port: " + portId.getName());

                    if (portId.getName().equals(portName)) 
                    {
                        SerialPort serialPort = (SerialPort) portId.open(extendedPortName, 2000);
                        //Creates the reader which is used to pull data from
                        UltrasonicReader.ReaderMap.Add( new UltrasonicReader(serialPort, extendedPortName) );
                        
                        return UltrasonicReader.ReaderMap[extendedPortName];
                    }
                } 
            }
        } catch (PortInUseException e) {
            //thrown if port in use by open(String app, int time) method
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
   
    public void serialEvent(SerialPortEvent event) 
    {
		switch(event.getEventType()) 
		{
	        case SerialPortEvent.BI:
	        case SerialPortEvent.OE:
	        case SerialPortEvent.FE:
	        case SerialPortEvent.PE:
	        case SerialPortEvent.CD:
	        case SerialPortEvent.CTS:
	        case SerialPortEvent.DSR:
	        case SerialPortEvent.RI:
	        	return;
	        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
		        //If buffer is empty stop method
	        	this.Stop();
	        case SerialPortEvent.DATA_AVAILABLE:
		        //Reads data available in buffer
	        	break;
	        default:
	        	return;
		}
		
        // sets index to zero
        int index = 0;
        // defines read which holds the output from the device
        int read = 0;
        // length of data
        int capacity = 6;
        // aforementioned alignment
        alignment = 0; 
        // 
        int dist = -1;
        //
        byte[] bytes = new byte[capacity];

        try {
			while (index<capacity && ((read = inputStream.read(bytes, index, capacity-index)) != -1)) {
				index += read;
			}
	
	        //Searches for '82' which starts each datastream
			for (int i = 0; i < index; i++) 
			{
				if (bytes[i] == 82) {
					break;
				}
				alignment++; 
			}
			
			//Tells us if the datastream is unaligned
			/*
			if (alignment != 0)
			{
				for (int i = 0; i < index; i++) 
				{
				    System.out.printf("%d ", bytes[i]);
				}
				//System.out.println("");
				//System.out.printf("alignment: %d", alignment);
			}
			*/
	
			if (alignment < 2)
			{
				dist = (1000 * (bytes[1 + alignment] - 48)) + 
				       (100 * (bytes[2 + alignment] - 48)) + 
				       (10 * (bytes[3 + alignment] - 48)) + 
                       (bytes[4 + alignment] - 48);
			}
	
			if (dist >= UltrasonicReader.MIN_DISTANCE && dist <= UltrasonicReader.MAX_DISTANCE)
			{
				this.distance = dist;
			}
			else
			{
				this.distance = UltrasonicReader.ERR_DISTANCE;
			}
        } 
        catch 
        {
			this.distance = UltrasonicReader.ERR_DISTANCE;
        }
    }

	public int getDistance()
	{
	    return this.distance;
	}
	
	public string getName()
	{
		return this.Name;
	}
}
