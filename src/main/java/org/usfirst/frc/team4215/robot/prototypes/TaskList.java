package main.java.org.usfirst.frc.team4215.robot.prototypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created to allow easy stringing
 * together of tasks
 * 
 * @author waweros
 *
 */
public class TaskList implements Runnable{
	
	private List<Runnable> tasks;
	
	/**
	 * Makes a TaskList from a List
	 * @param tasks
	 */
	public TaskList(List<Runnable> tasks) {
		this.tasks = tasks;
	}
	
	/**
	 * Allows easy and clear task
	 * management
	 * @param task
	 */
	public TaskList(Runnable task){
		tasks = new ArrayList<Runnable>();
		tasks.add(task);
	}
	
	/**
	 * Adds a new runnable task
	 * @param newTask
	 */
	public void add(Runnable newTask){
		tasks.add(newTask);
	}
	
	/**
	 * Runs all the tasks in order
	 */
	@Override
	public void run(){
		for(Runnable i : tasks){
			i.run();
		}
	}
}
