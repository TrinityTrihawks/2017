package main.java.org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import src.main.java.prototypes.UltrasonicHub;

public class UltrasonicPID implements PIDSource {

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
			pidSource = PIDSourceType.kDisplacement;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
	}

	UltrasonicHub hub = new UltrasonicHub();
	double P;
	double I;
	double D;
	PIDController hubControl = new PIDController(P, I, D, desiredAngle, null);
	
	PIDSource desiredAngle = new PIDSource(){
		public double pidGet() {
			double correctionAngle = hub.getCorrectionAngle();
			return correctionAngle;
		}

		
		};
	
}
