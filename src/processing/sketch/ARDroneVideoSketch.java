package processing.sketch;

import jp.nyatla.nyar4psg.MultiMarker;
import jp.nyatla.nyar4psg.NyAR4PsgConfig;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

import ardrone.ArdroneGroup;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PMatrix;

public class ARDroneVideoSketch extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7424814652795655599L;

	private ArdroneGroup group = null;
	public int row = 1;
	public int col = 1;

	public static final int w = 480;
	public static final int h = 360;

	PMatrix temp;
	MultiMarker nya;
	String camParamPath = "libs/nyar4psg-1.2.0/data/camera_para.dat";
	String patternPath = "libs/nyar4psg-1.2.0/data/patt.hiro";

	public void setup() {
		size(col * w, row * h, P3D);
		background(0);
		nya = new MultiMarker(this, width, height, camParamPath,
				NyAR4PsgConfig.CONFIG_PSG);
		nya.addARMarker(patternPath, 80);// id=0
		temp = getMatrix();
	}

	public void drawImage(ARDroneForP5 drone) {
		PImage im = drone.getVideoImage(true);
		if (im != null) {
			im.resize(w, h);
			image(im, 0, 0);
			/*
			 * loadPixels(); for (int x = 0; x < im.width; x++) { for (int y =
			 * 0; y < im.height; y++) { int loc = x + y * im.width; float r =
			 * red(im.pixels[loc]); float g = green(im.pixels[loc]); float b =
			 * blue(im.pixels[loc]);
			 * 
			 * r *= 3; g *= 3; b *= 3; r = constrain(r, 0, 255); g =
			 * constrain(g, 0, 255); b = constrain(b, 0, 255);
			 * 
			 * pixels[loc] = color(r, g, b); } } updatePixels();
			 */

			nya.detect(im);
			if (nya.isExistMarker(0)) {
				nya.beginTransform(0);
				fill(255, 0, 255);
				stroke(100, 0, 0);
				rect(-40, -40, 80, 80);
				stroke(255);
				fill(255);
				nya.endTransform();
			}
		}
		pushMatrix();
		color(255);
		text("Battery: " + drone.getBatteryPercentage() + "%", 0, 100);
		popMatrix();
	}

	public void draw() {
		background(0);
		if (group != null) {
			int x = 0;
			int y = 0;
			if (group.getARDrone(0) != null) {
				for (int i = 0; i < group.getArdroneNumber(); i++) {
					// translate(x * w, 0);
					drawImage(group.getARDrone(i));
					// translate(-x * w, 0);
					if (x < col)
						x++;
					else {
						x = 0;
						translate(0, -y * h);
						y++;
						translate(0, y * h);
					}
				}
			} else {
				text("Virtual drones,\nno image =(", 0, 50);
			}

		}
	}

	public void setGroup(ArdroneGroup group) {
		this.group = group;
	}

	public void calculateRowsAndCols(int nb) {
		col = (int) Math.ceil(Math.sqrt(nb));
		if (col * (col - 1) > nb)
			row = col - 1;
		else
			row = col;
	}

	public void keyPressed() {
		if (key == 'c' && group != null && group.getARDrone(0) != null) {
			group.toggleCamera();
		}
	}
}
