package ardrone;

import java.util.HashMap;
import java.util.LinkedList;

import com.shigeodayo.ardrone.ARDrone;

import utils.Calculus;
import utils.Constants;
import utils.Position;

public class ArdroneGroup {

	private int id;

	private boolean connected = false;

	private HashMap<Integer, ARDrone> ardrones = new HashMap<Integer, ARDrone>();
	private HashMap<Integer, Position> positions = new HashMap<Integer, Position>();
	private LinkedList<Float> altitudeErrors = new LinkedList<Float>();

	private boolean inAir = false;
	private boolean stopped = true;

	public ArdroneGroup(int groupID) {
		id = groupID;
	}

	public int getID() {
		return id;
	}

	public ARDrone getARDrone(int id) {
		return ardrones.get(id);
	}

	public Position getPosition(int id) {
		return positions.get(id);
	}

	public void setPosition(int id, Position pos) {
		positions.put(id, pos);
	}

	public void addArdrone(ARDrone a, int id) {
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
					ardrones.get(i).connectNav(Constants.NAV_PORT + i);
					System.out.println("Connecting video for "
							+ Constants.IP_ADDRESS_MASK + i);
					ardrones.get(i).connectVideo();
					ardrones.get(i).start();
					System.out.println("Drone conected: "+i);
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
			for (ARDrone a : ardrones.values()) {
				a.stop();
				a.landing();
			}
		inAir = false;
		stopped = true;
	}

	public void stop() {
		// System.out.println("Stop");
		if (connected)
			for (ARDrone a : ardrones.values()) {
				a.stop();
			}
		stopped = true;
	}

	public void takeOff() {
		// System.out.println("TakeOff");
		if (connected) {
			for (ARDrone a : ardrones.values()) {
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
			for (ARDrone a : ardrones.values()) {
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
		for (ARDrone drone : ardrones.values()) {
			altitudeErrors.addLast(drone.getAltitude());
		}
	}

	public float getAltitudeError(int i) {
		return altitudeErrors.get(i);
	}

	public void toggleCamera() {
		if (connected) {
			for (ARDrone drone : ardrones.values()) {
				drone.toggleCamera();
			}
		}
	}

	public void move3d(int x, int y, int z, int ang) {
		System.out.println("x=" + x + " y=" + y + " z=" + z + " ang=" + ang);
		if (connected) {
			for (ARDrone drone : ardrones.values()) {
				drone.move3D(x, y, z, ang);
			}
		}
	}

	public void setMaxAltitude(int i) {
		if (connected) {
			for (ARDrone drone : ardrones.values()) {
				drone.setMaxAltitude(i);
			}
		}
	}
}
