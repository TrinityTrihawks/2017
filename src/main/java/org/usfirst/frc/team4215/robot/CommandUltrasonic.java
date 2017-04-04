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
		requires(ultrasonichub);
	}
	
	protected void initialize() {
		correction = hub.getCorrectionAngle();
		correctionPID = new PIDController(Kp, Ki, Kd, gyro, drivetrain);
	}
	
	protected void end(){
		ultrasonicsub.stop();
	}
	
	protected void interrupted(){
		
	}
	
	@Override
	protected boolean isFinished() {
		return false; //for now
		
	}
	
	
}