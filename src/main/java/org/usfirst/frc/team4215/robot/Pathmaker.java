package main.java.org.usfirst.frc.team4215.robot;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class Pathmaker {
	
	private double dt;
	private double MAX_VELOCITY;
	private double MAX_ACCELERATION;
	private double MAX_JERK;
	private Waypoint[] auto;
	private Trajectory.Config config;
	private Trajectory trajectory = null;
	
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
	
	
	public Trajectory generateTrajectory(Waypoint[] auto, Trajectory.Config config){
		if (trajectory == null){
			Trajectory traj = Pathfinder.generate(auto, config);
			trajectory = traj;
			return trajectory;
		} else {
			return trajectory;
		}
	}

	TankModifier modifier = new TankModifier(trajectory).modify(0.5);
	

	
	
	
	
	
	public Trajectory getTrajectory() {
		return trajectory;
	}

	

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
