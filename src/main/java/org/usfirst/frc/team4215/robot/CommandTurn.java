package org.usfirst.frc.team4215.robot;

import org.usfirst.frc.team4215.robot.Drivetrain.AutoMode;

import edu.wpi.first.wpilibj.command.Command;

public class CommandTurn extends Command {

	double Kp = .1;
	double Ki = .1;
	double Kd = .01;
	
	Drivetrain drivetrain;
	double angle;
	
	public void CommandTurn(double angle){
		
		drivetrain = Drivetrain.Create();
		this.angle = angle;
		requires(drivetrain);
		
	}
	
	protected void initialize(){
		drivetrain.resetEncoder();
		drivetrain.setAutoMode(AutoMode.Turn);
	    drivetrain.setPID(Kp, Ki, Kd); 
	  //drivetrain.Go(angle,angle,angle,angle); 
	    
	
	}
	
	protected void end(){
		drivetrain.disableControl();
	}
	
protected void interrupted(){
		
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
