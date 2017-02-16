package main.java.org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import main.java.org.usfirst.frc.team4215.robot.steamworks.VisionTest;

<<<<<<< Updated upstream
public class Robot<VisonTest> extends IterativeRobot {

	private VisionTest visionTest1;
=======
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
<<<<<<< Updated upstream
	    
=======
>>>>>>> Stashed changes
	
	}

	@Override
	public void autonomousInit() {
		drivetrain.setTalonControlMode(TalonControlMode.Position);
<<<<<<< Updated upstream
		drivetrain.resetEncoder();
		drivetrain.setPID(.05, 0, 0);
		drivetrain.enableControl();
		drivetrain.Go(24, 24, 24, 24);
=======
<<<<<<< Updated upstream
		drivetrain.setPID(.1, 0, 0);
=======

>>>>>>> Stashed changes
>>>>>>> Stashed changes
	}
	
>>>>>>> Stashed changes
	
	public void robotInit(){
	
		visionTest1 = new VisionTest();
			visionTest1.visionInit();
			
	}
	
	public void teleopInit(){		
		visionTest1.visionStart();
	}
	
	public void disableInit() throws InterruptedException{
			
			visionTest1.visionStop();
	}
}
