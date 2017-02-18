package main.java.org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.DigitalInput;
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
	Joystick winchJoystick = new Joystick(0);
	WinchTest winch;
	
	public void robotInit(){
	arm =  new Arm();
	leftStick = new Joystick(1);
	 drivetrain = Drivetrain.Create();
	 winch = new WinchTest();
	}
	
	public void teleopInit(){		
		drivetrain.setTalonControlMode(TalonControlMode.PercentVbus);
		drivetrain.disableControl();
	}
	public void teleopPeriodic(){
		double left = drivestick.getRawAxis(1);
		double right = drivestick.getRawAxis(3);
		double strafe = drivestick.getRawAxis(2);
		boolean isStrafing = drivestick.getRawButton(1);
		
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
		double l = winchJoystick.getRawAxis(2);
		

		winch.set(l);
	}
	

	@Override
	public void autonomousInit() {
		drivetrain.setTalonControlMode(TalonControlMode.Position);
		drivetrain.resetEncoder();
		drivetrain.setPID(.05, 0, 0);
		drivetrain.enableControl();
		drivetrain.Go(24, 24, 24, 24);
	}
	
	
	double[] dist = new double[4];
	
	@Override
	public void autonomousPeriodic() {
		dist = drivetrain.getDistance();
		System.out.printf("Dist: %f \n",dist[0]);
	}
	
}
