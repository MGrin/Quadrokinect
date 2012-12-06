package test;

import com.shigeodayo.ardrone.ARDrone;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Test extends PApplet {

	ARDrone drone;
	int speedX = 0;
	int speedY = 0;
	int speedZ = 0;
	int speedAng = 0;
	boolean changed = false;

	public void setup() {
		size(640, 400);
		textFont(createFont(PFont.list()[0], 20));
		drone = new ARDrone();
		drone.connect(5552);
		drone.connectVideo();
		drone.connectNav(5554);
		drone.start();
		drone.setMaxAltitude(1000000);
	}

	public void draw() {
		background(0);
		PImage im = drone.getVideoImage(true);
		if (im != null) {
			image(im, 0, 0);
		}
		stroke(255);
		text("Battery: " + drone.getBatteryPercentage() + "%", 10, 380);
		if (changed) {
			drone.move3D(speedX, speedY, speedZ, speedAng);
			changed = false;
		}
	}

	public void keyPressed() {
		if (key == 'c')
			drone.toggleCamera();
		else if (key == 't')
			drone.takeOff();
		else if (key == 'l')
			drone.landing();
		else if (key == 'w') {
			speedX+=50;
			changed = true;
		} else if (key == 's') {
			speedX-=50;
			changed = true;
		} else if (key == 'a') {
			speedY-=50;
			changed = true;
		} else if (key == 'd') {
			speedY+=50;
			changed = true;
		} else if (key == CODED) {
			if (keyCode == UP) {
				speedZ+=50;
				changed = true;
			} else if (keyCode == DOWN) {
				speedZ-=50;
				changed = true;
			} else if (keyCode == LEFT) {
				speedAng-=50;
				changed = true;
			} else if (keyCode == RIGHT) {
				speedAng+=50;
				changed = true;
			}

		}
	}
}
