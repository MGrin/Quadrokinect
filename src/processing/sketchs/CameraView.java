package processing.sketchs;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

import processing.core.PApplet;
import processing.core.PImage;

public class CameraView extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7840982273798806415L;
	
	ARDroneForP5 ardrone;

	public CameraView(ARDroneForP5 ar) {
		this.ardrone = ar;
	}

	public void setup() {
		size(320, 240);
	}

	public void draw() {
		PImage img = ardrone.getVideoImage(true);
		background(0,0,0);
		if (img == null)
			return;
		image(img, 0, 0);
	}
}
