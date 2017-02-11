package main.java.org.usfirst.frc.team4215.robot;

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
	
	/**
	 * This method gets the left encoder follower.
	 * @param leftTraj
	 * @param ENCODER_POSITION
	 * @param TICK_PER_ROTATION
	 * @param WHEEL_DIAMETER
	 * @param Kp
	 * @param Ki
	 * @param Kd
	 * @param MAX_VELOCITY
	 * @return left
	 */
	public EncoderFollower getLeftEncoderFollower(Trajectory leftTraj, int ENCODER_POSITION, int TICK_PER_ROTATION, double WHEEL_DIAMETER,
			double Kp, double Ki, double Kd, double MAX_VELOCITY){
		if (left == null){
			EncoderFollower left = new EncoderFollower(leftTraj);
			left.configureEncoder(ENCODER_POSITION, TICK_PER_ROTATION, WHEEL_DIAMETER);
			left.configurePIDVA(1.0, 0.0, 0.0, 1 / MAX_VELOCITY, 0);
			return left;
		} else
			return left;
		
	}
	
	/**
	 * This method gets the right encoder follower.
	 * @param rightTraj
	 * @param ENCODER_POSITION
	 * @param TICK_PER_ROTATION
	 * @param WHEEL_DIAMETER
	 * @param Kp
	 * @param Ki
	 * @param Kd
	 * @param MAX_VELOCITY
	 * @return right
	 */
	public EncoderFollower getRightEncoderFollower(Trajectory rightTraj, int ENCODER_POSITION, int TICK_PER_ROTATION, double WHEEL_DIAMETER,
			double Kp, double Ki, double Kd, double MAX_VELOCITY){
		if (right == null){
			EncoderFollower right = new EncoderFollower(rightTraj);
			right.configureEncoder(ENCODER_POSITION, TICK_PER_ROTATION, WHEEL_DIAMETER);
			right.configurePIDVA(1.0, 0.0, 0.0, 1 / MAX_VELOCITY, 0);
			return right;
		} else
			return right;
		
	}

}
