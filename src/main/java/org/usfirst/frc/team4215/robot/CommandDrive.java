package org.usfirst.frc.team4215.robot;

import org.usfirst.frc.team4215.robot.Drivetrain.AutoMode;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class CommandDrive extends Command {
	
	
	Drivetrain drivetrain;
	public double distance;
	
	double Kp = 0.0625;
	double Ki = 0;
	double Kd = 0.01;
	
	public CommandDrive(double distance){
	
		drivetrain = Drivetrain.Create();
		this.distance = distance;
		requires(drivetrain);
		
	}
	
	protected void initialize(){
		drivetrain.resetEncoder();
		drivetrain.setTalonControlMode(TalonControlMode.Position);
		drivetrain.setAutoMode(AutoMode.Distance);
	    drivetrain.setPID(Kp, Ki, Kd); 
	    drivetrain.enableControl();
	    drivetrain.Go(distance,distance,distance,distance); 
	    System.out.println("Initialized");
	}
	
	protected void end(){
		drivetrain.disableControl();
		System.out.println("Ended");
	}
	
	protected void interrupted(){
		System.out.println("Interrupted");
	}
	
	@Override
	protected boolean isFinished() {
		double[] Array =  drivetrain.getDistance();
		System.out.println(" IsFinished   " + Array[0]);		
		return drivetrain.isClosedLoopDone();
	}
}