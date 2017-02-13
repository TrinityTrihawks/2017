package main.java.org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import main.java.org.usfirst.frc.team4215.robot.steamworks.VisionTest;

public class Robot<VisonTest> extends IterativeRobot {

	private VisionTest visionTest1;
	
	public void robotInit(){
	
		visionTest1 = new VisionTest();
			visionTest1.visionInit();
			
	}
	
	public void teleopInit(){		
		visionTest1.visionStart();
	}
	
	public void disableInit() throws InterruptedException{
			
			visionTest1.visionStop();
	}
}
