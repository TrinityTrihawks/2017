package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import prototypes.UltrasonicHub;

public class CommandUltrasonic extends Command {
	
	UltrasonicHub hub;
	Drivetrain drivetrain;
	PIDController correctionPID;
	AnalogGyro gyro;
	
	double correction;
	
	//values gotten from tuning
	private final double Kp = 0.012029;
	private final double Kd = 0.00046235;
	private final double Ki = 0.0375292;
	
	
	public CommandUltrasonic() {
		hub = new UltrasonicHub();
		drivetrain = Drivetrain.Create();
		gyro = new AnalogGyro(0);
		requires(drivetrain);
	}
	
	protected void initialize() {
		correction = hub.getCorrectionAngle();
		correctionPID = new PIDController(Kp, Ki, Kd, gyro, drivetrain);
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
		if (correctionPID.getAvgError() == 0){
			System.out.println("IsFinished = true");
			return true;
		}
		System.out.println("IsFinished = false");
		return false; 
		
	}
	
	
}