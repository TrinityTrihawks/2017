package org.usfirst.frc.team4215.robot;

import org.usfirst.frc.team4215.robot.Drivetrain.AutoMode;
import org.usfirst.frc.team4215.robot.prototypes.PIDTask;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class CommandVision extends Command {

	PIDController camAuto;
	Drivetrain drivetrain;
	double Kp = .01;
	double Ki = .05;
	double Kd = 0;
	AxisCamera cameraCMD;
	int IMG_WIDTH = 320;
	int IMG_HEIGHT = 240;		
	CameraPID visionPID;
	VisionThread visionThread;
	
	public CommandVision(AxisCamera cameraCMD){
		 visionPID = new CameraPID();
		 visionThread = new VisionThread(cameraCMD, new Pipeline(), visionPID);
		 System.out.println("VisonThread initialized properly");
		 
		 visionThread.setDaemon(false);
		 System.out.println("Daemon set properly");
		 

		drivetrain = Drivetrain.Create();
		drivetrain.setAutoMode(AutoMode.Strafe);
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
		requires(drivetrain);
	}
	
	protected void initialize(){
		visionThread.start();
		System.out.println("VisonThread started without a hitch");
		camAuto = new PIDController(Kp, Ki, Kd, visionPID, drivetrain);
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
