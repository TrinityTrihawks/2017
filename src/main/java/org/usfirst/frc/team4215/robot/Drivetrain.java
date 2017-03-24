package org.usfirst.frc.team4215.robot;

//imports things needed for talons
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.MotionProfileStatus;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogGyro;
//import jaci.pathfinder.Trajectory;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
	
	public class Drivetrain implements PIDOutput {
		public enum MotorGranular{
			FAST,
			NORMAL,
			SLOW
		}
		
		public enum AutoMode {
			Turn,
			Strafe,
			Distance
		}
		
		AutoMode mode;
		
		double coeffNormal = .666;
		double coeffFast = 1;
		double coeffSlow = .3;
		
		double wheelRadius = 3; // inches
		double wheelCirc = 2*Math.PI*wheelRadius; //wheel circumference
		double secondsToMinutes = (double) 1/60; // seconds/minutes
		
		CANTalon.TalonControlMode controlMode;
		
		//declares wheel talons vars
		CANTalon flWheel;
		CANTalon frWheel;
		CANTalon blWheel;
		CANTalon brWheel;
		

		
		//Declare Lists of wheels to be used for pathmaker trajectories
		CANTalon[] talonList;
		
		AnalogGyro gyro;
		

		
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
			//instantiates wheel objects with port numbers
			brWheel = new CANTalon(4);
			blWheel = new CANTalon(1);
			frWheel = new CANTalon(3);
			flWheel = new CANTalon(2);
			
			
			//instantiates gyro object with port number
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
			mode = AutoMode.Distance;
			//list of all the wheel objects
			talonList = new CANTalon[]{
					flWheel,
					frWheel,
					blWheel,
					brWheel
			};
		}
			
			
		
		/**
		 * Sets the PIDs for the wheels
		 * @param double Kp
		 * @param double Ki
		 * @param double Kd
		 */
		public void setPID(double Kp, double Ki, double Kd){
			flWheel.setPID(Kp, Ki, Kd);
			frWheel.setPID(Kp, Ki, Kd);
			blWheel.setPID(Kp, Ki, Kd);
			brWheel.setPID(Kp, Ki, Kd);
		}
		
		/**
		 * Sets wheel encoders to 0
		 */
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
		
		
		

		
		
		
		int totalCount = 0;
		private String side;
		
		/**
		 * 
		 * @param double[][] pointList
		 * @param String side
		 */
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
		/**
		 * Gets distance for each wheel
		 * @return double[] dist
		 */
		public double[] getDistance(){
			for (int i = 0; i < talonList.length; i++){
				CANTalon _talon = talonList[i];
				dist[i] = _talon.getPosition();
			}
			
			return dist;
		}
		
		
		double[] speed = new double[4];
		/**
		 * Get velocity for wheels
		 * @return double[] speed
		 */
		public double[] getVelocities(){
			for (int i = 0; i < talonList.length; i++){
				CANTalon _talon = talonList[i];
				speed[i] = _talon.getSpeed();
			}
			
			return speed;
		}
		
		
		/**
		 * Get angle of gyro
		 */
		public double getAngle(){
			return gyro.getAngle();
		}
		
		
		/**
		 * Get rate of gyro
		 * @return
		 */
		public double getAngleSpeed(){
			return gyro.getRate();
		}
		
		
		double[] err = new double[4];
		/**
		 * Get position of wheels
		 */
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
			else if(controlMode == CANTalon.TalonControlMode.Speed){
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

		/**
		 * Wheels stop moving
		 */
		public void Reset() {
			Go(0,0,0,0);
		}
		
		CTREMotionProfiler[] profileList = new CTREMotionProfiler[4];
		public void createProfiles(){
			for (int i = 0; i < talonList.length; i++){	
				CANTalon _talon = talonList[i];
				CTREMotionProfiler profile = new CTREMotionProfiler(_talon);
				profileList[i] = profile;
			}
		}
			
		public void startMotionProfiles(){
			for (int i = 0; i < talonList.length; i++){
				CANTalon _talon = talonList[i];
				CTREMotionProfiler profile = profileList[i];
				_talon.changeControlMode(TalonControlMode.MotionProfile);
				profile.startMotionProfile();
			}
		}
		
		/*
		public void mpTest(){
			if(profileList[1] == null){
				for (int i = 0; i < talonList.length; i++){	
					CANTalon _talon = talonList[i];
					CTREMotionProfiler profile = new CTREMotionProfiler(_talon);
			
					_talon.changeControlMode(TalonControlMode.MotionProfile);
			
					CANTalon.SetValueMotionProfile setOutput = profile.getSetValue();		
					_talon.set(setOutput.value);
			
					profile.startMotionProfile();
					profile.control();
				}
			}
			else{
				for(int i = 0; i < talonList.length; i++){
					profileList[i].control();
					CANTalon.SetValueMotionProfile setOutput = profileList[i].getSetValue();		
					talonList[i].set(setOutput.value);
				}
		}
		*/
		
		public void runMotionProfile(){
			for(int i = 0; i < talonList.length; i++){
				profileList[i].control();
				CANTalon.SetValueMotionProfile setOutput = profileList[i].getSetValue();		
				talonList[i].set(setOutput.value);
			}
		}
		
		public void resetMotionProfile(){
			if(profileList[0] == null){
				for(int i = 0; i < 0; i++){
					profileList[i].reset();
				}
			}
		}
		
		
		
		
		
		/**
		 * Set left and right wheels, accounting for strafing
		 * 
		 * @param double left
		 * @param double right
		 * @param double strafe
		 * @param boolean IsStrafing
		 */
		public void drive(double left, double right, double strafe, boolean IsStrafing
							, MotorGranular m){
			switch(m){
				case FAST:
					left *= coeffFast;
					right *= coeffFast;
					strafe *= coeffFast;
					break;
				case NORMAL:
					left *= coeffNormal;
					right *= coeffNormal;
					strafe *= coeffNormal;
					break;
				case SLOW:
					left *= coeffSlow;
					right *= coeffNormal;
					break;
			}
			
			
			if (!IsStrafing){
				Go(left,left,right,right);
			}
			

			if (IsStrafing){
			Go(strafe,-strafe,-strafe,strafe);
			}
		}
		
		public void setAutoMode(AutoMode m){
			mode = m;
		}
		
		public AutoMode getAutoMode(AutoMode m){
			return mode;
		}
		
		@Override
		public void pidWrite(double output) {
			
			switch(mode){
			
				case Distance:
					drive(output,output, 0, false, MotorGranular.FAST);
					break;
				case Strafe:
					drive(0,0, -output, true, MotorGranular.FAST);
					break;
				case Turn:
					drive(output,-output, 0, false, MotorGranular.FAST);
					break;
			}
		}
}
	
	
