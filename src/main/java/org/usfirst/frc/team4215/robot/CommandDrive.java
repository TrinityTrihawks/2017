package org.usfirst.frc.team4215.robot;

import org.usfirst.frc.team4215.robot.Drivetrain.AutoMode;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class CommandDrive extends Command {
	
	
	Drivetrain drivetrain;
	public double distance;
	boolean repeat;
	int count = 0;
	double Kp = 0.0225;
	double Ki = 0;
	double Kd = 0.05;
	
	int margin;

	public CommandDrive(double distance, int margin){
		this(distance, margin, false);
	}

	public CommandDrive(double distance, int margin, boolean useBrakes){
	
		drivetrain = Drivetrain.Create();
		this.distance = distance;
		this.margin = margin;
		this.drivetrain.setBrakes(useBrakes);
		//requires(drivetrain);
	}
	
	protected void initialize(){
		drivetrain.resetEncoder();
		drivetrain.setTalonControlMode(TalonControlMode.Position);
		drivetrain.setAutoMode(AutoMode.Distance);
	    drivetrain.setPID(Kp, Ki, Kd); 
	    drivetrain.enableControl();
	    drivetrain.Go(distance,distance,distance,distance); 
	    System.out.println("CommandDrive Initialized");
	}
	
	protected void end(){
		//drivetrain.disableControl();
		System.out.println("CommandDrive Ended");
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
	}
	
	protected void interrupted(){
		System.out.println("CommandDrive Interrupted");
		
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
		
		if (drivetrain.isClosedLoopDone(0))
		{
			System.out.println("CommandDrive: isClosedLoopDone = true");
			drivetrain.brakeMode();
			drivetrain.hardStop();
			return true;
			
		}
		
		return count >= 10;
	}
}