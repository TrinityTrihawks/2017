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
	Joystick leftStick;
	Joystick drivestick;
	Drivetrain drivetrain;
	WinchTest winch;
	CameraPID visionPID;
	UltrasonicHub hub;
//	PIDTask camAuto;
//	PIDTask ultraAuto;
//	AnalogGyro gyro;
//	PIDController con;
//	VisionThread visionThread;
	
	//CommandGroup autonomousCommand;
	
	double Kp = .01;
	double Ki = .05;
	double Kd = 0;
	
	
	
	final int IMG_WIDTH = 320;
	final int IMG_HEIGHT = 240;
	SimpleCsvLogger logger = new SimpleCsvLogger();
//	AxisCamera cameraFront;
//	AxisCamera cameraBack;
	
	@Override
	public void robotInit(){

		//leftStick = new Joystick(Portmap.OP2_JOYSTICK_ID); //This joystick broke at EMCC, 9/16/17 and was removed
		drivestick = new Joystick(Portmap.DRIVE_JOYSTICK_ID);

		drivetrain = Drivetrain.Create();
		arm =  new Arm();
		winch = new WinchTest();

		/*
		 hub = new UltrasonicHub();
		 hub.addReader("/dev/ttyUSB0");
		 hub.addReader("/dev/ttyUSB1"); 
		*/
		
		/*
		cameraBack = CameraServer.getInstance().addAxisCamera("Back", "10.42.15.37");
		cameraBack.setResolution(IMG_WIDTH, IMG_HEIGHT);
		System.out.println("Back camera initialized properly");
		 // Creates the interface to the back camera

		 
		 			 
		cameraFront = CameraServer.getInstance().addAxisCamera("Front", "10.42.15.39");
		cameraFront.setResolution(IMG_WIDTH, IMG_HEIGHT);
		System.out.println("Front camera initialized properly");
		*/
		
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
		 
		//autonomousCommand = new AutonomousCommandCenter(); 
	}

	@Override
	public void teleopInit(){
		//drivetrain.disableControl();
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
		try{
			//ultraAuto.disable();
		}catch(Exception e){
			
		}
	    //autonomousCommand.cancel();
		//Scheduler.getInstance().disable();
	
	}
	
	@Override
	public void teleopPeriodic(){
		
		double left = drivestick.getRawAxis(Portmap.DRIVE_LEFT_JOYSTICK_ID);
		double right = -drivestick.getRawAxis(Portmap.DRIVE_RIGHT_JOYSTICK_ID);
		double strafe = drivestick.getRawAxis(Portmap.STRAFE_DRIVE_ID);
		boolean isStrafing = drivestick.getRawButton(Portmap.STRAFE_ID);

		//System.out.println("l: " + left + "  r: " + right + " strafe: " + isStrafing + + " - " + strafe);
		
		/* Removed to make sure the mode is fast
		Drivetrain.MotorGranular mode = Drivetrain.MotorGranular.NORMAL;
		if(drivestick.getRawButton(Portmap.DRIVE_LEFT_BOTTOM_TRIGGER_ID) 
				&& !drivestick.getRawButton(Portmap.DRIVE_LEFT_TOP_TRIGGER_ID)){
			 mode = Drivetrain.MotorGranular.FAST;
		}
		
		else if(!drivestick.getRawButton(Portmap.DRIVE_LEFT_BOTTOM_TRIGGER_ID) 
					&& drivestick.getRawButton(Portmap.DRIVE_LEFT_TOP_TRIGGER_ID)){
			mode = Drivetrain.MotorGranular.SLOW;
		}
		*/
		
		mode = Drivetrain.MotorGranular.FAST; // Permanently set the mode to Fast, with a coefficient of 1
		drivetrain.drive(left, right, strafe, isStrafing, mode);
		
		/*
		if(leftStick.getRawButton(Portmap.ARM_COMPRESS_BUTTON_ID)){
			arm.armCompress();
		}
		
		if(leftStick.getRawButton(Portmap.ARM_DECOMPRESS_BUTTON_ID)){
			arm.armDecompress();
		}
		if(!leftStick.getRawButton(Portmap.ARM_COMPRESS_BUTTON_ID)&&!leftStick.getRawButton(Portmap.ARM_DECOMPRESS_BUTTON_ID)){
			arm.armOff();
		}
		*/
		
	
		arm.armOff(); //Turns of the solenoids in the arm
		arm.setArm(0); //Sends no power to the arm
		//arm.setArm(leftStick.getRawAxis(Portmap.JOYSTICK_ARM_ID));
		if (drivestick.getRawButton(Portmap.JOYSTICK_WINCH_ID)){
			winch.set(1); //Set the winch to a button

		}
		else{
			winch.set(0);
		}
		
		System.out.println("mode: " + mode + "  l: " + left + "  r: " + right + " strafe: " + strafe);

	}
	
	@Override
	public void autonomousInit(){
		/*
		Scheduler.getInstance().enable();
		if (autonomousCommand != null){
			autonomousCommand.start();
		}
		*/
		drivetrain.resetEncoder();
		 //drivetrain.setAutoMode(AutoMode.Strafe);
		 //drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);	

		/*
		String[] ls = new String[] { "1", "1", "1", "1", "1"};
		logger.init(ls, ls);
		drivetrain.setVoltageRampRate(3);
		drivetrain.setPID(.25, 0, 0);
		drivetrain.setTalonControlMode(TalonControlMode.Position);
		//drivetrain.GotoPosition(1000);
		drivetrain.drive(10, 10, 0, false, MotorGranular.FAST);
		*/
		timer.start();
	}
	
	@Override
	public void autonomousPeriodic() {
		//Scheduler.getInstance().run();
		logger.writeData(drivetrain.getDistance());
	}
	
	@Override
	public void disabledInit(){
		System.out.print(logger.close());
		//Scheduler.getInstance().disable();
		//autonomousCommand.cancel();
	}
	
	@Override
	public void disabledPeriodic(){
	}
}
