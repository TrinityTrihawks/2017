package org.usfirst.frc.team4215.robot;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import java.util.ArrayList;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team4215.robot.steamworks.VisionTest;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.TankModifier;
import org.usfirst.frc.team4215.robot.Arm;
import org.usfirst.frc.team4215.robot.Drivetrain;
import org.usfirst.frc.team4215.robot.Drivetrain.AutoMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.PIDController;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team4215.robot.WinchTest;
import org.usfirst.frc.team4215.robot.prototypes.PIDTask;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import prototypes.UltrasonicHub;
import com.ctre.CANTalon;

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
	CameraPID vision;
	UltrasonicHub hub;
	PIDTask camAuto;
	PIDTask ultraAuto;
	VisionThread visionThread;
	AnalogGyro gyro;
	PIDController con;
	
	double Kp = .01;
	double Ki = .1;
	double Kd = 0;
	
	// ID's
	int DRIVE_LEFT_JOYSTICK_ID = 3;
	int DRIVE_RIGHT_JOYSTICK_ID = 1;
	int STRAFE_ID = 8;
	int WINCH_ID = 1;
	int ARM_ID = 4;
	int STRAFE_DRIVE_ID = 0;
	int DRIVE_LEFT_TOP_TRIGGER = 5;
	int DRIVE_LEFT_BOTTOM_TRIGGER = 7;
	int IMG_WIDTH = 640;
	int IMG_HEIGHT = 480;
	
	AxisCamera cameraFront;
	AxisCamera cameraBack;
	
	@Override
	public void robotInit(){
		arm =  new Arm();
		leftStick = new Joystick(0);
		 drivetrain = Drivetrain.Create();
		 winch = new WinchTest();
		 hub = new UltrasonicHub();
		 hub.addReader("/dev/ttyUSB0");
		 hub.addReader("/dev/ttyUSB1");

		 gyro = new AnalogGyro(0);
		 gyro.calibrate();
		 
		 cameraBack = CameraServer.getInstance().addAxisCamera("Back", "10.42.15.37");
		 cameraBack.setResolution(IMG_WIDTH, IMG_HEIGHT);
		 System.out.println("Back camera initialized properly");
		 // Creates the interface to the back camera

		 
		 //Pipeline pipeline = new Pipeline();
		 			 
			 cameraFront = CameraServer.getInstance().addAxisCamera("Front", "10.42.15.39");
			 cameraFront.setResolution(IMG_WIDTH, IMG_HEIGHT);
			 System.out.println("Front camera initialized properly");
			 /*
			 Mat mat = new Mat();

			 CvSink cvSink = CameraServer.getInstance().getVideo();
			 CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 640, 480);
			 cvSink.grabFrame(mat);
			 System.out.println("CvSink and CvSouce initialized properly");
			 System.out.println(mat);
			 
			 //CameraServer.getInstance().addServer();
*/
			 //CameraServer.getInstance().startAutomaticCapture(cameraFront);
			 //CvSink cvSink = CameraServer.getInstance().getVideo(cameraFront);
			 

			 System.out.println("CameraServer initialized properly");

			 vision = new CameraPID();
		     visionThread = new VisionThread(cameraFront, new Pipeline(), vision);
		     System.out.println("VisonThread initialized properly");
		     
		     visionThread.setDaemon(false);
		     System.out.println("Daemon set properly");
		     
			 visionThread.start();
			 System.out.println("VisonThread started without a hitch");
			 
			 //camAuto = new PIDTask(vision,drivetrain,Kp,Ki,Kd,0,0);
			 //System.out.println("PIDTask is working properly. Expect results");
			 con = new PIDController(Kp, Ki, Kd, vision, drivetrain);
			 
		 drivetrain.setAutoMode(AutoMode.Strafe);
		 drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
		 
	}
	@Override
	public void teleopInit(){		
		//drivetrain.disableControl();
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
		try{
			ultraAuto.disable();
		}catch(Exception e){
			
		}
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
		//camAuto.run();
		con.enable();
	}
	
	@Override
	public void autonomousPeriodic(){
		
		double sang = gyro.getAngle();
		System.out.println(sang);
		double cang = hub.getCorrectionAngle();
		System.out.println(cang);
		drivetrain.setAutoMode(AutoMode.Turn);
		double nang = 0;
		while (nang != sang - cang){
			drivetrain.pidWrite(.1);
			nang = gyro.getAngle();
			System.out.println(nang);
		}
		System.out.println(con.getAvgError());
		
		
	}
	
	@Override
	public void disabledInit(){
		con.disable();
	}
	@Override
	public void disabledPeriodic(){
	}
}
