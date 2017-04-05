package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.command.*;

public class AutonomousCommandLeft extends CommandGroup {
		
	AutonomousCommandLeft(){
		addSequential(new CommandDrive(41.5,450));
//		addSequential(new CommandTurn(60));
	}

}