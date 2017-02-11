package main.java.org.usfirst.frc.team4215.robot.prototypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains static methods used to execute tasks
 * @author Jack Rausch
 *
 */
public class Autonomous_Executor_Class {
	
	/**
	 * Executes an individual method
	 * @param task
	 * @author Jack Rausch
	 */
	public static void execute(Runnable task) {
		task.run();
	}
	
	
	
}
