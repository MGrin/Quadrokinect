package listeners;

import processing.classes.KinectControllerNew;
import processing.classes.KinectControllerNewConstants;
import ardrone.ArdroneGroup;

public class CommandsListenerNew implements GeneralCommandListener {

	private ArdroneGroup group;
	private KinectControllerNew controller;
	private int[] speeds = { 0, 0, 0, 0 };

	public CommandsListenerNew(ArdroneGroup group,
			KinectControllerNew controller) {
		this.group = group;
		this.controller = controller;
	}

	@Override
	public void hand(float[] leftHand, float[] rightHand) {
		float[] leftHandSafeZone = controller.getLeftHandSafeZone();
		float[] rightHandSafeZone = controller.getRightHandSafeZone();

		if (delta(leftHand, leftHandSafeZone)[1] > 0
				&& Math.abs(delta(leftHand, leftHandSafeZone)[1]) > Math
						.abs(delta(leftHand, leftHandSafeZone)[0])
				&& !leftHandInSafeZone(leftHand, leftHandSafeZone)) {
			// landing
			if (group.inAir())
				group.landing();
		} else if (delta(leftHand, leftHandSafeZone)[1] < 0
				&& Math.abs(delta(leftHand, leftHandSafeZone)[1]) > Math
						.abs(delta(leftHand, leftHandSafeZone)[0])
				&& !leftHandInSafeZone(leftHand, leftHandSafeZone)) {
			// takeOff
			if (!group.inAir())
				group.takeOff();
		} else if (leftHandInSafeZone(leftHand, leftHandSafeZone)
				&& rightHandInSafeZone(rightHand, rightHandSafeZone)) {
			if (speeds[0] != 0 || speeds[1] != 0 || speeds[2] != 0)
				//group.move3d(-speeds[0], -speeds[1], -speeds[1], 0);
			group.stop();
			for (int i = 0; i < 4; i++) {
				speeds[i] = 0;
			}
		} else {
			int speedX = 0;
			int speedY = 0;
			int speedZ = 0;
			int speedAng = 0;
			if (!leftHandInSafeZone(leftHand, leftHandSafeZone)) {
				speedAng = -transformToAngle(leftHand[0] - leftHandSafeZone[0]);
			}

			if (!rightHandInSafeZone(rightHand, rightHandSafeZone)) {
				speedX = -transformToSpeed(rightHand[2] - rightHandSafeZone[2]);
				speedY = -transformToSpeed(rightHand[0] - rightHandSafeZone[0]);
				speedZ = transformToSpeed(rightHand[1] - rightHandSafeZone[1]);
			}

			if (speeds[0] != speedX || speeds[1] != speedY
					|| speeds[2] != speedZ || speeds[3] != speedAng) {
				System.out.println(speedX % 10
						* KinectControllerNewConstants.speedScale + " "
						+ speedY % 10 * KinectControllerNewConstants.speedScale
						+ " " + speedZ % 10
						* KinectControllerNewConstants.speedScale + " "
						+ speedAng % 10
						* KinectControllerNewConstants.speedScale);
				speeds[0] = speedX % 10
						* KinectControllerNewConstants.speedScale;
				speeds[1] = speedY % 10
						* KinectControllerNewConstants.speedScale;
				speeds[2] = speedZ % 10
						* KinectControllerNewConstants.speedScale;
				speeds[3] = speedAng % 10
						* KinectControllerNewConstants.speedScale;
				group.move3d(speeds[0], speeds[1], speeds[2], speeds[3]);
			}
		}
	}

	private float[] delta(float[] a, float[] b) {
		float[] res = new float[3];
		for (int i = 0; i < 3; i++) {
			res[i] = a[i] - b[i];
		}
		return res;
	}

	private int transformToAngle(float f) {
		int res = (int) f;
		res = res / 10;
		return res;
	}

	private int transformToSpeed(float f) {
		int res = (int) f;
		res = res / 10;
		return res;
	}

	private boolean leftHandInSafeZone(float[] leftHand,
			float[] leftHandSafeZone) {
		return (leftHand[0] - leftHandSafeZone[0])
				* (leftHand[0] - leftHandSafeZone[0])
				+ (leftHand[1] - leftHandSafeZone[1])
				* (leftHand[1] - leftHandSafeZone[1])
				+ (leftHand[2] - leftHandSafeZone[2])
				* (leftHand[2] - leftHandSafeZone[2]) < KinectControllerNewConstants.safeZoneSize
				* KinectControllerNewConstants.safeZoneSize;
	}

	private boolean rightHandInSafeZone(float[] rightHand,
			float[] rightHandSafeZone) {
		return (rightHand[0] - rightHandSafeZone[0])
				* (rightHand[0] - rightHandSafeZone[0])
				+ (rightHand[1] - rightHandSafeZone[1])
				* (rightHand[1] - rightHandSafeZone[1])
				+ (rightHand[2] - rightHandSafeZone[2])
				* (rightHand[2] - rightHandSafeZone[2]) < KinectControllerNewConstants.safeZoneSize
				* KinectControllerNewConstants.safeZoneSize;
	}

	@Override
	public void performClack() {

	}

}
