package main.java.org.usfirst.frc.team4215.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class Pathfollower {
	
	private CANTalon.MotionProfileStatus _status = new CANTalon.MotionProfileStatus();
	private CANTalon.SetValueMotionProfile output = CANTalon.SetValueMotionProfile.Disable;
	
	/**
	 * Sets each Talon to Motion Profile mode and makes it follow the profile.
	 * @author Jack Rausch
	 * @param talonList
	 * @param output
	 */
	public void follow(CANTalon[] talonList, double output){
		for (int i = 0; i < talonList.length; i++){
			CANTalon _talon = talonList[i];
			_talon.changeControlMode(TalonControlMode.MotionProfile);
			_talon.set(output);
			
		}
	}
	
	/**
	 * Takes the points out of the point array and pushes them to the Motion Profile.
	 * @author Jack Rausch
	 * @param pointList
	 * @param side
	 */
	public void fillPoints(double[][] pointList, CANTalon[] side){
		for (int i = 0; i < pointList.length; i++){
			CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
			point.position = pointList[0][i];
			point.position = pointList[1][i];
			point.timeDurMs = 10;
			point.profileSlotSelect = 0;
			point.velocityOnly = false;
			
			point.zeroPos = false;
			if (i == 0)
				point.zeroPos = true;
			
			point.isLastPoint = false;
			if ((i + 1) == pointList.length)
				point.isLastPoint = true;
			
			for (int j = 0; j < side.length; j++){
				side[j].pushMotionProfileTrajectory(point);
			}
		}
=======
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class Pathfollower {
	
	private int ENCODER_POSITION;
	private int TICK_PER_ROTATION;
	private double WHEEL_DIAMETER;
	private EncoderFollower left;
	private EncoderFollower right;
	
	
	/**
	 * This method must be used at the beginning of Autonomous for the Pathfollower code to work. 
	 * @author Jack 
	 */
	public void pathfInit(){
		EncoderFollower left = null;
		EncoderFollower right = null;
		
	}
	
	public EncoderFollower getLeftEncoderFollower(Trajectory leftTraj, int ENCODER_POSITION,
			int TICK_PER_ROTATION, double WHEEL_DIAMETER, double Kp, double Ki, double Kd,
				double MAX_VELOCITY){
		if (left == null){
			EncoderFollower left = new EncoderFollower(leftTraj);
			left.configureEncoder(ENCODER_POSITION, TICK_PER_ROTATION, WHEEL_DIAMETER);
			left.configurePIDVA(1.0, 0.0, 0.0, 1 / MAX_VELOCITY, 0);
			return left;
		} else
			return left;
		
	}
	
	public EncoderFollower getRightEncoderFollower(Trajectory rightTraj, int ENCODER_POSITION,
			int TICK_PER_ROTATION, double WHEEL_DIAMETER, double Kp, double Ki, double Kd,
				double MAX_VELOCITY){
		if (right == null){
			EncoderFollower right = new EncoderFollower(rightTraj);
			right.configureEncoder(ENCODER_POSITION, TICK_PER_ROTATION, WHEEL_DIAMETER);
			right.configurePIDVA(1.0, 0.0, 0.0, 1 / MAX_VELOCITY, 0);
			return right;
		} else
			return right;
		
	}

}
