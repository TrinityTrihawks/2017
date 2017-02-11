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
		
		CANTalon[] talonList = new CANTalon[]{
				flWheel, frWheel, blWheel, brWheel
		};
		
		
		

		private static Drivetrain instance;
		
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
		}
		
		/**
		 * Gets controlmode.
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
			
			
			frWheel.set(rFront);
			brWheel.set(rBack);
			flWheel.set(lFront);
			blWheel.set(lBack);
			
		}
		
		/**
		 * Gets the number of rotations from each wheel.
		 * @author Jack Rausch
		 * @param flWheel
		 * @param frWheel
		 * @param blWheel
		 * @param brWheel
		 * @return int[]
		 */
		public int[] getTicks(CANTalon flWheel, CANTalon frWheel, CANTalon blWheel, CANTalon brWheel){
			int[] Ticks = new int[]{
				flWheel.getEncPosition(),
				frWheel.getEncPosition(),
				blWheel.getEncPosition(),
				brWheel.getEncPosition()
			};
			return Ticks;
			
		}

		
		public void Reset() {
			Go(0,0,0,0);
		}
		
		public CANTalon getFlWheel() {
			return flWheel;
		}
		
		public CANTalon[] getTalonList() {
			return talonList;
		}

		public void setTalonList(CANTalon[] talonList) {
			this.talonList = talonList;
		}

		public void setFlWheel(CANTalon flWheel) {
			this.flWheel = flWheel;
		}

		public CANTalon getFrWheel() {
			return frWheel;
		}

		public void setFrWheel(CANTalon frWheel) {
			this.frWheel = frWheel;
		}

		public CANTalon getBlWheel() {
			return blWheel;
		}

		public void setBlWheel(CANTalon blWheel) {
			this.blWheel = blWheel;
		}

		public CANTalon getBrWheel() {
			return brWheel;
		}

		public void setBrWheel(CANTalon brWheel) {
			this.brWheel = brWheel;
		}
	}
	