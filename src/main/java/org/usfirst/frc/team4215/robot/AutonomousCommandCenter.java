package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommandCenter extends CommandGroup {

	public AutonomousCommandCenter() {
		addSequential(new CommandDrive(180,true));
	}

	public AutonomousCommandCenter(String name) {
		super(name);
		addSequential(new CommandDrive(180,true));
	}

}
