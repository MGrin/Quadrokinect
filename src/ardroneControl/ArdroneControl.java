package ardroneControl;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

public class ArdroneControl {

	private ARDroneForP5 ardrone;
	
	private boolean connected = true;
	private boolean inAir = false;
	private boolean stoped = true;
	
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
		stoped = true;
		inAir=false;
		System.out.println("Safe drone!");
	}

	public void stop() {
		if (connected)
			ardrone.stop();
		stoped = true;
		System.out.println("Stop");
	}

	public void up(int speed) {
		if (connected)
			ardrone.up(speed);
		stoped = false;
	}

	public void down(int speed) {
		if (connected)
			ardrone.down(speed);
		stoped = false;
	}

	public void goRight(int speed) {
		if (connected)
			ardrone.goRight(speed);
		stoped = false;
	}

	public void goLeft(int speed) {
		if (connected)
			ardrone.goLeft(speed);
		stoped = false;
	}

	public void forward(int speed) {
		if (connected)
			ardrone.forward(speed);
		stoped = false;
	}

	public void backward(int speed) {
		if (connected)
			ardrone.backward(speed);
		stoped = false;
	}

	public void spinLeft(int speed) {
		if (connected)
			ardrone.spinLeft(speed);
		stoped = false;
	}

	public void spinRight(int speed) {
		if (connected)
			ardrone.spinRight(speed);
		stoped = false;
	}
	
	public void takeOff(){
		if(connected)
			ardrone.takeOff();
		inAir = true;
		System.out.println("TakeOf");
	}
	
	public void landing(){
		if(connected)
			ardrone.landing();
		inAir = false;
		System.out.println("Landing");
	}
	
	public boolean inAir(){
		return inAir;
	}
	
	public boolean isStopped(){
		return stoped;
	}
}
