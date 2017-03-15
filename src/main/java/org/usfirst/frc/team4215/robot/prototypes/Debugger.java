package org.usfirst.frc.team4215.robot.prototypes;

import java.lang.reflect.InvocationTargetException;
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
	
	private String classname;
	private Class cls;
	private Class noparams[] = {};
	private Method mtf;
	
	/**
	 * Constructor for the method which builds the class and debug method objects from the classname.
	 * The class name's input should be a String in the form: "package.class"
	 * Example: UltrasonicHub's classname is "prototypes.UltrasonicHub"
	 * @param String classname
	 * @author Jack Rausch
	 */
	public Debugger(String classname){
		classname = this.classname;
		//try/catch loop to print errors if any exceptions are triggered
		try {
			//builds class object from inputed classname value
			cls = Class.forName(classname);
			//builds method from the class object built in the previous line
			mtf = cls.getDeclaredMethod("debug", noparams);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates an instance of the class from the constructor and invokes the debug method for that class.
	 * Finally, debug will print the resulting JSON document.
	 * @author Jack Rausch
	 * 
	 */
	public void debug(){
		try{
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
