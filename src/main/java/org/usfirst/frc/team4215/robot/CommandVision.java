package org.usfirst.frc.team4215.robot;

import org.usfirst.frc.team4215.robot.Drivetrain.AutoMode;
import org.usfirst.frc.team4215.robot.prototypes.PIDTask;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

public class CommandVision extends Command {

	PIDController camAuto;
	Drivetrain drivetrain;
	double Kp = .01;
	double Ki = .05;
	double Kd = 0;
	
	
	
	public CommandVision(vision){
		drivetrain = Drivetrain.Create();
		drivetrain.setAutoMode(AutoMode.Strafe);
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
		requires(drivetrain);
		 camAuto = new PIDController(Kp, Ki, Kd, vision, drivetrain);

	}
	protected void initialize(){
		camAuto.enable();
		drivetrain.enableControl();
	}
	
	protected void end(){
		camAuto.disable();
		drivetrain.disableControl();
	}
	
protected void interrupted(){
		
	}
	
	@Override
	protected boolean isFinished() {
		return camAuto.getAvgError() == 0 ? true: false;
	}

}
