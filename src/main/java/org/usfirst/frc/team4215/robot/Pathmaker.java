package org.usfirst.frc.team4215.robot;


//import org.json.JSONObject;

import com.ctre.CANTalon;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Config;
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
	
	private final double dt = .01;
	private double MAX_VELOCITY;
	private double MAX_ACCELERATION;
	private double MAX_JERK;
	private Waypoint[] auto;
	private Trajectory.Config configuration;
	private Trajectory trajectory;
	private TankModifier modifier;
	
	/**
	 * This method must be used at the beginning of Autonomous for the Pathmaker code to work.
	 * @author Jack Rausch
	 */
	public void pathmInit(){
		Trajectory.Config configuration = null;
		Trajectory trajectory = null;
		TankModifier modifier = null;
	}
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
	public Trajectory.Config config(double MAX_VELOCITY, double MAX_ACCELERATION, double MAX_JERK){
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_QUINTIC,
        		Trajectory.Config.SAMPLES_LOW, dt, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
        return config;

	}
	
	Waypoint[] Middle_2 = new Waypoint[]{
		new Waypoint(76, 0, Pathfinder.d2r(0)),	
	};
	
	Waypoint[] test = new Waypoint[]{
			new Waypoint(12, 0, Pathfinder.d2r(0)),	
			new Waypoint(-5,0,0)
		};
	

	public Pathmaker(Waypoint[] auto, Trajectory.Config configuration, TankModifier modifier) {
		this.auto = auto;
		this.configuration = configuration;
		this.modifier = modifier;
	}

	//x and y correspond to meters
	Waypoint[] auto1 = new Waypoint[]{
			//new Waypoint(x, y, Pathfinder.d2r(theta))
	};
	
	Waypoint[] auto2 = new Waypoint[]{
			//new Waypoint(x, y, Pathfinder.d2r(theta))
	};
	
	Waypoint[] auto3 = new Waypoint[]{
			//new Waypoint(x, y, Pathfinder.d2r(theta))
	};

	/**
	 * This method gets the trajectory if it has already been generated or generates it if necessary.
	 * @author Jack Rausch
	 * @param auto
	 * @param config

	 * @return Trajectory
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
	public Trajectory.Config getConfig( double MAX_VELOCITY, double MAX_ACCELERATION, double MAX_JERK){
		if (configuration == null){
			//Admittedly a very inefficient way to do this, but I'll fix it later(never).
			configuration = config(MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
			return configuration;
		} else {
			return configuration;
		}
				
	}


	/**
	 * This method creates a modified trajectory based on wheel base width, or merely gets it if you have already created said trajectory.
	 * This method gets both the right and left trajectory
	 * @author Jack Rausch
	 * @param wheelbasewidth
	 * @param trajectory
	 * @return Trajectory[]
	 */
	public Trajectory[] getBothTrajectories(double wheelbasewidth, Trajectory trajectory){

		TankModifier mod = new TankModifier(trajectory).modify(wheelbasewidth);
		modifier = mod.modify(wheelbasewidth);
		
		Trajectory leftTraj = modifier.getLeftTrajectory();
		Trajectory rightTraj = modifier.getRightTrajectory();
		
		Trajectory[] bothTrajs = {leftTraj, rightTraj};
		return bothTrajs;
	}
	
	

	/**
	 * This method takes the Trajectory defined by Jaci's code and converts it into points that the TalonSRX code can use.
	 * @author Jack Rausch
	 * @param traj
	 * @return double[][]
	 */
	public double[][] convertTrajectory(Trajectory traj, boolean side){
		double[][] pointList =  new double[2][traj.length()];
		//pointList[2][traj.length()];
		for (int i = 0; i < traj.length(); i++) {
			//Retrieves a segment from the trajectory
		    Trajectory.Segment seg = trajectory.get(i);
		    //Creates point object
		    CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
		    
		    if (side){
			    point.position = seg.position;
			    point.velocity = seg.velocity;
			    point.timeDurMs = 10;
			    
			    pointList[0][i] = point.velocity;
			    
			    pointList[1][i] = point.position;
		    } else {
		    	point.position = -seg.position;
			    point.velocity = -seg.velocity;
			    point.timeDurMs = 10;
			    
			    pointList[0][i] = point.velocity;
			    
			    pointList[1][i] = point.position;
		    }
		    
		}
		return pointList;
	}
	
	/**
	 * This method takes the Trajectory defined by Jaci's code and converts it into points that the TalonSRX code can use.
	 * @author Jack Rausch
	 * @param leftTraj
	 * @return double[][]
	 */
	public double[][] convertLeftTrajectory(Trajectory leftTraj){
		double[][] pointListL =  new double[][]{};
		for (int i = 0; i < leftTraj.length(); i++) {
		    Trajectory.Segment seg = trajectory.get(i);
		    CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
		    
		    point.position = seg.position;
		    point.velocity = seg.velocity;
		    point.timeDurMs = 10;
		    
		    pointListL[0][i] = point.velocity;
		    pointListL[1][i] = point.position;
		    
		}
		return pointListL;
	}
	
	
}
