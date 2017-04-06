package org.usfirst.frc.team4215.robot;

import org.usfirst.frc.team4215.robot.Drivetrain.AutoMode;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

public class CommandTurn extends Command {

	private final double Kp = 0.012029;
	private final double Kd = 0.00046235;
	private final double Ki = 0.0375292;
	
	
	Drivetrain drivetrain;
	double angle;
	PIDController conGyro;
	
	public CommandTurn(double angle){
		
		drivetrain = Drivetrain.Create();
		this.angle = angle;
		conGyro= new PIDController(Kp,Ki,Kd,2,drivetrain,drivetrain);
		conGyro.setSetpoint(angle);
		//requires(drivetrain);
		
	}
	
	protected void initialize(){
		drivetrain.disableControl();
		drivetrain.resetEncoder();
		drivetrain.calibrateGyro();
		drivetrain.setAutoMode(AutoMode.Turn);
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
	    drivetrain.setPID(Kp, Ki, Kd);
	    conGyro.enable();
		System.out.println("CommandTurn Initialized");
	    //drivetrain.Go(angle,angle,angle,angle); 
	}
	
	protected void end(){
		System.out.println("CommandTurn to" + angle + " finished at " + drivetrain.getAngle());
		conGyro.disable();
	}
	
	protected void interrupted(){
		System.out.println("COmmandTurn Interuppted");
	}
	
	@Override
	protected boolean isFinished() {
		double[] volts = drivetrain.getVoltages();
		System.out.println(volts[0] + ", " + volts[1] + ", " + volts[2] + ", " + volts[3]);
		return Math.abs(conGyro.getError()) < Math.abs(4);
	}

}
