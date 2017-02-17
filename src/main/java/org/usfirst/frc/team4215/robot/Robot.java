package main.java.org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import main.java.org.usfirst.frc.team4215.robot.steamworks.VisionTest;

public class Robot<VisonTest> extends IterativeRobot {

	private VisionTest visionTest1;
	Arm arm;
	Joystick drivestick;
	
	public void robotInit(){
	arm =  new Arm();
	drivestick = new Joystick(1);
	
	
	}
	
	public void teleopInit(){		
		
	}
	public void teleopPeriodic(){
		if(drivestick.getRawButton(1)){
			arm.armCompress();
			
		}
		if(drivestick.getRawButton(2)){
			arm.armDecompress();
		}
		if(!drivestick.getRawButton(1)&&!drivestick.getRawButton(2)){
			arm.armOff();
		}
		
		arm.setArm(drivestick.getRawAxis(1));
	
	}
	public void disableInit() throws InterruptedException{
			
			visionTest1.visionStop();
	}
}
