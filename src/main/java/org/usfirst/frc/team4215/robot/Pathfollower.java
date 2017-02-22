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
	
	public Pathfollower(){
		this.output = CANTalon.SetValueMotionProfile.Disable;
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

            point.zeroPos = i == 0;

            point.isLastPoint = (i + 1) == pointList.length;
			
			for (int j = 0; j < side.length; j++){
				side[j].pushMotionProfileTrajectory(point);
			}
		}

}
}
