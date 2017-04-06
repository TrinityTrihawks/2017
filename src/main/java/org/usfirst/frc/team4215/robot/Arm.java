package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class Arm {
	double coef = .5;
	private Victor arm;
	private Encoder enc;
	private DoubleSolenoid ds;
	DigitalInput limitSwitch;
	
	public Arm() {
		arm = new Victor(1);
		
		//so that up on joystick goes up on arm
		arm.setInverted(true);
		
		enc = new Encoder(1, 2, false);
		//change the ports to whichever are the correct ones
		//reversed may need to be 'true', depending on how encoder is set up
		
		ds = new DoubleSolenoid(6, 5);
		//These are sample port numbers. Change them to the correct ones.
		//might need to include moduleNumber in parameters
		limitSwitch = new DigitalInput(0);
	}
	
	
	public void setArm(double power) {		
		if(!limitSwitch.get()& power > 0){
			power = 0;
		}
		
		if (power > 1) power = 1;
		else if (power < -1) power = -1;
		power *= coef;
		arm.set(power);
		
	}
	
	
	public double getArmPosition() {
		return enc.get();
		
		
		
		//return enc.get() / 90;
		
			//max rotation for arm will be 90 degrees
			//total CPR is 360
			//therefore, this will get a number between 0 and 1
			
	}
	
	
	public double getArmPower() {
		return arm.get();
	}
	
	
	
	public void armCompress() {
		ds.set(DoubleSolenoid.Value.kForward);
	}
	
	
	public void armDecompress() {
		ds.set(DoubleSolenoid.Value.kReverse);
	}
	
	
	public void armOff() {
		ds.set(DoubleSolenoid.Value.kOff);
	}
	

}
