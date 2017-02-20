package main.java.org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.TankModifier;
import main.java.org.usfirst.frc.team4215.robot.Drivetrain;
import main.java.org.usfirst.frc.team4215.robot.SimpleCsvLogger;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Timer time;
	Joystick leftStick = new Joystick(0);
	Joystick rightStick = new Joystick(1);
	Drivetrain drivetrain;
	SimpleCsvLogger logger;
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		time = new Timer();
	    drivetrain = Drivetrain.Create();
	    logger = new SimpleCsvLogger();
	    logger.init(new String[] {"FR","BR","FL","BR","Angle","Time"},new String[] {"In","In","In","In","Degrees","S"});
	}

	@Override
	public void autonomousInit() {
		drivetrain.setTalonControlMode(TalonControlMode.Position);
		drivetrain.resetEncoder();
		drivetrain.setPID(1, 0, 0);
		drivetrain.enableControl();
		time.start();
		
	}
	
	
	double[] dist = new double[4];
	double[] log = new double[6];
	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		drivetrain.Go(24, 24, 24, 24);
		
		// Gets distance
		dist = drivetrain.getDistance();
		
		//Creates an array 
		for(int i = 0; i < 4; i++){
			log[i] = dist[i];
		}
		log[4] = drivetrain.getAngle();
		log[5] = time.get();
		
		//  Logs data
		logger.writeData(log);
		
		// Prints the positions
		System.out.printf("Dist: %f, %f, %f, %f \n",dist[0],dist[1],dist[2],dist[3]);
	}
	
	@Override
	public void teleopInit(){
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
		drivetrain.disableControl();
		
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		double left = leftStick.getRawAxis(1);
		double right = leftStick.getRawAxis(3);
		double strafe = leftStick.getRawAxis(2);
		boolean isStrafing = leftStick.getRawButton(1);
		
		drivetrain.drive(left, right, strafe,isStrafing);
		dist = drivetrain.getDistance();
		System.out.printf("Dist: %f \n",dist[0]);
	}
	
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}


