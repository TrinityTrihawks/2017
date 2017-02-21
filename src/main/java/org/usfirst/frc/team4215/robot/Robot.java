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
import edu.wpi.first.wpilibj.PowerDistributionPanel;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.MotionProfileStatus;
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
	private MotionProfileStatus status;
	Drivetrain drivetrain;
	SimpleCsvLogger logger;
	Pathmaker pathmaker;

	PowerDistributionPanel pdp;
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		pdp = new PowerDistributionPanel();
		time = new Timer();
	    drivetrain = Drivetrain.Create();
	    logger = new SimpleCsvLogger();
	    logger.init(new String[] {"FR","BR","FL","BR","Angle","Time"},new String[] {"In","In","In","In","Degrees","S"});
	    pathmaker = new Pathmaker();
	}
	

	@Override
	public void autonomousInit() {
		drivetrain.setTalonControlMode(TalonControlMode.Position);
		drivetrain.resetEncoder();
		drivetrain.setPID(1, 0, 0);
		drivetrain.enableControl();
		Trajectory.Config config = pathmaker.config(80, 100, 1500);
		Trajectory trajectory = pathmaker.getTrajectory(pathmaker.test, config);
		Trajectory[] trajList = pathmaker.getBothTrajectories(6, trajectory);
		double[][] leftPointList = pathmaker.convertTrajectory(trajList[0]);
		double[][] rightPointList = pathmaker.convertTrajectory(trajList[1]);
		time.start();
		
		if (status.isUnderrun != false){
			drivetrain.follow();
		} else {
			drivetrain.fillPoints(rightPointList, "right");
			drivetrain.fillPoints(leftPointList, "left");
		}
		
	}
	
	
	double[] dist = new double[4];
	double[] log = new double[7];
	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
	}
	
	@Override
	public void teleopInit(){
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
		logger.init(new String[] {"FR","BR","FL","BR","L","R","Time"},new String[] {"In","In","In","In","U","U","S"});
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("I is working");
		time.start();
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		double left = -.666*leftStick.getRawAxis(1);
		double right = -.666*leftStick.getRawAxis(3);
		double strafe = leftStick.getRawAxis(2);
		boolean isStrafing = leftStick.getRawButton(1);
		
		drivetrain.drive(left, right, strafe,isStrafing);
		dist = drivetrain.getVelocities();
		
		//Creates an array 
		for(int i = 0; i < 4; i++){
			log[i] = dist[i];
		}
		log[4] = left;
		log[5] = right;
		log[6] = time.get();
		
		logger.writeData(dist);
	}
	
	@Override
	public void disabledInit(){
		logger.close();
	}
}


