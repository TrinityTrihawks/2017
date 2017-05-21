package org.usfirst.frc.team4215.robot.CommandGroup;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.usfirst.frc.team4215.robot.command.*;

public class AutonomousCommandLeft extends CommandGroup {
		
	AutonomousCommandLeft(AxisCamera cameraFront){

		//addSequential(new CommandDrive(180, 10));
		addSequential(new CommandDrive(41.5));
		addSequential(new CommandTurn(60));
		//addSequential(new CommandDrive(24, 270));
	}

}