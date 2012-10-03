package processingCode;

import java.awt.Point;

import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;
import ardroneControl.ArdroneControl;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

public class KinectCapture_v_2 extends PApplet {

	private static final long serialVersionUID = 8456790925493242935L;

	OscP5 oscp5;
	ArdroneControl ardrone;

	float[] leftHand = new float[3];
	float[] rightHand = new float[3];
	int userTracked = -1;

	static final int width = 1024;
	static final int height = 768;

	public void setup() {
		size(1024, 768, P3D);
		background(0, 0, 0);

		oscp5 = new OscP5(this, 7110);

		ardrone = new ArdroneControl(new ARDroneForP5("192.168.1.1"));
		ardrone.connect();
	}

	String lastCommand = "land";

	public void draw() {
		background(0, 0, 0);
		drawScene();
		drawController();
		handCommands();
	}

	void handCommands() {

	}

	void drawController() {
		
	}

	public void keyPressed() {
		if (key == CODED) {
			if (keyCode == SHIFT) {
				println("TakeOFF!");
				ardrone.takeOff();
				lastCommand = "takeOff";
			} else if (keyCode == CONTROL) {
				println("landing");
				ardrone.landing();
				lastCommand = "land";
			} else if (keyCode == ESC) {
				ardrone.safeDrone();
			}
		}

	}

	void drawScene() {
		stroke(255, 255, 255);
		fill(0);
		line(width / 2, 0, width / 2, height);
		stroke(0);
	}

	void oscEvent(OscMessage msg) {
		// msg.print();
		if (msg.checkAddrPattern("/new_user") && msg.checkTypetag("i")) {
			// New user
			if (userTracked == -1) {
				int userId = msg.get(0).intValue();
				userTracked = userId;
			} else {
				System.out
						.println("New user detected, but still tracking the first one\nThe new one - please, GO OUT FROM THE KINECT! =)");
			}
		} else if (msg.checkAddrPattern("/new_skel") && msg.checkTypetag("i")) {
			// New user calibrated

		} else if (msg.checkAddrPattern("/joint") && msg.checkTypetag("sifff")) {
			// Getting coordinates of hands and user id.
			String bodyPart = msg.get(0).stringValue();
			int userId = msg.get(1).intValue();
			if (userTracked == -1) {
				userTracked = userId;
			}
			if (userTracked != -1 && userId == userTracked) {
				if (bodyPart.equals("r_hand")) {
					leftHand[0] = msg.get(2).floatValue();
					leftHand[1] = msg.get(3).floatValue();
					leftHand[2] = msg.get(4).floatValue();
					// println("LeftHand!");
				}
				if (bodyPart.equals("l_hand")) {
					rightHand[0] = msg.get(2).floatValue();
					rightHand[1] = msg.get(3).floatValue();
					rightHand[2] = msg.get(4).floatValue();
					// println("RightHand!");
				}
			}
		} else if (msg.checkAddrPattern("/lost_user") && msg.checkTypetag("i")) {
			// User lost
			int id = msg.get(0).intValue();
			if (id == userTracked) {
				userTracked = -1;
			}
		}
	}

}
