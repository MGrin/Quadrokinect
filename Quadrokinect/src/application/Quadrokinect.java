package application;

import processing.sketch.ARDroneVideoSketch;
import processing.sketch.PositionsSketch;
import processing.sketch.StatusSketch;
import ardrone.ArdroneGroup;
import gui.ControllerSketchWindow;
import gui.StatusSketchWindow;
import gui.VideoSketchWindow;

public class Quadrokinect {

	public static ArdroneGroup mainArdroneGroup;
	public static ARDroneVideoSketch mainVideoSketch;
	public static PositionsSketch mainPositionsSketch;
	public static StatusSketch mainStatusSketch;

	public static void main(String[] args) {
		mainArdroneGroup = ControllerSketchWindow.show();
		mainVideoSketch = VideoSketchWindow.show(mainArdroneGroup);
		// mainPositionsSketch = PositionsSketchWindow.show(mainArdroneGroup);
		mainStatusSketch = StatusSketchWindow.show(mainArdroneGroup
				.getARDrone(0));
	}
}
