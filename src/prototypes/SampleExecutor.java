package prototypes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SampleExecutor {

	public static void main(String[] args) {
		Runnable task1 = new SomeRunnable1();
		Runnable task2= new SomeRunnable2();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(task1);
		executor.execute(task2);

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
	
	public void execute(Runnable task){
		task.run();
	}

}
