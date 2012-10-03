package processingCode;

import java.awt.Point;

import processing.core.PApplet;

public class CircleZone {

	PApplet parent;
	
	Point center;
	float R;
	
	CircleZone(PApplet p, Point c, float r){
		parent = p;
		center = c;
		R = r;
	}
	
	void display(){
		parent.ellipseMode(parent.CENTER);
		parent.ellipse(center.x, center.y, R, R);
	}
	
	boolean contains(float[] point){
		if(Math.pow(center.x - point[0], 2) + Math.pow(center.y - point[1], 2) < R*10)
			return true;
		else
			return false;
	}
}
