package main.java.org.usfirst.frc.team4215.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.MotionProfileStatus;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogGyro;
//import jaci.pathfinder.Trajectory;
	
	public class Drivetrain {
		
		double wheelRadius = 3; // inches
		double wheelCirc = 2*Math.PI*wheelRadius;
		double secondsToMinutes = (double) 1/60; // seconds/minutes
		
		CANTalon.TalonControlMode controlMode;
		
		//21-24 declare talons
		CANTalon flWheel;
		CANTalon frWheel;
		CANTalon blWheel;
		CANTalon brWheel;
		
		CANTalon[] talonList;
		
		AnalogGyro gyro;
		
		//Declare Lists of wheels to be used for pathmaker trajectories

		private static Drivetrain instance;
		
		/**
		 * Creates an instance of Drivetrain
		 * @return
		 */
		public static Drivetrain Create() {
			if (instance == null) {
				instance = new Drivetrain();
			}
			return instance;
		}
		
 
		private Drivetrain() {
			//21-24 declare talons
			brWheel = new CANTalon(4);
			blWheel = new CANTalon(1);
			frWheel = new CANTalon(3);
			flWheel = new CANTalon(2);
			
			 gyro = new AnalogGyro(1);
			
			flWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
			blWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
			frWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
			brWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
			
			flWheel.setAllowableClosedLoopErr(0);
			frWheel.setAllowableClosedLoopErr(0);
			blWheel.setAllowableClosedLoopErr(0);
			brWheel.setAllowableClosedLoopErr(0);
			
			frWheel.reverseSensor(true);
			brWheel.reverseSensor(true);
			flWheel.reverseSensor(true);
			blWheel.reverseSensor(true);
			
			flWheel.setProfile(0);
			frWheel.setProfile(0);
			brWheel.setProfile(0);
			blWheel.setProfile(0);
			
			talonList = new CANTalon[]{
					flWheel,
					frWheel,
					blWheel,
					brWheel
			};
		}
		
		public void setPID(double Kp, double Ki, double Kd){
			flWheel.setPID(Kp, Ki, Kd);
			frWheel.setPID(Kp, Ki, Kd);
			blWheel.setPID(Kp, Ki, Kd);
			brWheel.setPID(Kp, Ki, Kd);
		}
		
		public void resetEncoder(){
			/*
			int absolutePosition = flWheel.getPulseWidthPosition() & 0xFFF;
			flWheel.setEncPosition(absolutePosition);
			absolutePosition = frWheel.getPulseWidthPosition() & 0xFFF;
			frWheel.setEncPosition(absolutePosition);
			absolutePosition = blWheel.getPulseWidthPosition() & 0xFFF;
			blWheel.setEncPosition(absolutePosition);
			absolutePosition = brWheel.getPulseWidthPosition() & 0xFFF;
			brWheel.setEncPosition(absolutePosition);
			*/
			flWheel.setEncPosition(0);
			frWheel.setEncPosition(0);
			blWheel.setEncPosition(0);
			brWheel.setEncPosition(0);
		}
		
		private String side;
		
		public void resetMotionProfile(){
			for (int i = 0; i < talonList.length; i++){
				CANTalon _talon = talonList[i];
				_talon.clearMotionProfileTrajectories();
				_talon.set(CANTalon.SetValueMotionProfile.Disable.value);
			}
		}
		int totalCount = 0;
		public void fillPoints(double[][] pointList, String side){
			
			for (int i = 0; i < talonList.length; i++){
				CANTalon _talon = talonList[i];
				_talon.clearMotionProfileTrajectories();
				_talon.set(TalonControlMode.Disabled.value);
			}
			for (int j = 0; j < 128 && totalCount < pointList.length; j++){
				CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
				point.position = pointList[0][j];
				point.position = pointList[1][j];
				point.timeDurMs = 10;
				point.profileSlotSelect = 0;
				point.velocityOnly = false;
				
				if (side == "left"){
					flWheel.pushMotionProfileTrajectory(point);
					blWheel.pushMotionProfileTrajectory(point);
				} else if (side == "right"){
					frWheel.pushMotionProfileTrajectory(point);
					brWheel.pushMotionProfileTrajectory(point);
				} else{
					System.err.println("Invalid String Name");
				}
				
				totalCount++;
				
			}
			
		}
		/**
		 *	Changes control modes of component talons
		 * @author Waweru and Carl(RIP) 
		 */
		public void setTalonControlMode(CANTalon.TalonControlMode newMode){
			controlMode = newMode;
			for (int i = 0; i < talonList.length; i++){
				CANTalon _talon = talonList[i];
				_talon.changeControlMode(controlMode);
			}
		}
		
		
		/**
		 * Gets control mode.
		 * @return TalonControlMode
		 */
		public CANTalon.TalonControlMode getTalonCOntrolMode(){
			return flWheel.getControlMode();
			
		}
		
		public void enableControl(){
			for (int i = 0; i < talonList.length; i++){
				CANTalon _talon = talonList[i];
				_talon.enableControl();
			}
		}
		
		public void disableControl(){
			for (int i = 0; i < talonList.length; i++){
				CANTalon _talon = talonList[i];
				_talon.disableControl();
			}
		}
		
		double[] dist = new double[4];
		public double[] getDistance(){
			for (int i = 0; i < talonList.length; i++){
				CANTalon _talon = talonList[i];
				dist[i] = _talon.getPosition();
			}
			
			return dist;
		}
		
		double[] speed = new double[4];
		public double[] getVelocities(){
			for (int i = 0; i < talonList.length; i++){
				CANTalon _talon = talonList[i];
				speed[i] = _talon.getSpeed();
			}
			
			return speed;
		}
		
		
		public double getAngle(){
			return gyro.getAngle();
		}
		
		public double getAngleSpeed(){
			return gyro.getRate();
		}
		
		double[] err = new double[4];
		public double[] getPosition(){
			for (int i = 0; i < talonList.length; i++){
				CANTalon _talon = talonList[i];
				err[i] = _talon.getPosition();
			}
			
			return err;
		}
		
		/**
		 * floor/ceiling for power and setting wheels
		 * @author Carl(RIP) and Will 
		 */
		public void Go(double lFront, double lBack,double rFront, double rBack){
			if(controlMode == CANTalon.TalonControlMode.Position){
				lFront = lFront/wheelCirc;
				lBack = lBack/wheelCirc;
				rFront = rFront/wheelCirc;
				rBack = rBack/wheelCirc;
			}
			if(controlMode == CANTalon.TalonControlMode.Speed){
				lFront = lFront*secondsToMinutes/wheelCirc;
				lBack = lBack*secondsToMinutes/wheelCirc;
				rFront = rFront*secondsToMinutes/wheelCirc;
				rBack = rBack*secondsToMinutes/wheelCirc;
			}
			
			flWheel.set(lFront);
			blWheel.set(lBack);
			frWheel.set(-rFront);
			brWheel.set(-rBack);
		}

		public void Reset() {
			Go(0,0,0,0);
		}
		
		public void follow(){
			
			/*
			for (int i = 0; i < talonList.length; i++){
				CANTalon _talon = talonList[i];
				_talon.changeControlMode(TalonControlMode.MotionProfile);
			}
			*/
			frWheel.reverseOutput(true);
			brWheel.reverseOutput(true);
			flWheel.set(CANTalon.SetValueMotionProfile.Enable.value);
			blWheel.set(CANTalon.SetValueMotionProfile.Enable.value);
			frWheel.set(CANTalon.SetValueMotionProfile.Enable.value);
			brWheel.set(CANTalon.SetValueMotionProfile.Enable.value);
		
		
		
		}
		
		public MotionProfileStatus[] getStatus(){
			MotionProfileStatus status_tmp0 = new MotionProfileStatus();
			MotionProfileStatus status_tmp1 = new MotionProfileStatus();
			MotionProfileStatus status_tmp2 = new MotionProfileStatus();
			MotionProfileStatus status_tmp3 = new MotionProfileStatus();
			flWheel.getMotionProfileStatus(status_tmp0);
			frWheel.getMotionProfileStatus(status_tmp1);
			brWheel.getMotionProfileStatus(status_tmp2);
			blWheel.getMotionProfileStatus(status_tmp3);
			MotionProfileStatus[] stat = new MotionProfileStatus[] {
					status_tmp0,
					status_tmp1,
					status_tmp2,
					status_tmp3
						
			};
			return stat;
		}
		
		public void drive(double left, double right, double strafe, boolean IsStrafing){
			if (!IsStrafing){
				Go(left,left,right,right);
			}

			
			
			if (IsStrafing){
			Go(strafe,-strafe,-strafe,strafe);
			}
	
	}
}