package main.java.org.usfirst.frc.team4215.robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;


/**
 * This class defines each autonomous path and configures it.
 * IMPORTANT: Make sure that when autonomous is initialized both configuration and trajectory are set to null, or else the config and trajectory will not be initialized!
 * 
 * @author Jack Rausch
 *
 */
public class Pathmaker {
	
	private double dt;
	private double MAX_VELOCITY;
	private double MAX_ACCELERATION;
	private double MAX_JERK;
	private Waypoint[] auto;
	private Trajectory.Config configuration = null;
	private Trajectory trajectory = null;
	
	/**
	 * This method creates the configuration based on certain parameters. 
	 * If you wish to get the configuration use the getConfig method instead.
	 * @author Jack Rausch
	 * @param dt
	 * @param MAX_VELOCITY
	 * @param MAX_ACCELERATION
	 * @param MAX_JERK
	 * @return config(Don't use)
	 */
	public Trajectory.Config config(double dt, double MAX_VELOCITY, double MAX_ACCELERATION, double MAX_JERK){
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
        		Trajectory.Config.SAMPLES_HIGH, dt, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
        return config;

	}
	
	Waypoint[] auto1 = new Waypoint[]{
			//Put points here
	};
	
	Waypoint[] auto2 = new Waypoint[]{
			//Put points here
	};
	
	Waypoint[] auto3 = new Waypoint[]{
			//Put points here
	};
	
	/**
	 * This method gets the trajectory if it has already been generated or generates it if necessary.
	 * @author Jack Rausch
	 * @param auto
	 * @param config
	 * @return trajectory
	 */
	public Trajectory getTrajectory(Waypoint[] auto, Trajectory.Config config){
		if (trajectory == null){
			trajectory = Pathfinder.generate(auto, config);
			return trajectory;
		} else {
			return trajectory;
		}
	}
	
	/**
	 * This method gets the configuration if it has already been generated or generates it if necessary. Use this method to get the configuration.
	 * @author Jack Rausch
	 * @param dt
	 * @param MAX_VELOCITY
	 * @param MAX_ACCELERATION
	 * @param MAX_JERK
	 * @return configuration
	 */
	public Trajectory.Config getConfig(double dt, double MAX_VELOCITY, double MAX_ACCELERATION, double MAX_JERK){
		if (configuration == null){
			//Admittedly a very inefficient way to do this, but I'll fix it later(never).
			configuration = config(dt, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
			return configuration;
		} else {
			return configuration;
		}
				
	}

	//Creates modifier
	TankModifier modifier = new TankModifier(trajectory).modify(0.5);
	

	
	
	
	

	
	//Simple getters and setters
	public Waypoint[] getAuto1() {
		return auto1;
	}

	public void setAuto1(Waypoint[] auto1) {
		this.auto1 = auto1;
	}

	public Waypoint[] getAuto2() {
		return auto2;
	}

	public void setAuto2(Waypoint[] auto2) {
		this.auto2 = auto2;
	}

	public Waypoint[] getAuto3() {
		return auto3;
	}

	public void setAuto3(Waypoint[] auto3) {
		this.auto3 = auto3;
	}

}
