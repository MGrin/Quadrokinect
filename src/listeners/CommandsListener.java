package listeners;

import ardrone.ArdroneGroup;

import processing.classes.Timer;
import processing.core.PApplet;
import utils.Calculus;
import utils.Constants;

public class CommandsListener {

	private ArdroneGroup controlGroup;

	private PApplet parent;

	Timer clackTimer;
	int nbOfClacks = 0;

	float[] speeds = new float[] { 0, 0, 0, 0, 0, 0 };
	int rotation = 0;

	public CommandsListener(ArdroneGroup controlGroup, PApplet p) {

		this.parent = p;
		this.controlGroup = controlGroup;

		clackTimer = new Timer(2000, parent);
		System.out.println("CommandListener created");
	}

	public void hand(float[] controllerLocation, float[] leftHand,
			float[] rightHand) {
		if (Calculus.getDistance(leftHand, rightHand) < Constants.MIN_DISTANCE_BETWEEN_HANDS) {
			performClack();
		} else {
			if (controlGroup.inAir()) {
				if (!controllerIsInSafeZone(controllerLocation)) {
					performDirectionChange(controllerLocation);
				} else {
					float angle = Calculus.calculateAngleBetween(rightHand[2]);
					if (!controlGroup.isStopped()) {
						controlGroup.stop();
						for (int i = 0; i < 6; i++) {
							speeds[i] = 0;
						}
					}
					if (Math.abs(angle) >= Constants.SAFE_ANGLE
							&& (int) (angle * Constants.SPIN_SCALE) != rotation) {
						rotation = (int) (angle * Constants.SPIN_SCALE);
						performRotationOfEachDrone(rightHand);
					}
				}
			}
		}
	}

	public void performClack() {
		if (clackTimer.isFinished()) {
			clackTimer.start();
			if (nbOfClacks > 0) {
				if (controlGroup.inAir()) {
					controlGroup.landing();
				} else if (!controlGroup.inAir()) {
					controlGroup.takeOff();
				}
			}
			nbOfClacks++;
		}
	}

	public void performDirectionChange(float[] controllerLocation) {
		if (controllerLocation[0] > Constants.SAFE_ZONE_SIZE / 2
				&& speeds[0] != (int) ((Math.abs(controllerLocation[0]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE)) {
			controlGroup
					.goRight((int) ((Math.abs(controllerLocation[0]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE));
			speeds[0] = (int) ((Math.abs(controllerLocation[0]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE);
			for (int i = 1; i < speeds.length; i++) {
				speeds[i] = 0;
			}
		}
		if (controllerLocation[0] < -Constants.SAFE_ZONE_SIZE / 2
				&& speeds[1] != (int) ((Math.abs(controllerLocation[0]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE)) {
			controlGroup
					.goLeft((int) ((Math.abs(controllerLocation[0]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE));
			for (int i = 0; i < speeds.length; i++)
				speeds[i] = 0;
			speeds[1] = (int) ((Math.abs(controllerLocation[0]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE);
		}
		if (controllerLocation[1] > Constants.SAFE_ZONE_SIZE / 2
				&& speeds[2] != (int) ((Math.abs(controllerLocation[1]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE)) {
			controlGroup
					.down((int) ((Math.abs(controllerLocation[1]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE));
			for (int i = 0; i < speeds.length; i++)
				speeds[i] = 0;
			speeds[2] = (int) ((Math.abs(controllerLocation[1]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE);
		}
		if (controllerLocation[1] < -Constants.SAFE_ZONE_SIZE / 2
				&& speeds[3] != (int) ((Math.abs(controllerLocation[1]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE)) {
			controlGroup
					.up((int) ((Math.abs(controllerLocation[1]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE));
			for (int i = 0; i < speeds.length; i++)
				speeds[i] = 0;
			speeds[3] = (int) ((Math.abs(controllerLocation[1]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE);
		}
		if (controllerLocation[2] > Constants.SAFE_ZONE_SIZE / 2
				&& speeds[4] != (int) ((Math.abs(controllerLocation[2]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE)) {
			controlGroup
					.backward((int) ((Math.abs(controllerLocation[2]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE));
			for (int i = 0; i < speeds.length; i++)
				speeds[i] = 0;
			speeds[4] = (int) ((Math.abs(controllerLocation[2]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE);
		}
		if (controllerLocation[2] < -Constants.SAFE_ZONE_SIZE / 2
				&& speeds[5] != (int) ((Math.abs(controllerLocation[2]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE)) {
			controlGroup
					.forward((int) ((Math.abs(controllerLocation[2]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE));
			for (int i = 0; i < speeds.length; i++)
				speeds[i] = 0;
			speeds[5] = (int) ((Math.abs(controllerLocation[2]) - Constants.SAFE_ZONE_SIZE / 2) * Constants.SPEED_SCALE);
		}
	}

	public void performRotationOfEachDrone(float[] rightHand) {
		if (rightHand[2] > 0) {
			controlGroup.spinRight(rotation);
		} else {
			controlGroup.spinLeft(rotation);
		}
	}

	boolean controllerIsInSafeZone(float[] controllerLocation) {
		return controllerLocation[0] * controllerLocation[0]
				+ controllerLocation[1] * controllerLocation[1]
				+ controllerLocation[2] * controllerLocation[2] < 5 * Constants.SAFE_ZONE_SIZE;
	}

	public void initialize() {
		nbOfClacks = 0;
	}

}
