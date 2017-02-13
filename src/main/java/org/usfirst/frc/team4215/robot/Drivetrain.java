package main.java.org.usfirst.frc.team4215.robot;

import java.util.ArrayList;

import com.ctre.CANTalon;
	
	public class Drivetrain {
		
		double wheelRadius = 6; // inches
		double wheelCirc = 2*Math.PI*wheelRadius;
		double secondsToMinutes = (double) 1/60; // seconds/minutes
		
		CANTalon.TalonControlMode controlMode;
		
		//21-24 declare talons
		CANTalon flWheel;
		CANTalon frWheel;
		CANTalon blWheel;
		CANTalon brWheel;
		
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
			flWheel = new CANTalon(3);
			frWheel = new CANTalon(0);
			blWheel = new CANTalon(1);
			brWheel = new CANTalon(2);			
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
	
