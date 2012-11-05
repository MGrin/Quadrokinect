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
		size(640, 480);
		background(0);
	}

	public void draw() {
		if (drone != null) {
			PImage im = drone.getVideoImage(true);
			if (im != null) {
				im.resize(640, 480);
				loadPixels();
				  
				  // We must also call loadPixels() on the PImage since we are going to read its pixels.  img.loadPixels(); 
				  for (int x = 0; x < im.width; x++ ) {
				    for (int y = 0; y < im.height; y++ ) {
				      
				      // Calculate the 1D pixel location
				      int loc = x + y*im.width;
				      
				      // Get the R,G,B values from image
				      float r = red (im.pixels[loc]);
				      float g = green (im.pixels[loc]);
				      float b = blue (im.pixels[loc]);
				      
				      r *= 3;
				      g *= 3;
				      b *= 3;
				      
				      // Constrain RGB to between 0-255
				      r = constrain(r,0,255);
				      g = constrain(g,0,255);
				      b = constrain(b,0,255);

				      pixels[loc] = color(r,g,b);;
				    }
				  }
				  
				  updatePixels(); 
			}
			color(0);
			text("Battery: "+drone.getBatteryPercentage()+"%", 0, 100);
		}
	}

	public void setDrone(ARDroneForP5 ard) {
		drone = ard;
	}
}
