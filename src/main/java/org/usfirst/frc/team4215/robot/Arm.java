package main.java.org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.Victor;

public class Arm {
	
	Victor arm = new Victor(1);
	
	public void setArm(double power) {
		if (power > 1) power = 1;
		else if (power < -1) power = -1;
		
		arm.set(power);
	}

}
