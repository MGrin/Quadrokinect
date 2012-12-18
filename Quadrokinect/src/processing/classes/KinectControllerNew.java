package processing.classes;

import com.shigeodayo.ardrone.ARDrone;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;

public class KinectControllerNew implements ControllerInterface {

	PApplet parent;
	ARDrone drone;
	
	float[] leftHand = new float[] {
			-KinectControllerNewConstants.defaultHandPosition, 0, 0 };
	float[] rightHand = new float[] {
			KinectControllerNewConstants.defaultHandPosition, 0, 0 };

	float[] leftHandSafeZone = new float[3];
	float[] rightHandSafeZone = new float[3];

	boolean safeZoneCalibrated = false;
	Timer calibrationTimer;
	int calibrationTime = 30;

	public KinectControllerNew(PApplet p) {
		parent = p;
	}

	@Override
	public void setup() {
		parent.size(700, 700, PConstants.P3D);
		parent.background(0);
		parent.textFont(parent.createFont(PFont.list()[0], 30));
		calibrationTimer = new Timer(calibrationTime * 1000, parent);
		calibrationTimer.start();
	}

	@Override
	public void display() {
		parent.background(0);
		parent.translate(350, 350, -300);
		drawBackgroundVideo();
		parent.lights();
		drawHands();
		if (!safeZoneCalibrated) {
			calibrateZone();
		} else {
			drawSafeZones();
		}
	}

	private void drawBackgroundVideo() {
		if (drone != null) {
			PImage im = drone.getVideoImage(false);
			parent.translate(0, 0, 300);
			if (im != null) {
				parent.imageMode(PImage.CENTER);
				im.resize(700, 400);
				parent.image(im, 0, 0);
			}
			parent.stroke(255);
			parent.fill(255);
			parent.text("Battery: " + drone.getBatteryPercentage() + "%", -350,
					-250);
			parent.translate(0, 0, -300);
		}

	}

	private void drawHands() {
		parent.noStroke();
		parent.fill(255, 100, 0);
		parent.pushMatrix();
		{
			parent.translate(leftHand[0], leftHand[1], leftHand[2]);
			parent.sphere(KinectControllerNewConstants.handSize);
			parent.translate(-leftHand[0] + rightHand[0], -leftHand[1]
					+ rightHand[1], -leftHand[2] + rightHand[2]);
			parent.sphere(KinectControllerNewConstants.handSize);
		}
		parent.popMatrix();
		parent.fill(0);
	}

	private void calibrateZone() {
		if (!calibrationTimer.isFinished()) {
			parent.pushMatrix();
			{
				parent.fill(255);
				parent.textFont(parent.createFont(PFont.list()[1], 100));
				int passedTime = calibrationTime
						- (parent.millis() - calibrationTimer.savedTime) / 1000;
				parent.text("" + passedTime, -60, -100);
				parent.fill(0);
			}
			parent.popMatrix();
		} else {
			safeZoneCalibrated = true;
			leftHandSafeZone = leftHand.clone();
			rightHandSafeZone = rightHand.clone();
		}
	}

	private void drawSafeZones() {
		if (leftHandIsInSafeZone()) {
			parent.fill(0, 255, 255, 150);
		} else {
			parent.fill(180, 180, 180, 120);
		}

		parent.pushMatrix();
		{
			parent.translate(leftHandSafeZone[0], leftHandSafeZone[1],
					leftHandSafeZone[2]);
			parent.sphere(KinectControllerNewConstants.safeZoneSize);
		}
		parent.noStroke();
		parent.popMatrix();
		if (rightHandIsInSafeZone()) {
			parent.fill(0, 255, 255, 150);
		} else {
			parent.fill(180, 180, 180, 120);
		}

		parent.pushMatrix();
		{
			parent.translate(rightHandSafeZone[0], rightHandSafeZone[1],
					rightHandSafeZone[2]);
			parent.sphere(KinectControllerNewConstants.safeZoneSize);
		}
		parent.popMatrix();
	}

	private boolean leftHandIsInSafeZone() {
		return (leftHand[0] - leftHandSafeZone[0])
				* (leftHand[0] - leftHandSafeZone[0])
				+ (leftHand[1] - leftHandSafeZone[1])
				* (leftHand[1] - leftHandSafeZone[1])
				+ (leftHand[2] - leftHandSafeZone[2])
				* (leftHand[2] - leftHandSafeZone[2]) < KinectControllerNewConstants.safeZoneSize
				* KinectControllerNewConstants.safeZoneSize;
	}

	private boolean rightHandIsInSafeZone() {
		return (rightHand[0] - rightHandSafeZone[0])
				* (rightHand[0] - rightHandSafeZone[0])
				+ (rightHand[1] - rightHandSafeZone[1])
				* (rightHand[1] - rightHandSafeZone[1])
				+ (rightHand[2] - rightHandSafeZone[2])
				* (rightHand[2] - rightHandSafeZone[2]) < KinectControllerNewConstants.safeZoneSize
				* KinectControllerNewConstants.safeZoneSize;
	}

	@Override
	public void updateLeftHand(float x, float y, float z) {
		leftHand[0] = x;
		leftHand[1] = y;
		leftHand[2] = z;
	}

	@Override
	public void updateRightHand(float x, float y, float z) {
		rightHand[0] = x;
		rightHand[1] = y;
		rightHand[2] = z;
	}

	@Override
	public float[] getLeftHand() {
		return leftHand;
	}

	@Override
	public float[] getRightHand() {
		return rightHand;
	}

	@Override
	public void resetController() {
		leftHand[0] = -KinectControllerNewConstants.defaultHandPosition;
		rightHand[0] = KinectControllerNewConstants.defaultHandPosition;
		for (int i = 1; i < 3; i++) {
			leftHand[i] = 0;
			rightHand[i] = 0;
		}
	}

	public float[] getLeftHandSafeZone() {
		return leftHandSafeZone;
	}

	public float[] getRightHandSafeZone() {
		return rightHandSafeZone;
	}

	public boolean isCalibrated() {
		return safeZoneCalibrated;
	}
	
	public void setDrone(ARDrone drone){
		this.drone = drone;
	}

}
