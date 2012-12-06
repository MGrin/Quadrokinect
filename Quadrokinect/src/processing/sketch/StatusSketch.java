package processing.sketch;

import com.shigeodayo.ardrone.ARDrone;

import processing.core.PApplet;

public class StatusSketch extends PApplet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ARDrone drone;

	public void setup(){
		size(300, 300);
		background(0);
	}
	
	public void draw(){
		background(0);
		if(drone!=null){
			
		}
	}
	
	public void setDrone(ARDrone drone){
		this.drone = drone;
	}
}
