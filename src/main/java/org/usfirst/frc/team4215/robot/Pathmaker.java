package main.java.org.usfirst.frc.team4215.robot;

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
	
	public Pathmaker( Waypoint[] auto, Trajectory.Config configuration, TankModifier modifier) {
		this.auto = auto;
		this.configuration = configuration;
		this.modifier = modifier;
	}

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
	 * This method creates a modified trajectory based on wheel base width, or merely gets it if you have already created said trajectory.
	 * @author Jack Rausch
	 * @param wheelbasewidth
	 * @param trajectory
	 * @return TankModifier
	 */
	public TankModifier getModifier(double wheelbasewidth, Trajectory trajectory){
		if (modifier == null) {
			TankModifier mod = new TankModifier(trajectory).modify(wheelbasewidth);
			modifier = mod.modify(wheelbasewidth);
			return modifier;
		} else 
			return modifier;
		
	}
	
	/**
	 * This method gets the left trajectory
	 * @author Jack Rausch
	 * @param modifier
	 * @return Trajectory
	 */
	public Trajectory getLeftTrajectory(TankModifier modifier){
		Trajectory leftTraj = modifier.getLeftTrajectory();
		return leftTraj;
	}
	
	/**
	 * This method gets the right trajectory
	 * @param modifier
	 * @return Trajectory
	 */
	public Trajectory getRightTrajectory(TankModifier modifier){
		Trajectory rightTraj = modifier.getRightTrajectory();
		return rightTraj;
	}
	
	/**
	 * This method takes the Trajectory defined by Jaci's code and converts it into points that the TalonSRX code can use.
	 * @author Jack Rausch
	 * @param rightTraj
	 * @return double[][]
	 */
	public double[][] convertRightTrajectory(Trajectory rightTraj){
		double[][] pointListR =  new double[][]{};
		for (int i = 0; i < rightTraj.length(); i++) {
			//Retrieves a segment from the trajectory
		    Trajectory.Segment seg = trajectory.get(i);
		    //Creates point object
		    CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
		    
		    point.position = seg.position;
		    point.velocity = seg.velocity;
		    point.timeDurMs = 10;
		    
		    pointListR[0][i] = point.velocity;
		    pointListR[1][i] = point.position;
		    
		}
		return pointListR;
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
