package application;

import processing.sketch.ARDroneVideoSketch;
import processing.sketch.PositionsSketch;
import ardrone.ArdroneGroup;
import gui.ControllerSketchWindow;
import gui.PositionsSketchWindow;
import gui.VideoSketchWindow;

public class Quadrokinect {

	public static ArdroneGroup mainArdroneGroup;
	public static ARDroneVideoSketch mainVideoSketch;
	public static PositionsSketch mainPositionsSketch;
	
	public static void main(String[] args) {
		mainArdroneGroup = ControllerSketchWindow.show();
		mainVideoSketch = VideoSketchWindow.show(mainArdroneGroup);
		mainPositionsSketch = PositionsSketchWindow.show(mainArdroneGroup);
	}
}
