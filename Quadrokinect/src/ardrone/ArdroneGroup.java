package ardrone;

import java.util.HashMap;
import java.util.LinkedList;

import utils.Calculus;
import utils.Constants;
import utils.Position;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

public class ArdroneGroup {

	private int id;

	private boolean connected = true;

	private HashMap<Integer, ARDroneForP5> ardrones = new HashMap<Integer, ARDroneForP5>();
	private HashMap<Integer, Position> positions = new HashMap<Integer, Position>();
	private LinkedList<Float> altitudeErrors = new LinkedList<Float>();

	private boolean inAir = false;
	private boolean stopped = true;
	private boolean nav = false;
	private boolean videoON = false;

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
			setPosition(id, new Position());
			a.setMaxAltitude(6000);
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
						System.out.println("Connecting video for "
								+ Constants.IP_ADDRESS_MASK + i);
						ardrones.get(i).connectVideo();
					}
					ardrones.get(i).start();
				} else {
					System.out.println("No connection with ardrone on "
							+ Constants.IP_ADDRESS_MASK + i);
				}
			}
			calculateAltitudeError();
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
		if (ardrones.size() > 0)
			return ardrones.size();
		else
			return 1;
	}

	public void setNAV(boolean b) {
		nav = b;
	}

	public void setVideo(boolean b) {
		videoON = b;
	}

	public boolean isConnected() {
		return connected;
	}

	public void updatePositions() {
		if (connected) {
			for (int i = 0; i < getArdroneNumber(); i++) {
				if (noCollision(i)) {
					float alt = getARDrone(i).getAltitude();
					Position p = positions.get(i);
					// TODO
					p.setZ(alt);
				}
			}
		}
	}

	private boolean noCollision(int i) {
		boolean rest = true;
		for (int n = i + 1; n < getArdroneNumber(); n++) {
			if (Calculus.getDistance(positions.get(i).getFloatArray(),
					positions.get(n).getFloatArray()) < Constants.MINIMAL_SAFE_DISTANCE) {
				return false;
			}
		}
		return rest;
	}

	public void calculateAltitudeError() {
		for (ARDroneForP5 drone : ardrones.values()) {
			altitudeErrors.addLast(drone.getAltitude());
		}
	}

	public float getAltitudeError(int i) {
		return altitudeErrors.get(i);
	}

	public void toggleCamera() {
		if (connected) {
			for (ARDroneForP5 drone : ardrones.values()) {
				drone.toggleCamera();
			}
		}
	}

	public void move3d(int x, int y, int z, int ang) {
		System.out.println("x="+x+" y="+y+" z="+z+" ang="+ang);
		if(connected){
			for (ARDroneForP5 drone : ardrones.values()) {
				drone.move3D(x, y, z, ang);
			}
		}
	}

	public void setMaxAltitude(int i) {
		if(connected){
			for (ARDroneForP5 drone : ardrones.values()) {
				drone.setMaxAltitude(i);
			}
		}
	}
}
