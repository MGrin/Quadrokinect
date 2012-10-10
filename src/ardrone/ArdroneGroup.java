package ardrone;

import java.util.HashMap;

import utils.Constants;

import com.shigeodayo.ardrone.ARDrone;
import com.shigeodayo.ardrone.processing.ARDroneForP5;

public class ArdroneGroup {

	private int id;

	private boolean connected = true;

	private HashMap<Integer, ARDroneForP5> ardrones = new HashMap<Integer, ARDroneForP5>();

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

	public void addArdrone(ARDroneForP5 a, int id) {
		if (!ardrones.containsKey(id))
			ardrones.put(id, a);
	}

	public boolean connect() {
		boolean res = true;
		if (connected) {
			for (int i : ardrones.keySet()) {
				ardrones.get(i).connect(Constants.STARTING_PORT + i);
				ardrones.get(i).connectNav(Constants.NAV_PORT + i);
				ardrones.get(i).connectVideo(Constants.VIDEO_PORT + i);
				ardrones.get(i).start();
			}
		}
		return res;
	}

	public void safeDrone() {
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.stop();
				a.landing();
			}
		inAir = false;
		stopped = true;
	}

	public void stop() {
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.stop();
			}
		stopped = true;
	}

	public void up(int speed) {
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.up(speed);
			}
		stopped = false;
	}

	public void down(int speed) {
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.down(speed);
			}
		stopped = false;
	}

	public void goRight(int speed) {
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.goRight(speed);
			}
		stopped = false;
	}

	public void goLeft(int speed) {
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.goLeft(speed);
			}
		stopped = false;
	}

	public void forward(int speed) {
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.forward(speed);
			}
		stopped = false;
	}

	public void backward(int speed) {
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.backward(speed);
			}
		stopped = false;
	}

	public void spinLeft(int speed) {
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.spinLeft(speed);
			}
		stopped = false;
	}

	public void spinRight(int speed) {
		if (connected)
			for (ARDroneForP5 a : ardrones.values()) {
				a.spinRight(speed);
			}
		stopped = false;
	}

	public void takeOff() {
		if (connected) {
			int arId = ardrones.keySet().iterator().next();
			for (ARDroneForP5 a : ardrones.values()) {
				a.takeOff();
				System.out.println(Constants.IP_ADDRESS_MASK + arId
						+ ": takeOff");
			}
		}
		inAir = true;
		stopped = true;
	}

	public void landing() {
		if (connected) {
			int arId = ardrones.keySet().iterator().next();
			for (ARDroneForP5 a : ardrones.values()) {
				a.landing();
				System.out.println(Constants.IP_ADDRESS_MASK + arId
						+ ": landing");
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
}
