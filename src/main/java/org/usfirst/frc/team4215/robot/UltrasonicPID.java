package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import prototypes.UltrasonicHub;

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

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
