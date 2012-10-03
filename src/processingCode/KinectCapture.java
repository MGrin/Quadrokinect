package processingCode;

import java.awt.Point;

import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;

import ardroneControl.ArdroneControl;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

public class KinectCapture extends PApplet {

	private static final long serialVersionUID = 8456790925493242935L;

	OscP5 oscp5;
	ArdroneControl ardrone;

	float[] leftHand = new float[3];
	float[] rightHand = new float[3];
	int userTracked = -1;

	static final int ZONE_SIZE = 50;

	static final int width = 1024;
	static final int height = 768;

	final CircleZone leftHandStableZone = new CircleZone(this, new Point(
			2 * width / 5 - 15, height / 2), ZONE_SIZE * 2);
	final CircleZone rightHandStableZone = new CircleZone(this, new Point(
			3 * width / 5 + 15, height / 2), ZONE_SIZE * 2);

	final CircleZone gazZone = new CircleZone(this, new Point(width / 3,
			height / 4), ZONE_SIZE * 4);
	final CircleZone directionZone = new CircleZone(this, new Point(width / 3,
			3 * height / 4), ZONE_SIZE * 4);
	final CircleZone landingZoneL = new CircleZone(this, new Point(width / 9,
			3 * height / 4), ZONE_SIZE * 4);
	final CircleZone takeoffZoneL = new CircleZone(this, new Point(width / 9,
			height / 4), ZONE_SIZE * 4);
	final CircleZone turnLeftZone = new CircleZone(this, new Point(width / 4,
			height / 2), ZONE_SIZE * 2.5f);

	final CircleZone goLeftTurnRightZone = new CircleZone(this, new Point(
			width / 2, height / 2), ZONE_SIZE * 2.5f);

	final CircleZone upForwardZone = new CircleZone(this, new Point(
			2 * width / 3, height / 4), ZONE_SIZE * 4);
	final CircleZone downBackZone = new CircleZone(this, new Point(
			2 * width / 3, 3 * height / 4), ZONE_SIZE * 4);
	final CircleZone landingZoneR = new CircleZone(this, new Point(
			8 * width / 9, 3 * height / 4), ZONE_SIZE * 4);
	final CircleZone takeoffZoneR = new CircleZone(this, new Point(
			8 * width / 9, height / 4), ZONE_SIZE * 4);
	final CircleZone goRightZone = new CircleZone(this, new Point(
			3 * width / 4, height / 2), ZONE_SIZE * 2.5f);

	CircleZone[] zones;

	public void setup() {
		size(1024, 768, P3D);
		background(0, 0, 0);
		zones = new CircleZone[] { gazZone, directionZone, turnLeftZone,
				goLeftTurnRightZone, upForwardZone, downBackZone, goRightZone,
				leftHandStableZone, rightHandStableZone };

		oscp5 = new OscP5(this, 7110);

		ardrone = new ArdroneControl(new ARDroneForP5("192.168.1.1"));
		ardrone.connect();
	}

	String lastCommand = "land";

	public void draw() {
		background(0, 0, 0);
		drawScene();

		for (CircleZone c : zones) {
			if (c.contains(leftHand)) {
				stroke(255, 0, 0);
				c.display();
				stroke(0);
				break;
			}
		}
		for (CircleZone c : zones) {
			if (c.contains(rightHand)) {
				stroke(0, 255, 0);
				c.display();
				stroke(0);
				break;
			}
		}

		drawHands();
		handCommands();
	}

	void handCommands() {
		if (leftHandStableZone.contains(leftHand) && ardrone.inAir()) {
			ardrone.stop();
			lastCommand = "stop";
			println("STOP");
		} else if (gazZone.contains(leftHand)) {
			if (upForwardZone.contains(rightHand) && ardrone.inAir()
					&& !lastCommand.equals("up")) {
				ardrone.up(15);
				lastCommand = "up";
				println("UP");
			} else if (downBackZone.contains(rightHand) && ardrone.inAir()
					&& !lastCommand.equals("down")) {
				ardrone.down(15);
				lastCommand = "down";
				println("DOWN");
			} else if (goRightZone.contains(rightHand) && ardrone.inAir()
					&& !lastCommand.equals("gright")) {
				ardrone.goRight(20);
				lastCommand = "gright";
				println("GO RIGHT");
			} else if (goLeftTurnRightZone.contains(rightHand)
					&& ardrone.inAir() && !lastCommand.equals("gleft")) {
				ardrone.goLeft(20);
				lastCommand = "gleft";
				println("GO LEFT");
			}
		} else if (directionZone.contains(leftHand)) {
			if (upForwardZone.contains(rightHand) && ardrone.inAir()
					&& !lastCommand.equals("forward")) {
				ardrone.forward(10);
				lastCommand = "forward";
				println("GO FORWARD");
			} else if (downBackZone.contains(rightHand) && ardrone.inAir()
					&& !lastCommand.equals("back")) {
				ardrone.backward(10);
				println("GO BACKWARD");
			}
		} else if (turnLeftZone.contains(leftHand) && ardrone.inAir()
				&& !lastCommand.equals("sleft")) {
			ardrone.spinLeft(20);
			lastCommand = "sleft";
			println("SPIN LEFT");
		} else if (goLeftTurnRightZone.contains(leftHand) && ardrone.inAir()
				&& !lastCommand.equals("sright")) {
			ardrone.spinRight(20);
			lastCommand = "sright";
			println("SPIN RIGHT");
		}

	}

	void drawHands() {
		if (userTracked != -1) {
			if (leftHand[2] > rightHand[2]) {
				drawLeft();
				drawRightHand();
			} else {
				drawRightHand();
				drawLeft();
			}
		}
	}

	public void keyPressed() {
		try {
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
		} catch (NullPointerException e) {
			if (key == CODED) {
				if (keyCode == SHIFT) {
					println("TakeOFF!");
					// ardrone.takeOff();
					lastCommand = "takeOff";
				} else if (keyCode == CONTROL) {
					println("landing");
					// ardrone.landing();
					lastCommand = "land";
				} else if (keyCode == ESC) {
					ardrone.safeDrone();
				}
			}
		}
	}

	void drawScene() {
		stroke(255, 255, 255);
		fill(0);
		line(width / 2, 0, width / 2, height);
		leftHandStableZone.display();
		rightHandStableZone.display();

		gazZone.display();
		directionZone.display();
		turnLeftZone.display();
		upForwardZone.display();
		goRightZone.display();
		downBackZone.display();
		goLeftTurnRightZone.display();

		stroke(0);
	}

	void drawRightHand() {
		fill(0, 255, 0);
		ellipseMode(CENTER);
		ellipse(rightHand[0], rightHand[1], rightHand[2], rightHand[2]);
		fill(0);
	}

	void drawLeft() {
		fill(255, 0, 0);
		ellipseMode(CENTER);
		ellipse(leftHand[0], leftHand[1], leftHand[2], leftHand[2]);
		fill(0);
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
