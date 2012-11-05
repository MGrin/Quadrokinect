package processing.classes;

import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class Axis {

	private PApplet parent;

	PeasyCam cam;
	PFont axisLabelFont;
	PVector axisXHud;
	PVector axisYHud;
	PVector axisZHud;
	PVector axisOrgHud;

	public Axis(PApplet p) {
		this.parent = p;
		cam = new PeasyCam(parent, 100);
		axisLabelFont = parent.createFont("Arial", 14);
		axisXHud = new PVector();
		axisYHud = new PVector();
		axisZHud = new PVector();
		axisOrgHud = new PVector();
	}

	public void display(boolean orientation) {
		calculateAxis(50, orientation);

		cam.beginHUD();
		drawAxis(2);
		cam.endHUD();
	}

	void calculateAxis(float length, boolean orientation) {
		// Store the screen positions for the X, Y, Z and origin
		axisXHud.set(parent.screenX(length, 0, 0),
				parent.screenY(length, 0, 0), 0);
		if (orientation) {
			axisYHud.set(parent.screenX(0, length, 0),
					parent.screenY(0, length, 0), 0);
		} else {
			axisYHud.set(parent.screenX(0, -length, 0),
					parent.screenY(0, -length, 0), 0);
		}
		axisZHud.set(parent.screenX(0, 0, length),
				parent.screenY(0, 0, length), 0);
		axisOrgHud.set(parent.screenX(0, 0, 0), parent.screenY(0, 0, 0), 0);
	}

	void drawAxis(float weight) {
		parent.pushStyle(); // Store the current style information

		parent.strokeWeight(weight); // Line width

		parent.stroke(255, 0, 0); // X axis color (Red)
		parent.line(axisOrgHud.x, axisOrgHud.y, axisXHud.x, axisXHud.y);

		parent.stroke(0, 255, 0);
		parent.line(axisOrgHud.x, axisOrgHud.y, axisYHud.x, axisYHud.y);

		parent.stroke(0, 0, 255);
		parent.line(axisOrgHud.x, axisOrgHud.y, axisZHud.x, axisZHud.y);

		parent.fill(255); // Text color
		parent.textFont(axisLabelFont); // Set the text font

		parent.text("X", axisXHud.x, axisXHud.y);
		parent.text("Y", axisYHud.x, axisYHud.y);
		parent.text("Z", axisZHud.x, axisZHud.y);

		parent.popStyle(); // Recall the previously stored style information
	}
}
