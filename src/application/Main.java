package application;

import gui.KinectWindow;

public class Main {

	final static String OSCeleton = "/home/mgrin/Drivers/KinectDrivers/OSCeleton/osceleton -mx 100 -my 100 -mz 100 -ox -50 -oy -50 -oz -100 -w";

	public static void main(String[] args) {
		(new KinectWindow()).setVisible(true);
	}

}
