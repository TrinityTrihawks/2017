package org.usfirst.frc.team4215.robot;

import org.usfirst.frc.team4215.robot.Drivetrain.AutoMode;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import prototypes.UltrasonicHub;

public class CommandDrive extends Command {
	
	
	Drivetrain drivetrain;
	UltrasonicHub brakes;
	public double distance;
	boolean useBrakes;
	boolean repeat;
	int count = 0;
	double Kp = 0.0225;
	double Ki = 0;
	double Kd = 0.05;
	

	public CommandDrive(double distance){
		this(distance, false);
	}

	public CommandDrive(double distance, boolean useBrakes){
	
		drivetrain = Drivetrain.Create();
		brakes = new UltrasonicHub();
		brakes.addReader("/dev/ttyUSB0");
		brakes.addReader("/dev/ttyUSB1");
		this.distance = distance;
		//requires(drivetrain);
		
		this.useBrakes = useBrakes;
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
		
		if (useBrakes && brakes.atMinDistance())
		{
			System.out.println("CommandDrive: atMinDistance = true");
			return true;
			
		}
		
		return count >= 10;
	}
}