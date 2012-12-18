package listeners.oneController;

import listeners.GeneralCommandListener;
import ardrone.ArdroneGroup;

import processing.classes.Timer;
import processing.core.PApplet;
import utils.Calculus;
import utils.Constants;

public class CommandsListener implements GeneralCommandListener {

	private ArdroneGroup controlGroup;

	private PApplet parent;

	Timer clackTimer;
	int nbOfClacks = 0;

	float[] speeds = new float[] { 0, 0, 0, 0 };
	int rotation = 0;

	float[] controllerLocation = new float[3];

	public CommandsListener(ArdroneGroup controlGroup, PApplet p) {

		this.parent = p;
		this.controlGroup = controlGroup;

		clackTimer = new Timer(2000, parent);
		System.out.println("CommandListener created");
	}

	private void hand(float[] controllerLocation, float[] leftHand,
			float[] rightHand) {
		if (Calculus.getDistance(leftHand, rightHand) < Constants.MIN_DISTANCE_BETWEEN_HANDS) {
			performClack();
		} else {
			if (controlGroup.inAir()) {
				int speedX = 0;
				int speedY = 0;
				int speedZ = 0;
				int speedAng = 0;

				float angspeed = Calculus.calculateAngleBetween(rightHand[2]);

				if (!controllerIsInSafeZone(controllerLocation)) {
					speedX = (int) ((controllerLocation[2]) * Constants.SPEED_SCALE);
					speedY = (int) ((controllerLocation[0]) * Constants.SPEED_SCALE);
					speedZ = -(int) ((controllerLocation[1]) * Constants.SPEED_SCALE);
				} else {
					if (speeds[0] != 0 || speeds[1] != 0 || speeds[2] != 0
							|| speeds[3] != 0) {
						controlGroup.stop();
						for (int i = 0; i < 4; i++)
							speeds[i] = 0;
					}
					if (Math.abs(angspeed) >= Constants.SAFE_ANGLE) {
						speedAng = (int) (angspeed * Constants.SPIN_SCALE);
					}
				}

				if (!(speeds[0] == speedX && speeds[1] == speedY
						&& speeds[2] == speedZ && speeds[3] == speedAng)) {
					speeds[0] = speedX;
					speeds[1] = speedY;
					speeds[2] = speedZ;
					speeds[3] = speedAng;
					controlGroup.move3d(speedX, speedY, speedZ, speedAng);
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

	boolean controllerIsInSafeZone(float[] controllerLocation) {
		return controllerLocation[0] * controllerLocation[0]
				+ controllerLocation[1] * controllerLocation[1]
				+ controllerLocation[2] * controllerLocation[2] < 5 * Constants.SAFE_ZONE_SIZE;
	}

	public void initialize() {
		nbOfClacks = 0;
	}

	@Override
	public void hand(float[] leftHand, float[] rightHand) {
		hand(calculateMiddle(leftHand, rightHand), leftHand, rightHand);
	}

	private float[] calculateMiddle(float[] leftHand, float[] rightHand) {
		controllerLocation[0] = (float) (0.5 * leftHand[0] + 0.5 * rightHand[0]);
		controllerLocation[1] = (float) (0.5 * leftHand[1] + 0.5 * rightHand[1]);
		controllerLocation[2] = (float) (0.5 * leftHand[2] + 0.5 * rightHand[2]);
		return controllerLocation;
	}

}
