package processing.sketch;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

import processing.core.PApplet;

public class StatusSketch extends PApplet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ARDroneForP5 drone;

	public void setup(){
		size(300, 300);
		background(0);
	}
	
	public void draw(){
		background(0);
		if(drone!=null){
			drone.printARDroneInfo();
		}
	}
	
	public void setDrone(ARDroneForP5 drone){
		this.drone = drone;
	}
}
