package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommandCenter extends CommandGroup {

	public AutonomousCommandCenter() {
		addSequential(new CommandDrive(5*12,310));
		//addSequential(new )
	}

	public AutonomousCommandCenter(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

}
