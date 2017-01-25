package prototypes;

/**
 * Merely a class to test my Executor code (It works BTW)
 * @author Jack Rausch
 *
 */
public class SampleExecutor extends Autonomous_Executor_Class{

	public static void main(String[] args) {
		Runnable task1 = new SomeRunnable1();
		Runnable task2= new SomeRunnable2();
		submit(task1);
		submit(task2);
		executeAll(taskList);

	}
	
	private static class SomeRunnable1 implements Runnable{

		@Override
		public void run() {
			System.out.println("#1 is working...");
			
		}
		
	}
	
	
	private static class SomeRunnable2 implements Runnable{

		@Override
		public void run() {
			System.out.println("#2 is working...");
			
		}
		
	}
	
	
	
}
