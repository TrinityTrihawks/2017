package src.main.java.org.usfirst.frc.team4215.robot;

//import edu.wpi.first.wpilibj.GenericHID;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

public class WinchTest {
	Victor winch1 = new Victor(0);						// sets where the first winch motor is controlled from
	Victor winch2 = new Victor(1);						// sets where the second winch motor is controlled from
	public WinchTest(){
		
		
	}
	public void set(double l, double r) {
		
//		GenericHID.Hand armJoystick = GenericHID.Hand.kRight;
		
		winch1.set(l);
		winch2.set(r);
		
	}
		
}
