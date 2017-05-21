package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4215.robot.command.*;

public class AutonomousCommandRight extends CommandGroup {

	AutonomousCommandRight(){
		addSequential(new CommandDrive(180));
	}

}
