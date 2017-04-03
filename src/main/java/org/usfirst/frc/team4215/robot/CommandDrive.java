package org.usfirst.frc.team4215.robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class CommandDrive extends Command {
	
	
	Drivetrain drivetrain;
	int distance;
	
	public CommandDrive(int distance){
	
		this.distance = distance;
		requires(drivetrainsub);
		
	}
	
	protected void initialize(){
		drivetrain.resetEncoder();
		drivetrain.setTalonControlMode(TalonControlMode.Speed);
	    drivetrain.setPID(.0625,0,.01); 
	    drivetrain.Go(distance,distance,distance,distance); 

	}
	
	protected void end(){
		drivetrainsub.stop();
	}
	
	protected void interrupted(){
		
	}
	

	
	

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
