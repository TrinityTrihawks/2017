package main.java.org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
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

public class Robot<VisonTest> extends IterativeRobot {

	private VisionTest visionTest1;
	Joystick leftStick = new Joystick(0);
	Joystick rightStick = new Joystick(1);
	Drivetrain drivetrain;
	
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
	    drivetrain = Drivetrain.Create();

		visionTest1 = new VisionTest();
			visionTest1.visionInit();
			System.out.println("Hello World");
	}

	@Override
	public void autonomousInit() {
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
	@Override
	public void autonomousPeriodic() {
		dist = drivetrain.getDistance();
		System.out.printf("Dist: %f \n",dist[0]);
	}
	
	@Override
	public void teleopInit(){
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
		drivetrain.disableControl();
		visionTest1.visionStart();
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
	}

	
	public void disableInit() throws InterruptedException{
			
			visionTest1.visionStop();
	}
}
