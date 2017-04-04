package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandTurn extends Command {

	
	
	Drivetrain drivetrain;
	double angle;
	
	public void CommandTurn(double angle){
		
		drivetrain = Drivetrain.Create();
		this.angle = angle;
		
		
	}
	
	protected void initialize(){
		
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
