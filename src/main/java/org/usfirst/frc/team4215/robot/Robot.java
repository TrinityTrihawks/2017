package main.java.org.usfirst.frc.team4215.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.IterativeRobot;
import main.java.org.usfirst.frc.team4215.robot.steamworks.VisionTest;
import main.java.prototypes.UltrasonicHub;

public class Robot<VisonTest> extends IterativeRobot {

	private VisionTest visionTest1;
	private UltrasonicHub hub;
	
	public void robotInit(){
			
		hub =  new UltrasonicHub();
		ArrayList<String> devices;
		
		hub.addReader("/dev/ttyUSB0");
		hub.addReader("/dev/ttyUSB1");
	}
	
	public void teleopInit(){
	}
	
	public void disableInit() {
	}
	
	public void teleopPeriodic(){
		ArrayList<Integer> distances = hub.getDistancefromallPorts();
		for (int i=0; i<distances.size(); i++)
		{
			System.out.print("d: " + distances.get(i) + "\t");			
		}
		System.out.println("\n");
	}
}
