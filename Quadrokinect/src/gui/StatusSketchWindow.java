package gui;

import javax.swing.JFrame;

import processing.sketch.StatusSketch;

import com.shigeodayo.ardrone.processing.ARDroneForP5;

public class StatusSketchWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2339041533152520743L;
	
	private StatusSketch sketch;

	public static StatusSketch show(ARDroneForP5 drone) {
		StatusSketchWindow window = new StatusSketchWindow(drone);
		window.setVisible(true);
		return window.sketch;
	}

	private StatusSketchWindow(ARDroneForP5 drone) {
		sketch = new StatusSketch();
		sketch.init();
		sketch.setDrone(drone);
		add(sketch);
		pack();
	}
}
