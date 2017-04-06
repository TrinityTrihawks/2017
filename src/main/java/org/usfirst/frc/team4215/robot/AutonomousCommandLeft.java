package org.usfirst.frc.team4215.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class AutonomousCommandLeft extends CommandGroup {
		
	AutonomousCommandLeft(AxisCamera cameraFront){
		addSequential(new CommandDrive(41.5,450));
		//addSequential(new CommandDrive(180, 10));
		addSequential(new CommandTurn(60));
		//addSequential(new CommandDrive(24, 270));
	}

}