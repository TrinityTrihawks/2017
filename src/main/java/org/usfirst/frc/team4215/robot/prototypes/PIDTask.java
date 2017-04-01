package org.usfirst.frc.team4215.robot.prototypes;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDController;

/**
 * Made to allow easy creation of a PIDTask
 * @author waweros
 *
 */
public class PIDTask implements Runnable {
	PIDController control;
	double margin;
	private int smearCount = 10;
	boolean debug = false;
	
	
	public PIDTask(PIDSource sensor, PIDOutput motor, 
			double Kp, double Ki, double Kd,double setpoint,double margin, int smear) {
		
		
		PIDController control = new PIDController(Kp,Ki,Kd,sensor,motor);
		control.setSetpoint(setpoint);
		this.control = control;
		this.margin = margin;
		smearCount = smear;
		
	}
	
	public void setDebug(boolean debug){
		this.debug = debug;
	}
	
	/**
	 * Makes a PIDTask
	 * You need Kp, Ki, Kd
	 * 
	 * @param sensor
	 * @param motor
	 * @param Kp
	 * @param Ki
	 * @param Kd
	 * @param margin
	 */
	public PIDTask(PIDSource sensor, PIDOutput motor, 
					double Kp, double Ki, double Kd,double setpoint,double margin) {
		this(sensor,motor,Kp,Ki,Kd,setpoint,margin,10);
	}
	
	
	/**
	 * Starts and monitors PIDCotroller
	 */
	@Override
	public void run() {
		
		//Sets up error value to watch
		double error = control.getAvgError();;
		
		//enables controller
		control.enable();
		int count = 0;
		// Waits till the error is small
		while(count < 10){
			
			if (debug)
				System.out.println(control.getAvgError());
				
			if(error < margin){
				error = control.getAvgError();
				++count;
			}else{
				count = 0;
			}
			
		}
		
		// Disables the controller
		control.disable();
		System.out.println("run completed " + error);
		
	}
	
	public void disable(){
		control.disable();
	}
	
	public double getError(){
		return control.getAvgError();
	}
	
	public void setSmearCount(int count){
		smearCount = count;
	}
	
	public int getSmearCount(){
		return smearCount;
	}
}
