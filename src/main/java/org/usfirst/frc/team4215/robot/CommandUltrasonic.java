package org.usfirst.frc.team4215.robot;

import org.usfirst.frc.team4215.robot.Drivetrain.AutoMode;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import prototypes.UltrasonicHub;

public class CommandUltrasonic extends Command {
	
	UltrasonicHub hub;
	Drivetrain drivetrain;
	PIDController correctionPID;
	
	double correction;
	
	//values gotten from tuning
	private final double Kp = 0.012029;
	private final double Kd = 0;
	private final double Ki =  0;
	
	
	public CommandUltrasonic() {
		hub = new UltrasonicHub();
		hub.addReader("/dev/ttyUSB0");
		hub.addReader("/dev/ttyUSB1"); 
		drivetrain = Drivetrain.Create();
		requires(drivetrain);
	}
	
	protected void initialize() {
		drivetrain.setAutoMode(AutoMode.Turn);
		correction = hub.getCorrectionAngle();
		correctionPID = new PIDController(Kp, Ki, Kd, 0, drivetrain, drivetrain);
		correctionPID.setSetpoint(correction);
		correctionPID.enable();
		System.out.println("Initialized");
	}
	
	protected void end(){
		correctionPID.disable();
		System.out.println("Ended");
	}
	
	protected void interrupted(){
		
	}
	
	@Override
	protected boolean isFinished() {
		if (correctionPID.getError() == 0){
			System.out.println("IsFinished = " + correction + "at" + rain.getAngle());
			return true;
		}
		System.out.println("IsFinished = false");
		return false; 
		
	}
	
	
}