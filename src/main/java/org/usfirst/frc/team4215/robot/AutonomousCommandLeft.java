package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.command.*;

public class AutonomousCommandLeft extends CommandGroup {
		
	AutonomousCommandLeft(){
		addSequential(new CommandUltrasonic());
	}

}