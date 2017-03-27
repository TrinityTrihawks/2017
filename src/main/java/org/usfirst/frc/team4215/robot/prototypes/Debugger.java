package org.usfirst.frc.team4215.robot.prototypes;

import java.lang.reflect.Method;


/**
 * This class uses reflection to find the debug methods within each class.
 * The debug methods contain all the values the creater deemed useful for debugging.
 * These methods can be accessed by creating a Debugger object and using the debug() method.
 * @author Jack Rausch
 * 
 * 
 */
public class Debugger {
	
	private Class cls;
	private Object tor;
	private Class noparams[] = {};
	private Method mtf;
	
	/**
	 * 
	 * @param Class 
	 * @param Constructor 
	 * @author Jack Rausch
	 */
	public Debugger(Class cl, Object ctor){
		this.cls = cl;
		this.tor = ctor;
	}
	
	/**
	 * Creates an instance of the class from the constructor and invokes the debug method for that class.
	 * Finally, debug will print the resulting JSON document.
	 * @author Jack Rausch
	 * 
	 */
	public void debug(){
		try{
			//builds the debug method from the class
			//mtf = cls.getDeclaredMethod("jdebug", noparams);
			//Creates an instance of the class to be used in the debug methods invocation
			//Object instance = cls.newInstance();
			//invokes the debug method; null is used because the debug method has no parameters
			Object invoc = mtf.invoke(tor, null);
			System.out.print(invoc);
		} catch (Exception e){
			System.err.println(e);
		}
	}
	
	
	
	
	
}
