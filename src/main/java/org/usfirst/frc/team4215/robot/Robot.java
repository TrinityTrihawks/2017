package main.java.org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.TankModifier;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	//declare joysticks
	Joystick leftDriveJoystick = new Joystick(0);
	Joystick rightDriveJoystick = new Joystick(1);
	SendableChooser<String> chooser = new SendableChooser<>();
	Drivetrain drivetrain;
	
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		//creating grouping for left/right wheels
	    drivetrain = Drivetrain.Create();
	    Trajectory.Config configuration = null;
		Trajectory trajectory = null;
		TankModifier modifier = null;
	
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		
		//setting methods to functions in Wwheels
/*		
 leftWheel.SetLeftWheels(5);
	
		rightWheel.SetRightWheels(7);
		SideWheel.SSideWheels(8);
		*/
		System.out.println("Auto selected: " + autoSelected);
		/*
		Pathmaker pathmaker = new Pathmaker();
		config = pathmaker.config(dt, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
		trajectory = pathmaker.getTrajectory(auto, config);
		modifier = pathmaker.getModifier(wheelbasewidth, trajectory);
		leftTrajectory = pathmaker.getLeftTrajectory(modifier);
		rightTrajectory = pathmaker.getRightTrajectory(modifier);
		leftTrajectoryConverted = pathmaker.convertLeftTrajectory(leftTraj);
		rightTrajectoryConverted = pathmaker.convertRightTrajectory(rightTraj);
		*//*
		Pathfollower pathfollower = new Pathfollower();
		left = drivetrain.leftWheels;
		right = drivetrain.rightWheels;
		leftPath = pathfollower.fillPoints(pointList, left);
		rightPath = pathfollower.fillPoints(pointList, right);
		*/
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
	}


	
	
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}


