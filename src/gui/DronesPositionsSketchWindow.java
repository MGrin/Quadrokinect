package gui;

import javax.swing.JFrame;

import ardrone.ArdroneGroup;

import processing.sketch.DronesPositionsSketch;

public class DronesPositionsSketchWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DronesPositionsSketch sketch;
	
	public DronesPositionsSketchWindow(){
		sketch = new DronesPositionsSketch();
		sketch.init();
		add(sketch);
		setSize(720, 720);
		setTitle("Positions");
	}
	
	public void setDronesGroup(ArdroneGroup g){
		sketch.setDronesGroup(g);
	}
}
