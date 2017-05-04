package org.usfirst.frc.team4215.robot;

//import edu.wpi.first.wpilibj.GenericHID;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

public class WinchTest {
	Victor winch1 = new Victor(Portmap.VICTOR_WINCH_ID1);						// sets where the first winch motor is controlled from
	Victor winch2 = new Victor(Portmap.VICTOR_WINCH_ID2);						// sets where the second winch motor is controlled from

	public void set(double l) {
		if(l <0){
			l = 0;
		}
		winch1.set(l);
		winch2.set(l);
		
	}
		
}
