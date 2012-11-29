package gui;

import javax.swing.JFrame;

import processing.sketch.ARDroneVideoSketch;

import ardrone.ArdroneGroup;

public class VideoSketchWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ARDroneVideoSketch sketch;

	private VideoSketchWindow(ArdroneGroup group) {
		sketch = new ARDroneVideoSketch();
		sketch.calculateRowsAndCols(group.getArdroneNumber());
		sketch.init();
		sketch.setGroup(group);
		add(sketch);
		setSize(ARDroneVideoSketch.w*sketch.col, ARDroneVideoSketch.h*sketch.row+20);
	}

	public static ARDroneVideoSketch show(ArdroneGroup group){
		VideoSketchWindow window = new VideoSketchWindow(group);
		window.setVisible(true);
		return window.sketch;
	}
}
