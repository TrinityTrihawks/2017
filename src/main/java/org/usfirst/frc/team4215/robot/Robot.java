package main.java.org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import main.java.org.usfirst.frc.team4215.robot.steamworks.VisionTest;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.TankModifier;
import main.java.org.usfirst.frc.team4215.robot.Arm;
import main.java.org.usfirst.frc.team4215.robot.CameraInit;
import main.java.org.usfirst.frc.team4215.robot.Drivetrain;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import main.java.org.usfirst.frc.team4215.robot.WinchTest;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

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
	Thread visionThread;
	CameraInit cam;
	// ID's
	int DRIVE_LEFT_JOYSTICK_ID = 3;
	int DRIVE_RIGHT_JOYSTICK_ID = 1;
	int STRAFE_ID = 8;
	int WINCH_ID = 1;
	int ARM_ID = 4;
	int STRAFE_DRIVE_ID = 0;
	int DRIVE_LEFT_TOP_TRIGGER = 5;
	int DRIVE_LEFT_BOTTOM_TRIGGER = 7;
	
	
	public void robotInit(){
	arm =  new Arm();
	leftStick = new Joystick(0);
	 drivetrain = Drivetrain.Create();
	 winch = new WinchTest();
	 cam = new CameraInit();
	 visionThread = new Thread(cam);
		visionThread.setDaemon(true);
		visionThread.start();
		
		//visionTest1 = new VisionTest();
			//visionTest1.visionInit();
			System.out.println("Hello World");
	}
	
	public void teleopInit(){		
		//drivetrain.disableControl();
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
	}
	

	public void teleopPeriodic(){
		double left = -drivestick.getRawAxis(DRIVE_LEFT_JOYSTICK_ID);
		double right = -drivestick.getRawAxis(DRIVE_RIGHT_JOYSTICK_ID);
		double strafe = drivestick.getRawAxis(STRAFE_DRIVE_ID);
		boolean isStrafing = drivestick.getRawButton(STRAFE_ID);
		
		Drivetrain.MotorGranular mode = Drivetrain.MotorGranular.NORMAL;
		if(drivestick.getRawButton(DRIVE_LEFT_BOTTOM_TRIGGER) && !drivestick.getRawButton(DRIVE_LEFT_TOP_TRIGGER)){
			 mode = Drivetrain.MotorGranular.FAST;
		}
		else if(!drivestick.getRawButton(DRIVE_LEFT_BOTTOM_TRIGGER) && drivestick.getRawButton(DRIVE_LEFT_TOP_TRIGGER)){
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
		double l = leftStick.getRawAxis(4);
		
		winch.set(l);
	}
	/**public void autonomousInit() {
		drivetrain.setTalonControlMode(TalonControlMode.Position);
		drivetrain.resetEncoder();
		drivetrain.setPID(.05, 0, 0);
		drivetrain.enableControl();
		drivetrain.Go(24, 24, 24, 24);
		drivetrain.setPID(.1, 0, 0);
	}
	double[] dist = new double[4];
	**/
}
