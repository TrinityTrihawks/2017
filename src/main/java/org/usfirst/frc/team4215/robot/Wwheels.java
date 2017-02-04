package org.usfirst.frc.team4215.robot;


	import edu.wpi.first.wpilibj.Talon;
	

	public class Wwheels {
		
			private static Wwheels instance;
			public static Wwheels Create() {
				if (instance == null) {
					instance = new Wwheels();
				}
				return instance;
			}
			private Wwheels() {
				
			}
		
	
		//21-24 declare talons
		CANTalon flWheel = new CANTalon(3);
		CANTalon frWheel = new CANTalon(0);
		CANTalon blWheel = new CANTalon(1);
		CANTalon brWheel = new CANTalon(2);
		
		
		
		public void SetLeftWheels(double leftWheelPower) {
		//91-94 sets min/max left wheel power to -1 and 1
				if (leftWheelPower > 1) {
					leftWheelPower = 1;
				} else if (leftWheelPower < -1) {
					leftWheelPower = -1;
					flWheel.setPosition(3);
				}
		//28-31 sets wheels to left joystick position
		flWheel.set(leftWheelPower);
		blWheel.set(leftWheelPower);
		}
		
		public void SetSideWheels (double SideWheelPower) {
			if (SideWheelPower > 1) {
				SideWheelPower = 1;
			} else if (SideWheelPower < -1) {
				SideWheelPower = -1;
			}
			flWheel.set(SideWheelPower);
			brWheel.set(SideWheelPower);
			SideWheelPower *= -1;
			frWheel.set(SideWheelPower);
			blWheel.set(SideWheelPower);
			
		}
		public void SetRightWheels(double rightWheelPower) {
		//91-94 sets min/max wheel power to -1 and 1
				if (rightWheelPower > 1) {
					rightWheelPower = -1;
				} else if (rightWheelPower < -1) {
					rightWheelPower = 1;
				}
				
		frWheel.set(rightWheelPower);
		brWheel.set(rightWheelPower);
	}
		
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
	

