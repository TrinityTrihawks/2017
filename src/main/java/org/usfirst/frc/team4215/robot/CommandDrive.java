package org.usfirst.frc.team4215.robot;

import org.usfirst.frc.team4215.robot.Drivetrain.AutoMode;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class CommandDrive extends Command {
	
	
	Drivetrain drivetrain;
	public double distance;
	boolean repeat;
	int count = 0;
	double Kp = 0.0725;
	double Ki = 0;
	double Kd = 0.01;
	
	int margin;
	public CommandDrive(double distance, int margin){
	
		drivetrain = Drivetrain.Create();
		this.distance = distance;
		this.margin = margin;
		//requires(drivetrain);
		
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
	
	int E_0 = 0;
	int limit = 10;
	@Override
	protected boolean isFinished() {
		int[] Array =  drivetrain.getClosedLoopError();
		System.out.println(" IsFinished   " + Array[0]);
		if(E_0 == Array[0])
			count++;
		else{
			E_0 = Array[0];
			count = 0;
		}
		return count >= 10;
	}
}