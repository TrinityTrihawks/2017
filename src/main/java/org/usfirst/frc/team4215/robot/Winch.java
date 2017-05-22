package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.Victor;

public class Winch {
	// sets where the first winch motor is controlled from
	Victor winch1 = new Victor(Portmap.VICTOR_WINCH_ID1);
	// sets where the second winch motor is controlled from
	Victor winch2 = new Victor(Portmap.VICTOR_WINCH_ID2);

	public void set(double l) 
	{
		if(l < 0)
		{
			l = 0;
		}
		else if (l > 1)
		{
			l = 1;
		}
		
		winch1.set(l);
		winch2.set(l);		
	}
	
	public void stop()
	{
		// 
		winch1.set(0);
		winch1.stopMotor();
		// 
		winch2.set(0);
		winch2.stopMotor();
	}
}
