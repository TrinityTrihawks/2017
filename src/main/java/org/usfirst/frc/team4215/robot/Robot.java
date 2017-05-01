package org.usfirst.frc.team4215.robot;


import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.usfirst.frc.team4215.robot.Arm;
import org.usfirst.frc.team4215.robot.Drivetrain;
import org.usfirst.frc.team4215.robot.Drivetrain.AutoMode;
import org.usfirst.frc.team4215.robot.Drivetrain.MotorGranular;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

import com.ctre.CANTalon.TalonControlMode;
import org.usfirst.frc.team4215.robot.WinchTest;
import org.usfirst.frc.team4215.robot.prototypes.PIDTask;
import prototypes.UltrasonicHub;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Arm arm;
	Joystick leftStick = new Joystick(0);
	Joystick drivestick = new Joystick(1);
	Drivetrain drivetrain;
	WinchTest winch;
	CameraPID visionPID;
	UltrasonicHub hub;
	PIDTask camAuto;
	PIDTask ultraAuto;
	AnalogGyro gyro;
	PIDController con;
	VisionThread visionThread;
	
	CommandGroup autonomousCommand;
	
	double Kp = .01;
	double Ki = .05;
	double Kd = 0;
	
	// ID's
	int DRIVE_LEFT_JOYSTICK_ID = 3;
	int DRIVE_RIGHT_JOYSTICK_ID = RobotProperties.Drive_Right_Joystick_id;
	int STRAFE_ID = 8;
	int WINCH_ID = 1;
	int ARM_ID = 4;
	int STRAFE_DRIVE_ID = 0;
	int DRIVE_LEFT_TOP_TRIGGER = 5;
	int DRIVE_LEFT_BOTTOM_TRIGGER = 7;
	
	final int IMG_WIDTH = 320;
	final int IMG_HEIGHT = 240;
	
	AxisCamera cameraFront;
	AxisCamera cameraBack;
	
	@Override
	public void robotInit(){
		arm =  new Arm();
		leftStick = new Joystick(0);
		 drivetrain = Drivetrain.Create();
		 winch = new WinchTest();
/*
		 hub = new UltrasonicHub();
		 hub.addReader("/dev/ttyUSB0");
		 hub.addReader("/dev/ttyUSB1"); 
*/		 
		 cameraBack = CameraServer.getInstance().addAxisCamera("Back", "10.42.15.37");
		 cameraBack.setResolution(IMG_WIDTH, IMG_HEIGHT);
		 System.out.println("Back camera initialized properly");
		 // Creates the interface to the back camera

		 
		 			 
		 cameraFront = CameraServer.getInstance().addAxisCamera("Front", "10.42.15.39");
		 cameraFront.setResolution(IMG_WIDTH, IMG_HEIGHT);
		 System.out.println("Front camera initialized properly");
/*
		 visionPID = new CameraPID();
	     visionThread = new VisionThread(cameraFront, new Pipeline(), visionPID);
	     System.out.println("VisonThread initialized properly");
	     
	     visionThread.setDaemon(false);
	     System.out.println("Daemon set properly");
	     
		 visionThread.start();
		 System.out.println("VisonThread started without a hitch");
		 */
		 drivetrain.setAutoMode(AutoMode.Strafe);
		 drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);	
		 
		autonomousCommand = new AutonomousCommandCenter(); 
	}

	@Override
	public void teleopInit(){		
		//drivetrain.disableControl();
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
		try{
			ultraAuto.disable();
		}catch(Exception e){
			
		}
	    autonomousCommand.cancel();
		Scheduler.getInstance().disable();
	
	}
	
	@Override
	public void teleopPeriodic(){
		
		double left = -drivestick.getRawAxis(DRIVE_LEFT_JOYSTICK_ID);
		double right = -drivestick.getRawAxis(DRIVE_RIGHT_JOYSTICK_ID);
		double strafe = drivestick.getRawAxis(STRAFE_DRIVE_ID);
		boolean isStrafing = drivestick.getRawButton(STRAFE_ID);
		
		Drivetrain.MotorGranular mode = Drivetrain.MotorGranular.NORMAL;
		if(drivestick.getRawButton(DRIVE_LEFT_BOTTOM_TRIGGER) 
				&& !drivestick.getRawButton(DRIVE_LEFT_TOP_TRIGGER)){
			 mode = Drivetrain.MotorGranular.FAST;
		}
		
		else if(!drivestick.getRawButton(DRIVE_LEFT_BOTTOM_TRIGGER) 
					&& drivestick.getRawButton(DRIVE_LEFT_TOP_TRIGGER)){
			mode = Drivetrain.MotorGranular.SLOW;
		}
		
		drivetrain.drive(left, right, strafe,isStrafing,mode);
		
		if(leftStick.getRawButton(1)){
			arm.armCompress();
		}
		
		if(leftStick.getRawButton(2)){
			arm.armDecompress();
		}
		if(!leftStick.getRawButton(1)&&!leftStick.getRawButton(2)){
			arm.armOff();
		}
		
		arm.setArm(leftStick.getRawAxis(1));		
		winch.set(leftStick.getRawAxis(4));
	}
	
	@Override
	public void autonomousInit(){
		//Scheduler.getInstance().enable();
		//if (autonomousCommand != null){
		//	autonomousCommand.start();
		//}	
		drivetrain.setPID(10, 0, 0);
		drivetrain.setTalonControlMode(TalonControlMode.Position);
		drivetrain.drive(24, -24, 0, false, MotorGranular.NORMAL);
	}
	
	@Override
	public void autonomousPeriodic() {
		//Scheduler.getInstance().run();		
	}
	
	@Override
	public void disabledInit(){
		Scheduler.getInstance().disable();
		autonomousCommand.cancel();
	}
	
	@Override
	public void disabledPeriodic(){
	}
}
