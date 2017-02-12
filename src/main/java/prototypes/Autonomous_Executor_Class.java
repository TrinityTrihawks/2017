package main.java.prototypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a static method used to execute tasks
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
