package org.usfirst.frc.team4215.robot.commandgroup;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team4215.robot.command.*;

public class AutonomousCommandCenter extends CommandGroup {

	public AutonomousCommandCenter() {
		addSequential(new CommandDrive(24, true));
		//addSequential(new CommandDrive(36));
		//addSequential(new CommandUltrasonic());
	}

	public AutonomousCommandCenter(String name) {
		super(name);
		addSequential(new CommandDrive(180,true));
	}

}
