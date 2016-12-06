package prototypes;

import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * Stops the List from executing
	 * @param taskList
	 * @throws InterruptedException
	 * @author Jack Rausch
	 */
	public static void shutdown(List<Runnable> taskList) throws InterruptedException {
		taskList.wait();
	}
}
