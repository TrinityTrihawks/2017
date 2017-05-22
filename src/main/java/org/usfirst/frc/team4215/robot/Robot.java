package org.usfirst.frc.team4215.robot;

import java.lang.reflect.Array;

// wpilib
import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Timer;

// ctre
import com.ctre.CANTalon.TalonControlMode;

// 4215
import org.usfirst.frc.team4215.robot.commandgroup.*;
import org.usfirst.frc.team4215.robot.Arm;
import org.usfirst.frc.team4215.robot.Drivetrain;
import org.usfirst.frc.team4215.robot.Drivetrain.AutoMode;
import org.usfirst.frc.team4215.robot.Drivetrain.MotorGranular;
import org.usfirst.frc.team4215.robot.Winch;
import org.usfirst.frc.team4215.robot.prototypes.PIDTask;
import org.usfirst.frc.team4215.robot.ultrasonic.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Arm arm;
	Joystick leftStick = new Joystick(Portmap.JOYSTICK_DRIVER_LEFT);
	Joystick drivestick = new Joystick(Portmap.JOYSTICK_DRIVER_RIGHT);

	Drivetrain drivetrain;
	Winch winch;
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
	
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	Timer timer = new Timer();
	
	final int IMG_WIDTH = 320;
	final int IMG_HEIGHT = 240;
	
	SimpleCsvLogger logger = new SimpleCsvLogger();

	AxisCamera cameraFront;
	AxisCamera cameraBack;
	
	@Override
	public void robotInit(){

		//arm =  new Arm();
		leftStick = new Joystick(0);
		drivetrain = Drivetrain.Create();
		winch = new Winch();

		//hub = UltrasonicStereo.Create();

		/*
		// FRONT CAMERA
		cameraFront = CameraServer.getInstance().addAxisCamera("Front", "10.42.15.39");
		cameraFront.setResolution(IMG_WIDTH, IMG_HEIGHT);
		System.out.println("Front camera initialized properly");
		// BACK CAMERA
		cameraBack = CameraServer.getInstance().addAxisCamera("Back", "10.42.15.37");
		cameraBack.setResolution(IMG_WIDTH, IMG_HEIGHT);
		System.out.println("Back camera initialized properly");
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
		
		double left = -drivestick.getRawAxis(Portmap.DRIVE_LEFT_JOYSTICK_ID);
		double right = -drivestick.getRawAxis(Portmap.DRIVE_RIGHT_JOYSTICK_ID);
		double strafe = drivestick.getRawAxis(Portmap.STRAFE_DRIVE_ID);
		boolean isStrafing = drivestick.getRawButton(Portmap.STRAFE_ID);
		
		Drivetrain.MotorGranular mode = Drivetrain.MotorGranular.NORMAL;
		
		// DRIVE
		if( drivestick.getRawButton(Portmap.DRIVE_LEFT_BOTTOM_TRIGGER_ID) && 
			!drivestick.getRawButton(Portmap.DRIVE_LEFT_TOP_TRIGGER_ID))
		{
			 mode = Drivetrain.MotorGranular.FAST;
		}		
		else if(!drivestick.getRawButton(Portmap.DRIVE_LEFT_BOTTOM_TRIGGER_ID) 
					&& drivestick.getRawButton(Portmap.DRIVE_LEFT_TOP_TRIGGER_ID)){
			mode = Drivetrain.MotorGranular.SLOW;
		}
		
		drivetrain.drive(left, right, strafe, isStrafing, mode);
		
		// ARM Claw
		if(leftStick.getRawButton(Portmap.ARM_COMPRESS_BUTTON_ID))
		{
			arm.ClawCompress();
		}
		else if(leftStick.getRawButton(Portmap.ARM_DECOMPRESS_BUTTON_ID))
		{
			arm.ClawDecompress();
		}
		else
		{
			arm.ClawOff();
		}

		// ARM position
		arm.setArm(leftStick.getRawAxis(Portmap.JOYSTICK_ARM_ID));
		
		// WINCH
		winch.set(leftStick.getRawAxis(Portmap.JOYSTICK_WINCH_ID));
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
		String[] ls = new String[] { "1", "1", "1", "1", "1"};
		logger.init(ls, ls);
		drivetrain.setPID(10, 0, 0);
		drivetrain.setTalonControlMode(TalonControlMode.Current);
		drivetrain.drive(84, -84, 0, false, MotorGranular.NORMAL);
		timer.start();
		
	}
	
	@Override
	public void autonomousPeriodic() {
		//Scheduler.getInstance().run();
		double[] getDistance = drivetrain.getDistance();
		double[] getVoltage = drivetrain.getVoltages();
		double[] getTemperature = drivetrain.getTemperature();
		double[] getVelocity = drivetrain.getVelocity();
		double[] getOutput = drivetrain.getOutput();
		
		double[] logs =  new double[25];
		
		logs[0] = timer.get();
		
		for (int i = 1;i < 5; i++){
			logs[i] = getDistance[i-1];
		}
		for (int i = 5;i < 9; i++){
			logs[i] = getVoltage[i-5];
		}
		logs[9] = pdp.getCurrent(Portmap.PDP_Bus_Channel_Front_Left);
		logs[10] = pdp.getCurrent(Portmap.PDP_Bus_Channel_Front_Right);
		logs[11] = pdp.getCurrent(Portmap.PDP_Bus_Channel_Back_Left);
		logs[12] = pdp.getCurrent(Portmap.PDP_Bus_Channel_Back_Right);
		
		for (int i = 13;i < 17; i++){
			logs[i] = getTemperature[i-13];
		} 
		
		for (int i = 17;i < 21; i++){
			logs[i] = getVelocity[i-17];
		}
		for (int i = 21;i < 25; i++){
			logs[i] = getOutput[i-21];
		}
		
		logger.writeData(logs);
		
		
	}
	
	@Override
	public void disabledInit(){
		System.out.print(logger.close());
		Scheduler.getInstance().disable();
		autonomousCommand.cancel();
	}
	
	@Override
	public void disabledPeriodic(){
	}
}
