package main.java.org.usfirst.frc.team4215.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.IterativeRobot;
import main.java.org.usfirst.frc.team4215.robot.steamworks.VisionTest;
import main.java.prototypes.UltrasonicHub;

public class Robot<VisonTest> extends IterativeRobot {

	private VisionTest visionTest1;
	private UltrasonicHub hub;
	
	public void robotInit(){

		UltrasonicHub hub =  new UltrasonicHub();
		ArrayList<String> devices = hub.addReader("/dev/ttyUSB0");
		System.out.println(devices);
		
	}
	
	public void teleopInit(){		
	}
	
	public void disableInit() {
	}
	
	public void teleopPeriodic(){
		
		int dist = hub.getDistancefromPort("/dev/ttyUSB0");
		System.out.println(dist);
		ArrayList<Integer> portReadings = hub.getDistancefromallPorts();
		System.out.println(portReadings);
	}
}
