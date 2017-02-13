package main.java.org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class Arm {
	
	Victor arm = new Victor(1);
	Encoder enc = new Encoder(1, 2, false);
	//change the ports to whichever are the correct ones
	//reversed may need to be 'true', depending on how encoder is set up
	
	public Arm() {
		
	}
	
	
	
	public void setArm(double power) {
		if (power > 1) power = 1;
		else if (power < -1) power = -1;
		
		arm.set(power);
	}
	
	
	public double getArmPosition() {
		return enc.get() / 90;
		//max rotation for arm will be 90 degrees
		//total CPR is 360
		//therefore, this will get a number between 0 and 1
		
	}
	
	
	public double getArmPower() {
		return arm.get();
	}

}
