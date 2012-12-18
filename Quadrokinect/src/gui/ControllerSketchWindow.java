package gui;

import javax.swing.JFrame;

import ardrone.ArdroneGroup;

import processing.sketch.QuadroKinectSketch;

public class ControllerSketchWindow {
	
	public static ArdroneGroup show(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTitle("Controller");
		frame.setSize(700, 720);
		QuadroKinectSketch sketch = new QuadroKinectSketch();
		frame.add(sketch);
		sketch.init();

		return sketch.getArdrones();
	}
}
