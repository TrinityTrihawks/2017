package main.java.org.usfirst.frc.team4215.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;
	
	public class Wwheels {
		
			private static Wwheels instance;
			public static Wwheels Create() {
				if (instance == null) {
					instance = new Wwheels();
				}
				return instance;
			}
			private Wwheels() {
				//21-24 declare talons
				flWheel = new CANTalon(3);
				frWheel = new CANTalon(0);
				blWheel = new CANTalon(1);
				brWheel = new CANTalon(2);			
			}
			
		//21-24 declare talons
		CANTalon flWheel;
		CANTalon frWheel;
		CANTalon blWheel;
		CANTalon brWheel;
		
		
		
		/**
		 * @Author Carl and Will
		 * @Purpose floor/ceiling for power and setting wheels
		 */
		public void Go(double lFront, double lBack,double rFront, double rBack){
			if (lFront > 1) {
				lFront = 1;
			} else if (lFront < -1) {
				lFront = -1;
			} 
			
			if (lBack > 1) {
				lBack = 1;
			} else if (lBack < -1) {
				lBack = -1;
			} 
			
			if (rFront > 1) {
				rFront = 1;
			} else if (rFront < -1) {
				rFront = -1;
			}
			
			if (rBack > 1) {
				rBack = 1;
			} else if (rBack < -1) {
				rBack = -1;
			}
			
			frWheel.set(rFront);
			brWheel.set(rBack);
			flWheel.set(lFront);
			blWheel.set(lBack);
		}
		
		public void Reset() {
			Go(0,0,0,0);
		}
	}
	