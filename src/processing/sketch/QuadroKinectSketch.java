package processing.sketch;

import gui.DronesPositionsSketchWindow;
import gui.VideoSketchWindow;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

import ardrone.ArdroneGroup;
import listeners.CommandsListener;
import listeners.OscListener;
import processing.classes.KinectCapture3D;
import processing.core.PApplet;
import utils.Constants;
import utils.ControllerEnum;

public class QuadroKinectSketch extends PApplet {

	private static final long serialVersionUID = 1L;

	KinectCapture3D controller;
	OscListener oscListener;

	CommandsListener commandsListener;

	ArdroneGroup controlGroup;

	VideoSketchWindow videoWindow;
	
	DronesPositionsSketchWindow positionsWindow;

	private int userTrackedID = -1;

	public void setup() {
		// Processing draw with controller, axes, some other stuff
		controller = new KinectCapture3D(this);
		controller.setup();
		// Listener of OSC messages, comming from OSCeleton
		oscListener = new OscListener(this);

		// Group of drones
		controlGroup = new ArdroneGroup(0);
		// Adding drones in this group
		addARDrones(controlGroup, 1);
		// Etablishement of control connection
		controlGroup.setNAV(true);
		// Etablishemend of video connection
		controlGroup.setVideo(true, Constants.FRONT_CAMERA);
		controlGroup.connect();

		// Listen to commands, comming from kinect, and redirect them to the
		// drones group
		commandsListener = new CommandsListener(controlGroup, this);

		/*videoWindow = new VideoSketchWindow();
		videoWindow.setDrone(controlGroup.getARDrone(0), 0);
		videoWindow.setVisible(true);*/
		
		positionsWindow = new DronesPositionsSketchWindow();
		positionsWindow.setDronesGroup(controlGroup);
		positionsWindow.setVisible(true);
		
		controlGroup.calculateAltitudeError();
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
			controller.setLeftHand(x, y, z);
		} else if (hand == ControllerEnum.RIGHT_HAND) {
			controller.setRightHand(x, y, z);
		}
		commandsListener.hand(controller.getControllerLocation(),
				controller.getLeftHand(), controller.getRightHand());
	}

	public void resetController() {
		controller.resetController();
	}

	public void addARDrones(ArdroneGroup group, int nb) {
		for (int i = 3; i < nb + 3; i++) {
			ARDroneForP5 drone = new ARDroneForP5(Constants.IP_ADDRESS_MASK + i);
			group.addArdrone(drone, i - 3);
		}
	}
}
