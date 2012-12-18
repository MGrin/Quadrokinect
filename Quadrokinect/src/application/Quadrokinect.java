package application;

import ardrone.ArdroneGroup;
import gui.ControllerSketchWindow;
public class Quadrokinect {

	public static ArdroneGroup mainArdroneGroup;

	public static void main(String[] args) {
		mainArdroneGroup = ControllerSketchWindow.show();
	}
}
