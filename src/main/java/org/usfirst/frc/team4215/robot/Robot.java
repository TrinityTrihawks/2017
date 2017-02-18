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
	int DRIVE_LEFT_JOYSTICK_ID = 3;
	int DRIVE_RIGHT_JOYSTICK_ID = 1;
	int STRAFE_ID = 7;
	int WINCH_ID = 1;
	int ARM_ID = 4;
	int STRAFE_DRIVE_ID = 0;
	Thread visionThread;
	
	public void robotInit(){
	arm =  new Arm();
	leftStick = new Joystick(0);
	 drivetrain = Drivetrain.Create();
	 winch = new WinchTest();
	 visionThread = new Thread(() -> {
			// Get the Axis camera from CameraServer
			AxisCamera camera = CameraServer.getInstance().addAxisCamera("Front", "10.42.15.39");
			// Set the resolution
			camera.setResolution(640, 480);
			
			
			AxisCamera cameraBack = CameraServer.getInstance().addAxisCamera("Back", "10.42.15.37");
			// Set the resolution
			cameraBack.setResolution(640, 480);
		
			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink = CameraServer.getInstance().getVideo();
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 640, 480);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.
				if (cvSink.grabFrame(mat) == 0) {
					// Send the output the error.
					outputStream.notifyError(cvSink.getError());
					// skip the rest of the current iteration
					continue;
				}
				// Put a rectangle on the image
				Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
						new Scalar(255, 255, 255), 5);
				// Give the output stream a new image to display
				outputStream.putFrame(mat);
			}
		});
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
		double coeff = .66;
		double left = -coeff*drivestick.getRawAxis(DRIVE_LEFT_JOYSTICK_ID);
		double right = -coeff*drivestick.getRawAxis(DRIVE_RIGHT_JOYSTICK_ID);
		double strafe = -coeff*drivestick.getRawAxis(STRAFE_DRIVE_ID);
		
		boolean isStrafing = drivestick.getRawButton(STRAFE_ID);
		
		drivetrain.drive(left, right, strafe,isStrafing);
		
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
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	/**public void autonomousInit() {
		drivetrain.setTalonControlMode(TalonControlMode.Position);
		drivetrain.resetEncoder();
		drivetrain.setPID(.05, 0, 0);
		drivetrain.enableControl();
		drivetrain.Go(24, 24, 24, 24);
		drivetrain.setPID(.1, 0, 0);
	}
	double[] dist = new double[4];
	/**
	 * This function is called periodically during autonomous
	 **/
	
	
	
	/**
	 * This function is called periodically during operator control
	 */
	
}}
