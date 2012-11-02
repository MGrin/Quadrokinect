package processing.sketch;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

import processing.core.PApplet;
import processing.core.PImage;

public class ARDroneVideoSketch extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7424814652795655599L;

	private ARDroneForP5 drone = null;

	public void setup() {
		size(320, 240);
		background(0);
	}

	public void draw() {
		if (drone != null) {
			PImage im = drone.getVideoImage(true);
			if (im != null) {
				image(im, 0, 0);
			}
			color(255);
			text("Battery: "+drone.getBatteryPercentage()+"%", 0, 100);
		}
	}

	public void setDrone(ARDroneForP5 ard) {
		drone = ard;
	}
}
