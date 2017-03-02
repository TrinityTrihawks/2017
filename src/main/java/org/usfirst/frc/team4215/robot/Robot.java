package org.usfirst.frc.team4215.robot;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
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
import org.usfirst.frc.team4215.robot.CameraInit;
import org.usfirst.frc.team4215.robot.Drivetrain;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team4215.robot.WinchTest;
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
	Thread visionThread;
	CameraInit cam;
	UltrasonicHub hub;
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
	
	AxisCamera cameraBack = CameraServer.getInstance().addAxisCamera("Back", "10.42.15.37");
	//AxisCamera cameraFront = CameraServer.getInstance().addAxisCamera("Back", "10.42.15.39");
	private double centerX = 0.0;			//Creates the variable centerX. 
	
	private final Object imgLock = new Object();
	
	public void robotInit(){
		arm =  new Arm();
		leftStick = new Joystick(0);
		 drivetrain = Drivetrain.Create();
		 winch = new WinchTest();
		
			cameraBack = CameraServer.getInstance().addAxisCamera("Back", "10.42.15.37");
			cameraBack.setResolution(IMG_WIDTH, IMG_HEIGHT);
			visionThread = new VisionThread(cameraBack, new Pipeline(), pipeline -> {
	
				 if (!pipeline.filterContoursOutput().isEmpty()) {
			            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
			            synchronized (imgLock) {
			                centerX = r.x + (r.width / 2); 
			                //System.out.println(centerX); 	//if the code is actually working,
			                //System.out.println("Current Center X variable");          //a number should be displayed
			            }
			        }
			      else {
			    	  System.out.println("No Contours");
			      }
			    });
			visionThread.setDaemon(true);
			visionThread.start();
			
			System.out.println("Hello World");
			
			//double turnTest = centerX - (IMG_WIDTH/2);
			//System.out.println("Turn Test");
			//System.out.println(turnTest);
			//hub =  new UltrasonicHub();
			//ArrayList<String> devices;
			
			//hub.addReader("/dev/ttyUSB1");

	}
	
	public void teleopInit(){		
		//drivetrain.disableControl();
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
	}
	

	public void teleopPeriodic(){
		/*
		ArrayList<Integer> distances = hub.getDistancefromallPorts();
		for (int i=0; i<distances.size(); i++)
		{
			System.out.print("d: " + distances.get(i) + "\t");			
		}
		System.out.println("\n");
		*/
		
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
/**	public void autonomousPeriodic(){
		double centerX;
		synchronized (imgLock) {
			centerX = this.centerX;
		}
		double turn = centerX - (IMG_WIDTH / 2);
		
		
		double left = 0;
		double right = 0;
		double strafe = turn;
		boolean IsStrafing = true;
		Drivetrain.MotorGranular mode = Drivetrain.MotorGranular.SLOW;
		
		//drivetrain.drive(left, right, strafe, IsStrafing, mode);
	}
	**/
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
