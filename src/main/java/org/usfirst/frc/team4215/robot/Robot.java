package main.java.org.usfirst.frc.team4215.robot;

import org.usfirst.frc.team4215.robot.prototypes.Winch_Test;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {
	Joystick winchJoystick = new Joystick(0);
Winch_Test winch = new Winch_Test();
public void robotInit(){
	}

	public void teleopInit() {
	
		double l = winchJoystick.getRawAxis(2);
		

		winch.set(l, l);
		
	}

}
