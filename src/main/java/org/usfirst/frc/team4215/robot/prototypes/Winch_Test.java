package org.usfirst.frc.team4215.robot.prototypes;

//import edu.wpi.first.wpilibj.GenericHID;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

public class Winch_Test {
	Victor winch1 = new Victor(0);						// sets where the first winch motor is controlled from
	Victor winch2 = new Victor(1);						// sets where the second winch motor is controlled from
//	Joystick armJoystick = new Joystick(0);				// sets which joystick controlls the winch motors
	public Winch_Test () {
	};
	public void set(double l, double r) {
		
//		GenericHID.Hand armJoystick = GenericHID.Hand.kRight;
		
		winch1.set(l);
		winch2.set(r);
		
	}
		
}
