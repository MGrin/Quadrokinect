package gui;

import javax.swing.JFrame;

import ardrone.ArdroneGroup;

import processing.sketch.PositionsSketch;

public class PositionsSketchWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PositionsSketch sketch;
	
	private PositionsSketchWindow(ArdroneGroup group){
		sketch = new PositionsSketch();
		sketch.setDronesGroup(group);
		sketch.init();
		add(sketch);
		setSize(sketch.w, sketch.h+20);
		setTitle("Positions");
	}
	
	public static PositionsSketch show(ArdroneGroup group){
		PositionsSketchWindow window = new PositionsSketchWindow(group);
		window.setVisible(true);
		return window.sketch;
	}
}
