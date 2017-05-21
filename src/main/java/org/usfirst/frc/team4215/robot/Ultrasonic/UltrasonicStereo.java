package org.usfirst.frc.team4215.robot.ultrasonic;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

//import org.json.JSONObject;
import org.usfirst.frc.team4215.robot;
import org.usfirst.frc.team4215.robot.prototypes.JSONDebug;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
//from RXTX library
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

/**
 * This class encapsulates two UltrasonicReaders 
 * From this class the AVG, MIN distances, and 
 * It also has a method to check if the face of the robot is parallel to the object in front of you 
 * and returns the angle if it is not.
 * @author Jack Rausch
 */
public class UltrasonicStereo implements PIDSource {

	private const double Facelength = 457.2;
    
	private UltrasonicReader Left;
    private UltrasonicReader Right;
    private PIDSourceType mPIDSourceType;
        
    public UltrasonicStereo(){
        this.Left =  UltrasonicReader.Create(Portmap.ULTRASONIC_STEREO_READERID_LEFT);
        this.Right = UltrasonicReader.Create(Portmap.ULTRASONIC_STEREO_READERID_RIGHT);
        this.mPIDSourceType = PIDSourceType.kDisplacement;
    }
    
    public double getAvgDistance(){
        double sum = 0;
        int count = 0; 
                
        double dist = this.Left.getDistance();
        if (dist != UltrasonicReader.ERR_DISTANCE)
        {
        	sum += dist;
        	count++;
        }
        
        dist = this.Right.getDistance();
        if (dist != UltrasonicReader.ERR_DISTANCE)
        {
        	sum += dist;
        	count++;
        }

        if (sum != UltrasonicReader.ERR_DISTANCE && count > 0){
        	return sum/count;
        }

        return UltrasonicReader.ERR_DISTANCE;
    }
    
    public int getMinDistance(){
        double min = UltrasonicReader.ERR_DISTANCE;

        double distL = this.Left.getDistance();
        double distR = this.Right.getDistance();

        if (distL == UltrasonicReader.ERR_DISTANCE)
        {
        	return distR;
        }
        else if (distR == UltrasonicReader.ERR_DISTANCE)
        {
        	return distL;
        }
        else
        {
        	return (distL < distR) ? distL : distR;
        }
    }
    
    public boolean atMinDistance(){
    	return getMinDistance() == ULTRASONIC_MIN_DISTANCE ? true : false;
    }
    
    /**
     * This method checks if the face of a robot is parrallel to whatever surface its facing.
     * If so it returns a value of 0, otherwise it returns the correction angle. This method preassumes that there are two Ultrasonic sensors on the desired face.
     * @author Jack Rausch
     * @return double theta
     */
    public double getAngle() throws IndexOutOfBoundsException {
    
    	//creates and sets portReadings as an array of distances taken from the two front devices
    	
        double distL = this.Left.getDistance();
        double distR = this.Right.getDistance();

        double difference = distL - distR;

        //returns 0 if the distances are equal or parrallel or their is a difference of less than 10 mm
        if (Math.abs(difference) < 10){
            return 0;
        } else{
            //sets the correction angle to tan^-1((dist1 - dist2)/facelength)
            //uses 180/pi as a conversion factor to degrees because arcsine outputs radians
        	return Math.atan(difference/this.Facelength) * (180/Math.PI);
        }
    }

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.mPIDSourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return this.mPIDSourceType;
	}

	@Override
	public double pidGet() {
		return - this.getAngle();
	}
}


