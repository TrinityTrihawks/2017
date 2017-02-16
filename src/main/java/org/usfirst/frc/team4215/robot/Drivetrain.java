package main.java.org.usfirst.frc.team4215.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
	
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
		
		//Declare Lists of wheels to be used for pathmaker trajectories
		CANTalon[] wheelList = new CANTalon[]{
				flWheel, frWheel, blWheel, brWheel
		};
		
		CANTalon[] leftWheels = new CANTalon[]{
				flWheel, blWheel
		};
		
		CANTalon[] rightWheels = new CANTalon[]{
				frWheel, brWheel
		};
		
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
			flWheel = new CANTalon(4);
			frWheel = new CANTalon(1);
			blWheel = new CANTalon(3);
			brWheel = new CANTalon(2);
			
			flWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
			frWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
			blWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
			brWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
			
			flWheel.setAllowableClosedLoopErr(0);
			frWheel.setAllowableClosedLoopErr(0);
			blWheel.setAllowableClosedLoopErr(0);
			brWheel.setAllowableClosedLoopErr(0);
			
			
			flWheel.setProfile(0);
			frWheel.setProfile(0);
			brWheel.setProfile(0);
			blWheel.setProfile(0);
			
		}
		
		public void setPID(double Kp, double Ki, double Kd){
			flWheel.setPID(Kp, Ki, Kd);
			frWheel.setPID(Kp, Ki, Kd);
			blWheel.setPID(Kp, Ki, Kd);
			brWheel.setPID(Kp, Ki, Kd);
		}
		
		public void resetEncoder(){
			int absolutePosition = flWheel.getPulseWidthPosition() & 0xFFF;
			flWheel.setEncPosition(absolutePosition);
			absolutePosition = frWheel.getPulseWidthPosition() & 0xFFF;
			frWheel.setEncPosition(absolutePosition);
			absolutePosition = blWheel.getPulseWidthPosition() & 0xFFF;
			blWheel.setEncPosition(absolutePosition);
			absolutePosition = brWheel.getPulseWidthPosition() & 0xFFF;
			brWheel.setEncPosition(absolutePosition);
		}
		
		/**
		 *	Changes control modes of component talons
		 * @author Waweru and Carl(RIP) 
		 */
		public void setTalonControlMode(CANTalon.TalonControlMode newMode){
			controlMode = newMode;

			flWheel.changeControlMode(controlMode);
			frWheel.changeControlMode(controlMode);
			blWheel.changeControlMode(controlMode);
			brWheel.changeControlMode(controlMode);
			
		}
		
		/**
		 * Gets control mode.
		 * @return TalonControlMode
		 */
		public CANTalon.TalonControlMode getTalonCOntrolMode(){
			return flWheel.getControlMode();
			
		}
		
		public void enableControl(){
			flWheel.enableControl();
			frWheel.enableControl();
			brWheel.enableControl();
			blWheel.enableControl();
		}
		
		public void disableControl(){
			flWheel.disableControl();
			frWheel.disableControl();
			brWheel.disableControl();
			blWheel.disableControl();
		}
		
		double[] dist = new double[4];
		public double[] getDistance(){
			
			dist[0] = frWheel.getPosition();
			dist[1] = brWheel.getPosition();
			dist[2] = blWheel.getPosition();
			dist[3] = brWheel.getPosition();
			
			return dist;
		}
		
		double[] err = new double[4];
		public double[] getPosition(){
			err[0] = frWheel.getClosedLoopError();
			err[1] = brWheel.getClosedLoopError();
			err[2] = blWheel.getClosedLoopError();
			err[3] = brWheel.getClosedLoopError();
			
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
			
			flWheel.set(-lFront);
			blWheel.set(-lBack);
			frWheel.set(rFront);
			brWheel.set(rBack);
		}

		public void Reset() {
			Go(0,0,0,0);
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
	
