package gui;

import javax.swing.JFrame;

import processing.sketch.ARDroneVideoSketch;
import utils.Constants;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

public class VideoSketchWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ARDroneVideoSketch sketch;

	public VideoSketchWindow() {
		sketch = new ARDroneVideoSketch();
		sketch.init();
		add(sketch);
		pack();
	}

	public void setDrone(ARDroneForP5 drone, int id) {
		sketch.setDrone(drone);
		setTitle("Video from drone " + id);
	}
}
