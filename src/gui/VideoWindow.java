package gui;

import javax.swing.JFrame;

import processing.sketchs.CameraView;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

public class VideoWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 673056234006436329L;

	private CameraView cam;
	
	public VideoWindow(ARDroneForP5 ardrone){
		cam = new CameraView(ardrone);
		add(cam);
		cam.init();
		setLocation(100, 50);
		pack();
	}
}
