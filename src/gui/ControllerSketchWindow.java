package gui;

import javax.swing.JFrame;

import processing.sketch.QuadroKinectSketch;

public class ControllerSketchWindow {

	public static void show(){
		JFrame frame = new JFrame();
		QuadroKinectSketch sketch = new QuadroKinectSketch();
		frame.add(sketch);
		sketch.init();
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTitle("Controller");
	}
}
