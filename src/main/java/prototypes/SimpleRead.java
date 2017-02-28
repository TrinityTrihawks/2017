package main.java.prototypes;

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
public class SimpleRead implements Runnable, SerialPortEventListener {
	//length of the data you hope to get
    private int BytesSize = 6;
    private InputStream inputStream;
    private SerialPort serialPort;
    private Thread readThread;
    //Sometimes the bytes come in an unexpected order so this alignment allows us to still read that output
    private int alignment= 0;
    
    private int distance = 0;

    
    //Simple Constructor
    public SimpleRead(SerialPort serialPort) {
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
        // sets index to zero
	    int index = 0;
	    //defines read which holds the output from the device
	    int read = 0;
	    //length of data
	    int capacity = 6;
	    //aforementioned alignment
        alignment = 0; 
        //list of bytes which has bounds of six
        byte[] bytes = new byte[6];

        try {
			while (index<capacity && ((read = inputStream.read(bytes, index, capacity-index)) != -1)) {
			   //System.out.printf("read: %d, capacity: %d\n", read, capacity); 
				index += read;
			}
			
	        int dist = -1;
	        //Searches for '82' which starts each datastream
			for (int i = 0; i < index; i++) 
			{
				//if it finds 82 stop search
				if (bytes[i] == 82) {
					break;
				}
				//if 82 is not found move to next column of data
				alignment++; 
			}
			
			//Tells us if the datastream is unaligned
			if (alignment != 0)
			{
				//prints decimal form of the bytes read 
				for (int i = 0; i < index; i++) 
				{
				    System.out.printf("%d ", bytes[i]);
				}

			}
	
			//only prints data if alignments isn't too far off
			if (alignment < 2)
			{
				/*
				 * In ASCII the decimal 48 represents 0 and 57 represents 9
				 * In each parenthetical it takes the 1 byte taking into account alignment and puts it into decimal form by subtracting 48
				 * It then scales up the value to its correct decimal place by multiplying it by a certain power of ten.
				 * It then adds them all together to get a 4 digit number representing the reading in mm.
				 * 
				 * For example, if the byte list is {49, 54, 56, 53} and the data was not misaligned.
				 * dist = (1000 * (49 - 48)) + (100 * (54 - 48)) + (10 * (56 - 48)) + (53 - 48)
				 * dist = (1000 * 1) + (100 * 6) + (10 * 8) + 5 = 1685 meaning the sensor is 1685 mm away.
				 */
				dist = (1000 * (bytes[1 + alignment] - 48)) + 
				       (100 * (bytes[2 + alignment] - 48)) + 
				       (10 * (bytes[3 + alignment] - 48)) + 
	                   (bytes[4 + alignment] - 48);
			}
	
			//sets distance equal to dist for getters and setters
			this.distance = dist;

        } catch (IOException e) {
	    	System.out.println(e);
        } catch (Exception e) {
	    	System.out.println(e);
	    }
            break;
        }
        
    }
    
    //getters and setters allowing the distance to be retrieved and set in other classes
    public int getDistance(){
        return this.distance;
        	}
        	
    public void setDistance(int dist){
        this.distance = dist;
        	}
}
