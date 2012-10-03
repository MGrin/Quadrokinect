package ardroneControl;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

public class ArdroneControl {

	private ARDroneForP5 ardrone;
	
	private boolean connected = true;
	private boolean inAir = false;
	
	public ArdroneControl(ARDroneForP5 ard) {
		ardrone = ard;
	}

	public void connect() {
		if (connected) {
			ardrone.connect();
			ardrone.connectNav();
			ardrone.connectVideo();
			ardrone.start();
		}
	}

	public void safeDrone() {
		ardrone.stop();
		ardrone.landing();
	}

	public void stop() {
		if (connected)
			ardrone.stop();
	}

	public void up(int speed) {
		if (connected)
			ardrone.up(speed);
	}

	public void down(int speed) {
		if (connected)
			ardrone.down(speed);
	}

	public void goRight(int speed) {
		if (connected)
			ardrone.goRight(speed);
	}

	public void goLeft(int speed) {
		if (connected)
			ardrone.goLeft(speed);
	}

	public void forward(int speed) {
		if (connected)
			ardrone.forward(speed);
	}

	public void backward(int speed) {
		if (connected)
			ardrone.backward(speed);
	}

	public void spinLeft(int speed) {
		if (connected)
			ardrone.spinLeft(speed);
	}

	public void spinRight(int speed) {
		if (connected)
			ardrone.spinRight(speed);
	}
	
	public void takeOff(){
		if(connected)
			ardrone.takeOff();
		inAir = true;
	}
	
	public void landing(){
		if(connected)
			ardrone.landing();
		inAir = false;
	}
	
	public boolean inAir(){
		return inAir;
	}
}
