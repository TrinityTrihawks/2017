package org.usfirst.frc.team4215.robot.prototypes;

import java.lang.reflect.Method;

import prototypes.SimpleRead;
import prototypes.UltrasonicHub;

/**
 * This class uses reflection to find the debug methods within each class.
 * The debug methods contain all the values the creater deemed useful for debugging.
 * These methods can be accessed by creating a Debugger object and using the debug() method.
 * @author Jack Rausch
 * 
 * 
 */
public class Debugger {
	
	private String classname;
	private Class cls;
	private Class noparams[] = {};
	private Method mtf;
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException{
		Debugger hub = new Debugger();
		hub.cls = UltrasonicHub.class;
		hub.debug();
	}
	/**
	 * Constructor for the method which builds the class and debug method objects from the classname.
	 * The class name's input should be a String in the form: "package.class"
	 * Example: UltrasonicHub's classname is "prototypes.UltrasonicHub"
	 * @param String classname
	 * @author Jack Rausch
	 */
	public Debugger(){
		cls = this.cls;
		mtf = this.mtf;
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
			mtf = cls.getDeclaredMethod("debug", noparams);
			//Creates an instance of the class to be used in the debug methods invocation
			Object instance = cls.newInstance();
			//invokes the debug method; null is used because the debug method has no parameters
			Object invoc = mtf.invoke(instance, null);
			System.out.print(invoc);
		} catch (Exception e){
			System.err.println(e);
		}
	}
	
	
}
