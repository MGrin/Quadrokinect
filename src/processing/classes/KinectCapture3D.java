package processing.classes;

import processing.classes.Axis;
import processing.core.PApplet;
import processing.core.PConstants;
import utils.Calculus;
import utils.Constants;

public class KinectCapture3D {

	PApplet parent;

	float[] leftHand = new float[] { -Constants.SAFE_ZONE_SIZE, 0, 0 };
	float[] rightHand = new float[] { Constants.SAFE_ZONE_SIZE, 0, 0 };
	float[] controllerLocation = new float[] { 0, 0, 0 };

	Axis axis;
	float rotX;
	float rotY;
	float rotZ;

	public KinectCapture3D(PApplet p) {
		parent = p;
	}

	public void setup() {
		parent.size(700, 700, PConstants.P3D);
		parent.background(0, 0, 0);

		axis = new Axis(parent);
	}

	public void display() {
		parent.background(0, 0, 0);

		parent.rotateX(rotX);
		parent.rotateY(rotY);
		parent.rotateZ(rotZ);

		drawController();
		drawScene();
		drawSafeZone();
		// drawPauseZone();
	}

	void drawController() {
		parent.noStroke();
		parent.fill(255, 255, 0);
		parent.lights();
		parent.pushMatrix();
		parent.translate((int) controllerLocation[0],
				(int) controllerLocation[1], (int) controllerLocation[2]);
		parent.sphere(Constants.CONTROLLER_SIZE);
		parent.translate((int) leftHand[0], (int) leftHand[1],
				(int) leftHand[2]);
		parent.sphere(Constants.HAND_SIZE);
		parent.translate((int) (-leftHand[0] + rightHand[0]),
				(int) (-leftHand[1] + rightHand[1]),
				(int) (-leftHand[2] + rightHand[2]));
		parent.sphere(Constants.HAND_SIZE);
		parent.popMatrix();
		parent.fill(0);
	}

	void drawScene() {
		axis.display(true);
		parent.fill(0, 0, 0, 0);
		parent.stroke(255, 255, 255);
		parent.box(50);
		parent.stroke(0);
		parent.fill(0);

		drawDistanceBetweenHands();
	}

	void drawSafeZone() {
		parent.pushMatrix();
		parent.fill(0, 0, 0, 0);
		if (controllerIsInSafeZone())
			parent.stroke(0, 255, 255);
		else
			parent.stroke(255, 255, 0);
		parent.strokeWeight(3);

		for (int i = 0; i < Constants.SAFE_ZONE_STEP; i++) {
			parent.ellipse(0, 0, Constants.SAFE_ZONE_SIZE,
					Constants.SAFE_ZONE_SIZE);
			parent.rotateX(Constants.SAFE_ZONE_DELTA);
		}

		for (int i = 0; i < Constants.SAFE_ZONE_STEP; i++) {
			parent.ellipse(0, 0, Constants.SAFE_ZONE_SIZE,
					Constants.SAFE_ZONE_SIZE);
			parent.rotateY(Constants.SAFE_ZONE_DELTA);
		}

		for (int i = 0; i < Constants.SAFE_ZONE_STEP; i++) {
			parent.ellipse(0, 0, Constants.SAFE_ZONE_SIZE,
					Constants.SAFE_ZONE_SIZE);
			parent.rotateZ(Constants.SAFE_ZONE_DELTA);
		}

		parent.stroke(0);
		parent.popMatrix();
	}

	void drawDistanceBetweenHands() {
		parent.stroke(255);
		parent.pushMatrix();
		parent.translate(-25 + Calculus.getDistance(leftHand, rightHand) / 2,
				-22, -22);
		parent.fill(150, 150, 255);
		parent.box(Calculus.getDistance(leftHand, rightHand), 5, 5);
		parent.popMatrix();
	}

	/*
	 * void drawPauseZone() { parent.fill(0, 0, 0, 0); if
	 * (!controllerIsInMenuZone()) parent.stroke(255, 255, 255, 100); else
	 * parent.stroke(255, 0, 0, 100); parent.strokeWeight(2);
	 * 
	 * parent.pushMatrix(); parent.translate(Constants.MENU_LEFT_POSITION[0],
	 * Constants.MENU_LEFT_POSITION[1], Constants.MENU_LEFT_POSITION[2]);
	 * parent.sphere(Constants.MENU_ZONE_SIZE); parent.popMatrix();
	 * 
	 * parent.pushMatrix(); parent.translate(Constants.MENU_RIGHT_POSITION[0],
	 * Constants.MENU_RIGHT_POSITION[1], Constants.MENU_RIGHT_POSITION[2]);
	 * parent.sphere(Constants.MENU_ZONE_SIZE); parent.popMatrix();
	 * 
	 * parent.stroke(0); }
	 */

	/*
	 * boolean controllerIsInMenuZone() { return Math.pow(leftHand[0] -
	 * Constants.MENU_LEFT_POSITION[0], 2) + Math.pow(leftHand[1] -
	 * Constants.MENU_LEFT_POSITION[1], 2) + Math.pow(leftHand[2] -
	 * Constants.MENU_LEFT_POSITION[2], 2) < 4f * Constants.MENU_ZONE_SIZE &&
	 * Math.pow(rightHand[0] - Constants.MENU_RIGHT_POSITION[0], 2) +
	 * Math.pow(rightHand[1] - Constants.MENU_RIGHT_POSITION[1], 2) +
	 * Math.pow(rightHand[2] - Constants.MENU_RIGHT_POSITION[2], 2) < 4f *
	 * Constants.MENU_ZONE_SIZE; }
	 */

	void setUpControllerPosition() {
		controllerLocation[0] = (float) (0.5 * leftHand[0] + 0.5 * rightHand[0]);
		controllerLocation[1] = (float) (0.5 * leftHand[1] + 0.5 * rightHand[1]);
		controllerLocation[2] = (float) (0.5 * leftHand[2] + 0.5 * rightHand[2]);
	}

	boolean controllerIsInSafeZone() {
		return controllerLocation[0] * controllerLocation[0]
				+ controllerLocation[1] * controllerLocation[1]
				+ controllerLocation[2] * controllerLocation[2] < 5 * Constants.SAFE_ZONE_SIZE;
	}

	public void setLeftHand(float x, float y, float z) {
		leftHand[0] = x;
		leftHand[1] = y;
		leftHand[2] = z;
		setUpControllerPosition();
	}

	public void setRightHand(float x, float y, float z) {
		rightHand[0] = x;
		rightHand[1] = y;
		rightHand[2] = z;
		setUpControllerPosition();
	}

	public float[] getLeftHand() {
		return leftHand;
	}

	public float[] getControllerLocation() {
		return controllerLocation;
	}

	public float[] getRightHand() {
		return rightHand;
	}

	public void resetController() {
		leftHand = new float[] { -Constants.SAFE_ZONE_SIZE, 0, 0 };
		rightHand = new float[] { Constants.SAFE_ZONE_SIZE, 0, 0 };
		controllerLocation = new float[] { 0, 0, 0 };
	}

}
