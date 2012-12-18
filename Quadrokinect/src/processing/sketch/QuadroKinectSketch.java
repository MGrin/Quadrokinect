package processing.sketch;

import com.shigeodayo.ardrone.ARDrone;

import ardrone.ArdroneGroup;
import listeners.CommandsListenerNew;
import listeners.oneController.OscListener;
import processing.classes.KinectControllerNew;
import processing.core.PApplet;
import utils.Constants;
import utils.ControllerEnum;

public class QuadroKinectSketch extends PApplet {

	private static final long serialVersionUID = 1L;

	// KinectCapture3D controller;
	KinectControllerNew controller;
	OscListener oscListener;

	// CommandsListener commandsListener;
	CommandsListenerNew commandsListener;
	ArdroneGroup controlGroup;

	private int userTrackedID = -1;

	public void setup() {
		System.out.println("Quadrokinect setup");
		// controller = new KinectCapture3D(this);
		controller = new KinectControllerNew(this);
		controller.setup();
		// Listener of OSC messages, comming from OSCeleton
		oscListener = new OscListener(this);

		// Adding drones in this group
		addARDrones(controlGroup, 1);
		controlGroup.setMaxAltitude(10);
		controlGroup.connect();

		// Listen to commands, comming from kinect, and redirect them to the
		// drones group
		// commandsListener = new CommandsListener(controlGroup, this);
		commandsListener = new CommandsListenerNew(controlGroup, controller);
		controller.setDrone(controlGroup.getARDrone(0));
	}

	public void draw() {
		controller.display();
		controlGroup.updatePositions();
	}

	public int getUserTrackedID() {
		return userTrackedID;
	}

	public void setUserTrackedID(int id) {
		userTrackedID = id;
	}

	public void updateController(ControllerEnum hand, float x, float y, float z) {
		if (hand == ControllerEnum.LEFT_HAND) {
			controller.updateLeftHand(x, y, z);
		} else if (hand == ControllerEnum.RIGHT_HAND) {
			controller.updateRightHand(x, y, z);
		}
		if (controller.isCalibrated())
			commandsListener.hand(controller.getLeftHand(),
					controller.getRightHand());
	}

	public void resetController() {
		controller.resetController();
	}

	public void addARDrones(ArdroneGroup group, int nb) {
		for (int i = 3; i < nb + 3; i++) {
			ARDrone drone = new ARDrone(Constants.IP_ADDRESS_MASK + i);
			group.addArdrone(drone, i - 3);
		}
	}

	public ArdroneGroup getArdrones() {
		return controlGroup;
	}

	public QuadroKinectSketch() {
		controlGroup = new ArdroneGroup(0);
	}
}
