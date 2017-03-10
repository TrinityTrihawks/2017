package prototypes;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
//from RXTX library
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

/**
 * This class encapsulates all the Ultrasonic devices defined by SimpleRead and allows us to iterate over them.
 * From this class the distance from one port, or all the ports is obtainable.
 * It also has a method to check if the face of the robot is parrallel to the object in front of you 
 * and returns the correction angle if it is not.
 * @author Jack Rausch
 *
 */
public class UltrasonicHub implements PIDSource {
    //an array of Strings to encapsulate port names
    private ArrayList<String> portlist;
    //an array of SimpleRead objects to encapsulate the readers
    private ArrayList<SimpleRead> readerlist = null;
    //a hashmap which assigns a port name to a reader
    private HashMap<String,SimpleRead> serialMap = new HashMap<String,SimpleRead>();
    //private static ArrayList<Integer> portReadings;
    //Constructs reader object which is where the distance is actually pulled from
    private SimpleRead reader;

    public UltrasonicHub(){
        this.portlist = new ArrayList<String>();
        this.readerlist = new ArrayList<SimpleRead>();
        this.serialMap = new HashMap<String,SimpleRead>();
    }

    /**
     * This method adds a reader to the readerlist, and creates the object. This method will also check if portName is valid.
     * @author Jack Rausch
     * @param portName (given in like form "/dev/ttyUSB0")
     * @return ArrayList<String> portList
     * @throws PortInUseException
     */
    public ArrayList<String> addReader(String portName) {
        try {
            //creates portlist to parse for given port name
            Enumeration portList = CommPortIdentifier.getPortIdentifiers();
            //continues loop for as long port list contains unchecked elements
            while (portList.hasMoreElements()) {
                //creates port ID and sets it to the next element in portList
                CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
                //checks if the port is a serial port
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    //prints out port name
                    System.out.println("Port: " + portId.getName());
                    /*
                     * checks if the port taken from the list being iterated over is the same as the port 
                     * given by the user
                     */
                    if (portId.getName().equals(portName)) {
                        /*sets serialPort to the user given port by opening a SimpleReadApp
                         * portlist is added to SimpleReadApp to specify a device using the order in which it was added
                         * i.e the first device added would be SimpleReadApp1 
                         */
                        SerialPort serialPort = (SerialPort) portId.open("SimpleReadApp"+portlist.size(), 2000);
                        //Creates the reader which is used to pull data from
                        reader = new SimpleRead(serialPort);
                        //adds the reader to array of readers which can be iterated over
                        readerlist.add(reader);
                        //logs the portName in an array so that specific readers may be specified
                        portlist.add(portName);
                    }
                } 
            }
        } catch (PortInUseException e) {
            //thrown if port in use by open(String app, int time) method
            System.out.println(e);
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return portlist;
        
    }
    
    /**
     * This method gets a distance from a single device by specifying the portname.
     * @author Jack Rausch
     * @param portName (given in like form "/dev/ttyUSB0")
     * @return int distance
     */
    public int getDistancefromPort(String portName){
        //Searches for given portName in the portlist
        for (int i = 0; i < portlist.size(); i++){
            //Checks if portName is equal to the current member of the portlist
            if (portName == portlist.get(i)){
                //gets the reader and then gets the distance from it in the same line and returns that distance
                return readerlist.get(i).getDistance();
            }
        }
        
        return -1;
                
    }
    
    /**
     * This method outputs the distances from all ports in an array of integers.
     * @author Jack Rausch
     * @return ArrayList<Integer> portReadings
     */
    public ArrayList<Integer> getDistancefromallPorts(){
        //creates an arraylist of portreadings
        ArrayList<Integer> portReadings = new ArrayList<Integer>();
        //iterates over the list of readers added
        for (int i = 0; i < readerlist.size(); i++){
            //adds the distance from each reader to the portReadings list
            portReadings.add(readerlist.get(i).getDistance());
        }
        return portReadings;
        
    }
    
    /**
     * This method checks if the face of a robot is parrallel to whatever surface its facing.
     * If so it returns a value of 0, otherwise it returns the correction angle. This method preassumes that there are two Ultrasonic sensors on the desired face.
     * @author Jack Rausch
     * @return double theta
     */
    public double getCorrectionAngle(){
        //creates and sets portReadings as an array of distances taken from the two front devices
        ArrayList<Integer> portReadings = getDistancefromallPorts();
        //sets dist1 and dist2 to the first two distances taken
        double dist1 = portReadings.get(0);
        double dist2 = portReadings.get(1);
        //gets difference
        double difference = dist1 - dist2;
        double facelength = 457.2; // Distance between sensors
        //returns 0 if the distances are equal or parrallel or their is a difference of less than 10 mm
        if (Math.abs(difference) < 10){
            return 0;
        } else{
            //sets the correction angle to tan^-1((dist1 - dist2)/facelength)
            double theta = Math.atan(difference/facelength);
            //uses 180/pi as a conversion factor to degrees because arcsine outputs radians 
            return theta*(180/Math.PI);
        }
    }

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return 0;
	}

}


