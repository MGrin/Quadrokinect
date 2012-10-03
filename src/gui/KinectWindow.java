package gui;

import javax.swing.JFrame;

import processingCode.KinectCapture;

public class KinectWindow extends JFrame{

	private static final long serialVersionUID = 5438003697668827962L;

	private final KinectCapture kinect= new KinectCapture();
	
	public KinectWindow(){
		super("Data from Kinect");
		add(kinect);
		kinect.init();
		setLocation(100, 50);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
