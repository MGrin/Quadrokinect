package processing.sketchs;

import gui.VideoWindow;

import java.util.LinkedList;

import oscP5.OscMessage;
import oscP5.OscP5;
import processing.classes.Axis;
import processing.core.PApplet;
import utils.Calculus;
import utils.Constants;
import ardrone.ArdroneGroup;
import ardrone.CommandsListener;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

public class KinectCapture3D extends PApplet {

	private static final long serialVersionUID = 8456790925493242935L;

	OscP5 oscp5;

	ArdroneGroup controlGroup;
	LinkedList<ArdroneGroup> groups = new LinkedList<ArdroneGroup>();

	float[] leftHand = new float[] { -Constants.SAFE_ZONE_SIZE, 0, 0 };
	float[] rightHand = new float[] { Constants.SAFE_ZONE_SIZE, 0, 0 };
	float[] controllerLocation = new float[] { 0, 0, 0 };

	int userTracked = -1;

	Axis axis;
	float rotX;
	float rotY;
	float rotZ;

	CommandsListener commandsListener;

	public void setup() {
		size(1200, 800, P3D);
		background(0, 0, 0);

		axis = new Axis(this);

		oscp5 = new OscP5(this, 7110);

		controlGroup = new ArdroneGroup(0);

		/*ARDroneForP5 mainArdrone = new ARDroneForP5(Constants.IP_ADDRESS_MASK
				+ "3");*/
		//mainArdrone.setMaxAltitude(40000);
		ARDroneForP5 secondArdrone = new ARDroneForP5("192.168.0.3");
		secondArdrone.setMaxAltitude(400000);

		//controlGroup.addArdrone(mainArdrone, 3);
		controlGroup.addArdrone(secondArdrone, 4);
		groups.add(controlGroup);

		if (controlGroup.connect()) {
			//(new VideoWindow(mainArdrone)).setVisible(true);
			(new VideoWindow(secondArdrone)).setVisible(true);
		}
		commandsListener = new CommandsListener(groups, controlGroup, this);

	}

	public void draw() {
		background(0, 0, 0);

		rotateX(rotX);
		rotateY(rotY);
		rotateZ(rotZ);

		drawController();
		drawScene();
		drawSafeZone();
		drawPauseZone();
		handCommands();
	}

	void handCommands() {
		if (userTracked != -1) {
			commandsListener.hand(controllerLocation, leftHand, rightHand);
		}
	}

	void drawController() {
		noStroke();
		fill(255, 255, 0);
		lights();
		pushMatrix();
		translate((int) controllerLocation[0], (int) controllerLocation[1],
				(int) controllerLocation[2]);
		sphere(Constants.CONTROLLER_SIZE);
		translate((int) leftHand[0], (int) leftHand[1], (int) leftHand[2]);
		sphere(Constants.HAND_SIZE);
		translate((int) (-leftHand[0] + rightHand[0]),
				(int) (-leftHand[1] + rightHand[1]),
				(int) (-leftHand[2] + rightHand[2]));
		sphere(Constants.HAND_SIZE);
		popMatrix();
		fill(0);
	}

	void drawScene() {
		axis.display();
		fill(0, 0, 0, 0);
		stroke(255, 255, 255);
		box(50);
		stroke(0);
		fill(0);

		drawDistanceBetweenHands();
		drawInformation();
	}

	void drawSafeZone() {
		pushMatrix();
		fill(0, 0, 0, 0);
		if (controllerIsInSafeZone())
			stroke(0, 255, 255);
		else
			stroke(255, 255, 0);
		strokeWeight(3);

		for (int i = 0; i < Constants.SAFE_ZONE_STEP; i++) {
			ellipse(0, 0, Constants.SAFE_ZONE_SIZE, Constants.SAFE_ZONE_SIZE);
			rotateX(Constants.SAFE_ZONE_DELTA);
		}

		for (int i = 0; i < Constants.SAFE_ZONE_STEP; i++) {
			ellipse(0, 0, Constants.SAFE_ZONE_SIZE, Constants.SAFE_ZONE_SIZE);
			rotateY(Constants.SAFE_ZONE_DELTA);
		}

		for (int i = 0; i < Constants.SAFE_ZONE_STEP; i++) {
			ellipse(0, 0, Constants.SAFE_ZONE_SIZE, Constants.SAFE_ZONE_SIZE);
			rotateZ(Constants.SAFE_ZONE_DELTA);
		}

		stroke(0);
		popMatrix();
	}

	void drawDistanceBetweenHands() {
		stroke(255);
		pushMatrix();
		translate(-25 + Calculus.getDistance(leftHand, rightHand) / 2, -20, -20);
		text("Distance between hands: ", -50, -50, -50);
		fill(150, 150, 255);
		box(Calculus.getDistance(leftHand, rightHand), 10, 10);
		popMatrix();
	}

	void drawInformation() {
		fill(255);
		textSize(2);
		text("Controlled group: " + controlGroup.getID(), -60, -30, 25);
		text("Number of kopters: " + controlGroup.getArdroneNumber(), -60, -25,
				25);
		fill(0);
	}

	void drawPauseZone() {
		fill(0, 0, 0, 0);
		if (!controllerIsInMenuZone())
			stroke(255, 255, 255, 100);
		else
			stroke(255, 0, 0, 100);
		strokeWeight(2);

		pushMatrix();
		translate(Constants.MENU_LEFT_POSITION[0],
				Constants.MENU_LEFT_POSITION[1],
				Constants.MENU_LEFT_POSITION[2]);
		sphere(Constants.MENU_ZONE_SIZE);
		popMatrix();

		pushMatrix();
		translate(Constants.MENU_RIGHT_POSITION[0],
				Constants.MENU_RIGHT_POSITION[1],
				Constants.MENU_RIGHT_POSITION[2]);
		sphere(Constants.MENU_ZONE_SIZE);
		popMatrix();

		stroke(0);
	}

	boolean controllerIsInMenuZone() {
		return Math.pow(leftHand[0] - Constants.MENU_LEFT_POSITION[0], 2)
				+ Math.pow(leftHand[1] - Constants.MENU_LEFT_POSITION[1], 2)
				+ Math.pow(leftHand[2] - Constants.MENU_LEFT_POSITION[2], 2) < 4f * Constants.MENU_ZONE_SIZE
				&& Math.pow(rightHand[0] - Constants.MENU_RIGHT_POSITION[0], 2)
						+ Math.pow(rightHand[1]
								- Constants.MENU_RIGHT_POSITION[1], 2)
						+ Math.pow(rightHand[2]
								- Constants.MENU_RIGHT_POSITION[2], 2) < 4f * Constants.MENU_ZONE_SIZE;
	}

	void setUpControllerPosition() {
		controllerLocation[0] = (float) (0.5 * leftHand[0] + 0.5 * rightHand[0]);
		controllerLocation[1] = (float) (0.5 * leftHand[1] + 0.5 * rightHand[1]);
		controllerLocation[2] = (float) (0.5 * leftHand[2] + 0.5 * rightHand[2]);
	}

	void oscEvent(OscMessage msg) {
		// msg.print();
		if (msg.checkAddrPattern("/new_user") && msg.checkTypetag("i")) {
			// New user
			System.out.println("New user found, id=" + msg.get(0).intValue()
					+ ", calibration started");
			if (userTracked != -1) {
				System.out.println("Already has a user to track");
			}
		} else if (msg.checkAddrPattern("/new_skel") && msg.checkTypetag("i")) {
			int id = msg.get(0).intValue();
			if (userTracked == -1) {
				userTracked = id;
				System.out.println("Calibration for user " + id
						+ " finished, tracking");
			}
		} else if (msg.checkAddrPattern("/joint") && msg.checkTypetag("sifff")) {
			// Getting coordinates of hands and user id.
			String bodyPart = msg.get(0).stringValue();
			int userId = msg.get(1).intValue();
			if (userId == userTracked) {
				if (bodyPart.equals("r_hand")) {
					leftHand[0] = msg.get(2).floatValue();
					leftHand[1] = msg.get(3).floatValue();
					leftHand[2] = msg.get(4).floatValue();
					setUpControllerPosition();
				}
				if (bodyPart.equals("l_hand")) {
					rightHand[0] = msg.get(2).floatValue();
					rightHand[1] = msg.get(3).floatValue();
					rightHand[2] = msg.get(4).floatValue();
					setUpControllerPosition();
				}

			}
		} else if (msg.checkAddrPattern("/lost_user") && msg.checkTypetag("i")) {
			int id = msg.get(0).intValue();
			if (id == userTracked) {
				System.out.println("Lost user, stop tracking");
				userTracked = -1;
				for (int i = 0; i < 3; i++) {
					leftHand[i] = 0;
					rightHand[i] = 0;
					controllerLocation[i] = 0;
					controlGroup.safeDrone();
					commandsListener.initialize();
				}
			}
		}
	}

	public void keyPressed() {
		if (key == CODED) {
			if (keyCode == SHIFT) {
				controlGroup.takeOff();
			} else if (keyCode == CONTROL) {
				controlGroup.landing();
			} else if (keyCode == ESC) {
				controlGroup.safeDrone();
			}

			if (keyCode == UP) {
				rotX += Constants.ROTATION_DELTA;
			} else if (keyCode == DOWN) {
				rotX -= Constants.ROTATION_DELTA;
			} else if (keyCode == LEFT) {
				rotY += Constants.ROTATION_DELTA;
			} else if (keyCode == RIGHT) {
				rotY -= Constants.ROTATION_DELTA;
			}
		} else if (key == 'x') {
			rotZ += Constants.ROTATION_DELTA;
		} else if (key == 'z') {
			rotZ -= Constants.ROTATION_DELTA;
		} else if (key == 'w') {
			controllerLocation[2] -= 1;
		} else if (key == 's') {
			controllerLocation[2] += 1;
		} else if (key == 'a') {
			controllerLocation[0] -= 1;
		} else if (key == 'd') {
			controllerLocation[0] += 1;
		} else if (key == 'i') {
			controllerLocation[1] -= 1;
		} else if (key == 'k') {
			controllerLocation[1] += 1;
		} else if (key == 'j') {
			rightHand[2] -= 1;
			leftHand[2] += 1;
		} else if (key == 'l') {
			rightHand[2] += 1;
			leftHand[2] -= 1;
		} else if (key == 'o'){
			leftHand[0]=-Constants.SAFE_ZONE_SIZE;
			rightHand[0]=Constants.SAFE_ZONE_SIZE;
			leftHand[1]=0;
			rightHand[1]=0;
			leftHand[2]=0;
			rightHand[2]=0;
			controllerLocation[0]=0;
			controllerLocation[1]=0;
			controllerLocation[2]=0;
		}

	}

	boolean controllerIsInSafeZone() {
		return controllerLocation[0] * controllerLocation[0]
				+ controllerLocation[1] * controllerLocation[1]
				+ controllerLocation[2] * controllerLocation[2] < 5 * Constants.SAFE_ZONE_SIZE;
	}

}
