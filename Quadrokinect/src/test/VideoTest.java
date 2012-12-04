package test;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

import ardrone.ArdroneGroup;
import gui.VideoSketchWindow;

public class VideoTest {

	public static void main(String[] args) {
		ArdroneGroup group = new ArdroneGroup(0);
		group.addArdrone(new ARDroneForP5(), 0);
		group.setVideo(true);
		group.connect();
		VideoSketchWindow.show(group);
	}
}
