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
		PIDController control = new PIDController(Kp,Ki,Kd,sensor,motor);
		control.setSetpoint(setpoint);
		this.control = control;
		this.margin = margin;
	}
	/**
	 * Starts and monitors PIDCotroller
	 */
	@Override
	public void run() {
		
		//Sets up error value to watch
		double error = control.getAvgError();
		
		
		//enables controller
		control.enable();
		int count = 0;
		// Waits till the error is small
		while(count < 10){
			
			if(!control.isEnabled()){
				break;
			}
			if(error > margin){
				error = control.getAvgError();
				++count;
			}
		}
		
		// Disables the controller
		control.disable();
		
	}
	
	public void disable(){
		control.disable();
	}
	
	public double getError(){
		return control.getAvgError();
	}
}
