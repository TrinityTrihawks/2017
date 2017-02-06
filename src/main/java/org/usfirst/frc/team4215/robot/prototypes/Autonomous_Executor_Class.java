package main.java.org.usfirst.frc.team4215.robot.prototypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains static methods used to execute tasks
 * @author Jack Rausch
 *
 */
public class Autonomous_Executor_Class {
	
	static List<Runnable> taskList = new ArrayList<Runnable>();
	
	/**
	 * Executes an individual method
	 * @param task
	 * @author Jack Rausch
	 */
	public static void execute(Runnable task) {
		task.run();
	}
	
	/**
	 * Executes a series of individual tasks submitted to the List
	 * @param taskList
	 * @author Jack Rausch
	 */
	public static void executeAll(List<Runnable> taskList) {
		for (int i = 0; i < taskList.size(); i++){
			execute(taskList.get(i));
		}
	}
	
	/**
	 * Submits a task to a List which may be executed using the executeAll() command
	 * @param task
	 * @author Jack Rausch
	 */
	public static void submit(Runnable task) {
		taskList.add(task);
	}
	
}
