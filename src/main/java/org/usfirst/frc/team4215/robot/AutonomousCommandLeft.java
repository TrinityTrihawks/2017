package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.command.*;

public class AutonomousCommandLeft extends CommandGroup {
		
	
	AutonomousCommandLeft(){
		//addSequential(new CommandDrive(41.5));
		addSequential(new CommandVision(vision));
	}

}