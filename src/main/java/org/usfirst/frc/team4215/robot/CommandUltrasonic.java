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
	private final double Kd = 0.00046235;
	private final double Ki = 0.0375292;
	
	
	public CommandUltrasonic() {
		hub = new UltrasonicHub();
		hub.addReader("/dev/ttyUSB0");
		hub.addReader("/dev/ttyUSB1"); 
		drivetrain = Drivetrain.Create();
		correctionPID = new PIDController(Kp, Ki, Kd, 0, drivetrain, drivetrain);

		//requires(drivetrain);
	}
	
	protected void initialize() {
		drivetrain.calibrateGyro();
		correction = hub.getCorrectionAngle();
		correctionPID.setSetpoint(correction);
		drivetrain.setAutoMode(AutoMode.Turn);
	    drivetrain.setPID(Kp, Ki, Kd);
		correctionPID.enable();
		System.out.println("Initialized Ultrasonic");
	}
	
	protected void end(){
		correctionPID.disable();
		System.out.println("CommandUltrasonic Ended");
	}
	
	protected void interrupted(){
		System.out.println("CommandUltrasonic Interrupted");
	}
	
	@Override
	protected boolean isFinished() {
		if (Math.abs(correctionPID.getAvgError()) <= Math.abs(4)){
			System.out.println("IsFinished = " + correction + "at" + drivetrain.getAngle());
			return true;
		}
		System.out.println("IsFinished = false");
		return false; 
		
	}
	
	
}