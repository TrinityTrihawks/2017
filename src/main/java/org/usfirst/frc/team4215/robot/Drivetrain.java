package org.usfirst.frc.team4215.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;

public class Drivetrain {
	
	/*
	@Override
	protected void initDefaultCommand() {

	}
	
	protected void setDefaultCommand(){
		return;
	}
	
	
	public Command getCurrentCommand(){
		return super.getCurrentCommand();
	}
	*/
	
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
	
	final double coeffNormal = .666;
	final double coeffFast = 1;
	final double coeffSlow = .3;
	
	final double wheelRadius = 3; // inches
	final double wheelCirc = 2*Math.PI*wheelRadius;
	final double secondsToMinutes = 1.0/60; // seconds/minutes
	
	CANTalon.TalonControlMode controlMode;
	
	//21-24 declare talons
	CANTalon flWheel;
	CANTalon frWheel;
	CANTalon blWheel;
	CANTalon brWheel;
		
	//AnalogGyro gyro;
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
		flWheel = new CANTalon(Portmap.Talon_Front_Left_ID);
		frWheel = new CANTalon(Portmap.Talon_Front_Right_ID);
		blWheel = new CANTalon(Portmap.Talon_Back_Left_ID);
		brWheel = new CANTalon(Portmap.Talon_Back_Right_ID);
		
		flWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		frWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		blWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		brWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
		flWheel.setAllowableClosedLoopErr(0);
		frWheel.setAllowableClosedLoopErr(0);
		blWheel.setAllowableClosedLoopErr(0);
		brWheel.setAllowableClosedLoopErr(0);
		
		flWheel.setVoltageRampRate(3);
		frWheel.setVoltageRampRate(3);
		blWheel.setVoltageRampRate(3);
		brWheel.setVoltageRampRate(3);

		flWheel.reverseSensor(true);
		frWheel.reverseSensor(true);
		brWheel.reverseSensor(true);
		blWheel.reverseSensor(true);
		
		flWheel.setProfile(0);
		frWheel.setProfile(0);
		brWheel.setProfile(0);
		blWheel.setProfile(0);
		
		//gyro = new AnalogGyro(0);
		//gyro.calibrate();
		
		mode = AutoMode.Distance;
 
	}
	
	/*
	public void setBrakes(boolean brakesflag)
	{
		this.brakesFlag = brakesflag;
	}
	
	public boolean getBrakes()
	{
		return brakesFlag;
	}
	*/
	/*
	public double getAngle(){
		return gyro.getAngle();
	}
	
	public void calibrateGyro(){
		gyro.reset();
		gyro.calibrate();
	}
	
	public void setPID(double Kp, double Ki, double Kd){
		flWheel.setPID(Kp, Ki, Kd);
		frWheel.setPID(Kp, Ki, Kd);
		blWheel.setPID(Kp, Ki, Kd);
		brWheel.setPID(Kp, Ki, Kd);
	}
	*/
	public void resetEncoder(){		
		//int absolutePosition = flWheel.getPulseWidthPosition() & 0xFFF;
		int absolutePosition = 0;
		flWheel.setEncPosition(absolutePosition);
		//absolutePosition = frWheel.getPulseWidthPosition() & 0xFFF;
		frWheel.setEncPosition(absolutePosition);
		//absolutePosition = blWheel.getPulseWidthPosition() & 0xFFF;
		blWheel.setEncPosition(absolutePosition);
		//absolutePosition = brWheel.getPulseWidthPosition() & 0xFFF;
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
	
	public boolean isEnabled(){
		boolean flag = frWheel.isEnabled();
		flag &= flWheel.isEnabled();
		flag &= brWheel.isEnabled();
		flag &= blWheel.isEnabled();
		return flag;
	}
	
	/*
	public boolean isClosedLoopDone(int margin){
		System.out.println("Drivetrain brake: " + brakes.getMinDistance());
		/*
		]if (frWheel.getClosedLoopError()< margin){
			System.out.println("Drivetrain stopped.  closed loop error: " + frWheel.getClosedLoopError());
			return true;
		
		} else
		*/
		/* if(brakesFlag && brakes.getMinDistance() <= 1000){
			System.out.println("Drivetrain stopped.  brakes: " + brakes.getMinDistance());
			return true;
		}
		return false;
	}
	
	*/
	/*
	public void brakeMode(){
		flWheel.enableBrakeMode(true);
		frWheel.enableBrakeMode(true);
		brWheel.enableBrakeMode(true);
		blWheel.enableBrakeMode(true);
	}
	*/
	public void disableControl(){
		flWheel.disableControl();
		frWheel.disableControl();
		brWheel.disableControl();
		blWheel.disableControl();
	}
	
	public void hardStop(){
		this.setTalonControlMode(TalonControlMode.PercentVbus);
		this.Reset();
	}
	
	public double[] getDistance(){
		double[] dist = new double[4];
		dist[0] = frWheel.getEncPosition();
		dist[1] = brWheel.getEncPosition();
		dist[2] = flWheel.getEncPosition();
		dist[3] = blWheel.getEncPosition();
		
		return dist;
	}
	
	public double[] getVelocity(){
		double[] velocity = new double[4];
		velocity[0] = frWheel.getSpeed();
		velocity[1] = brWheel.getSpeed();
		velocity[2] = flWheel.getSpeed();
		velocity[3] = blWheel.getSpeed();
		return velocity;

	}
	
	public double[] getOutput(){
		double[] output = new double[4];
		output[0] = frWheel.get();
		output[1] = brWheel.get();
		output[2] = flWheel.get();
		output[3] = blWheel.get();
		return output;
		
	}
	public double[] getVoltages(){
		double[] volts = new double[4];
		volts[0] = frWheel.getBusVoltage();
		volts[1] = brWheel.getBusVoltage();
		volts[2] = flWheel.getBusVoltage();
		volts[3] = blWheel.getBusVoltage();
		return volts;
	}
	
	public double[] getTemperature(){
		double[] temp = new double[4];
		temp[0] = frWheel.getTemperature();
		temp[1] = brWheel.getTemperature();
		temp[2] = flWheel.getTemperature();
		temp[3] = blWheel.getTemperature();
		return temp;
	}
	public int[] getClosedLoopError(){
		int[] dist = new int[4];
		dist[0] = frWheel.getClosedLoopError();
		dist[1] = brWheel.getClosedLoopError();
		dist[2] = flWheel.getClosedLoopError();
		dist[3] = blWheel.getClosedLoopError();
		
		return dist;
	}
	
	public void setClosedLoopError(int margin){
		frWheel.setAllowableClosedLoopErr(margin);
		flWheel.setAllowableClosedLoopErr(margin);
		brWheel.setAllowableClosedLoopErr(margin);
		blWheel.setAllowableClosedLoopErr(margin);
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

		flWheel.set(-lFront);
		blWheel.set(-lBack);
		frWheel.set(rFront);
		brWheel.set(rBack);
		 
	}

	public void Reset() {
		Go(0,0,0,0);
	}

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
				right *= coeffSlow;
				strafe *= coeffSlow;
				break;
		}
		
		
		if (!IsStrafing){
			Go(left,left,right,right);
		}
		
		if (IsStrafing){
			Go(-strafe,strafe,strafe,-strafe);
		}

	}
	
	public void setAutoMode(AutoMode m){
		mode = m;
	}
	
	public AutoMode getAutoMode(){
		return mode;
	}
	
	
}
