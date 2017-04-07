package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommandCenter extends CommandGroup {

	public AutonomousCommandCenter() {
		addSequential(new CommandDrive(96, true));
		//addSequential(new CommandDrive(36));
		//addSequential(new CommandUltrasonic());
	}

	public AutonomousCommandCenter(String name) {
		super(name);
		addSequential(new CommandDrive(180,true));
	}

}
