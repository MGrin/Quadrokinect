package ardrone;

import java.util.HashMap;

import utils.Constants;
import utils.Position;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

public class ArdroneGroup {

	private int id;

	private boolean connected = true;

	private HashMap<Integer, ARDroneForP5> ardrones = new HashMap<Integer, ARDroneForP5>();
	private HashMap<Integer, Position> positions = new HashMap<Integer, Position>();

	private boolean inAir = false;
	private boolean stopped = true;
	private boolean nav = false;
	private boolean videoON = false;
	private byte videoType = Constants.FRONT_CAMERA;

	private float altitudeError = 0;

	public ArdroneGroup(int groupID) {
		id = groupID;
	}

	public int getID() {
		return id;
	}

	public ARDroneForP5 getARDrone(int id) {
		return ardrones.get(id);
	}

	public Position getPosition(int id) {
		return positions.get(id);
	}

	public void setPosition(int id, Position pos) {
		positions.put(id, pos);
	}

	public void addArdrone(ARDroneForP5 a, int id) {
		if (!ardrones.containsKey(id)) {
			ardrones.put(id, a);
			positions.put(id, new Position());
		}
	}

	public boolean connect() {
		boolean hasConnection = false;
		if (connected) {
			for (int i : ardrones.keySet()) {
				hasConnection = ardrones.get(i).connect(
						Constants.STARTING_PORT + i);
				if (hasConnection) {
					if (nav)
						ardrones.get(i).connectNav(Constants.NAV_PORT + i);
					if (videoON) {
						ardrones.get(i).connectVideo(Constants.VIDEO_PORT + i);
						if (videoType == Constants.FRONT_CAMERA)
							ardrones.get(i).setHorizontalCamera();
						else if (videoType == Constants.BOTTOM_CAMERA)
							ardrones.get(i).setVerticalCamera();
					}
					ardrones.get(i).start();
				} else {
					System.out.println("No connection with ardrone on "
							+ Constants.IP_ADDRESS_MASK + i);
				}
			}
		}
		return hasConnection;
	}

	public void safeDrone() {
		// System.out.println("SafeDrone");
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.stop();
				a.landing();
			}
		inAir = false;
		stopped = true;
	}

	public void stop() {
		// System.out.println("Stop");
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.stop();
			}
		stopped = true;
	}

	public void up(int speed) {
		// System.out.println("UP " + speed);
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.up(speed);
			}
		stopped = false;
	}

	public void down(int speed) {
		// System.out.println("Down " + speed);
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.down(speed);
			}
		stopped = false;
	}

	public void goRight(int speed) {
		// System.out.println("GoRight " + speed);
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.goRight(speed);
			}
		stopped = false;
	}

	public void goLeft(int speed) {
		// System.out.println("GoLeft " + speed);
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.goLeft(speed);
			}
		stopped = false;
	}

	public void forward(int speed) {
		// System.out.println("Forward " + speed);
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.forward(speed);
			}
		stopped = false;
	}

	public void backward(int speed) {
		// System.out.println("Backward " + speed);
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.backward(speed);
			}
		stopped = false;
	}

	public void spinLeft(int speed) {
		// System.out.println("SpinLeft " + speed);
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.spinLeft(speed);
			}
	}

	public void spinRight(int speed) {
		// System.out.println("SpinRight " + speed);
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.spinRight(speed);
			}
	}

	public void takeOff() {
		// System.out.println("TakeOff");
		if (connected) {
			int arId = ardrones.keySet().iterator().next();
			for (ARDroneForP5 a : ardrones.values()) {
				a.takeOff();
				// System.out.println(Constants.IP_ADDRESS_MASK + arId+
				// ": takeOff");
			}
		}
		inAir = true;
		stopped = true;
	}

	public void landing() {
		// System.out.println("Landing");
		if (connected) {
			int arId = ardrones.keySet().iterator().next();
			for (ARDroneForP5 a : ardrones.values()) {
				a.landing();
				// System.out.println(Constants.IP_ADDRESS_MASK + arId+
				// ": landing");
			}
		}
		inAir = false;
		stopped = true;
	}

	public boolean inAir() {
		return inAir;
	}

	public boolean isStopped() {
		return stopped;
	}

	public int getArdroneNumber() {
		return ardrones.size();
	}

	public void setNAV(boolean b) {
		nav = b;
	}

	public void setVideo(boolean b, byte type) {
		videoON = b;
		videoType = type;
	}

	public boolean isConnected() {
		return connected;
	}

	public void updatePositions() {
		for (int i = 0; i < getArdroneNumber(); i++) {
			float[] speed = getARDrone(i).getVelocity();
			float alt = getARDrone(i).getAltitude();
			Position p = positions.get(i);
			p.setX(p.getX() + speed[0] / 100);
			p.setY(p.getY() + speed[1] / 100);
			p.setZ(alt/100);
		}
	}

	public void calculateAltitudeError() {
		altitudeError = ardrones.get(0).getAltitude();
	}

	public float getAltitudeError(int i) {
		return altitudeError;
	}
}
