package gui;

import javax.swing.JFrame;

import com.shigeodayo.ardrone.ARDrone;

import processing.sketch.StatusSketch;

public class StatusSketchWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2339041533152520743L;
	
	private StatusSketch sketch;

	public static StatusSketch show(ARDrone drone) {
		StatusSketchWindow window = new StatusSketchWindow(drone);
		window.setVisible(true);
		return window.sketch;
	}

	private StatusSketchWindow(ARDrone drone) {
		sketch = new StatusSketch();
		sketch.init();
		sketch.setDrone(drone);
		add(sketch);
		pack();
	}
}
