package gui;

import javax.swing.JFrame;

import ardrone.ArdroneGroup;

import processing.sketch.QuadroKinectSketch;

public class ControllerSketchWindow {
	
	public static ArdroneGroup show(){
		JFrame frame = new JFrame();
		QuadroKinectSketch sketch = new QuadroKinectSketch();
		frame.add(sketch);
		sketch.init();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTitle("Controller");
		frame.pack();
		return sketch.getArdrones();
	}
}
