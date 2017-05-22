package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class Arm {
	double coef = .5;
	private Victor armMotor;
	private Encoder enc;
	private DoubleSolenoid ds;
	DigitalInput limitSwitch;
	
	
	public Arm() {
		armMotor = new Victor(Portmap.VICTOR_ARM_ID);
		armMotor.setInverted(true);
		enc = new Encoder(1, 2, false);
		ds = new DoubleSolenoid(6, 5);
		limitSwitch = new DigitalInput(Portmap.LIMITSWITCH_DIO_ID);
	}
	
	
	public void setArm(double power) {

		if(!limitSwitch.get())
		{
			power = 0;
			this.armMotor.stopMotor();
			return;
		}
		
		if (power > 1) 
		{
			power = 1;
		}
		else if (power < -1)
		{
			power = -1;
		}
		
		power *= coef;
		this.armMotor.set(power);
	}
	
	public double getArmPosition() {
		return enc.get();
	}
		
	public double getArmPower() {
		return this.armMotor.get();
	}
	
	public void ClawCompress() {
		this.ds.set(DoubleSolenoid.Value.kForward);
	}
	
	public void ClawDecompress() {
		this.ds.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void ClawOff() {
		this.ds.set(DoubleSolenoid.Value.kOff);
	}
}
