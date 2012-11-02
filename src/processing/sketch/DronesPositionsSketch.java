package processing.sketch;

import ardrone.ArdroneGroup;
import processing.classes.Axis;
import processing.core.PApplet;
import utils.Constants;

public class DronesPositionsSketch extends PApplet {

	private static final long serialVersionUID = 2624629919624959926L;

	private ArdroneGroup group;

	Axis axis;

	public void setup() {
		size(700, 700, P3D);
		background(0);
		axis = new Axis(this);
	}

	public void draw() {
		background(0);
		axis.display();
		fill(0, 0, 0, 0);
		stroke(255, 255, 255);
		box(50);
		stroke(0);
		fill(0);

		if (group != null)
			drawDrones();
	}

	public void drawDrones() {
		for (int i = 0; i < group.getArdroneNumber(); i++) {
			pushMatrix();

			fill(255);
			translate((int) (group.getPosition(i).getX()),
					(int) (group.getPosition(i).getY()),
					(int) (group.getPosition(i).getZ() - group.getAltitudeError(i)/100));
			sphere(Constants.DRONE_SIZE);
			fill(0);

			popMatrix();
			
			System.out.println("Position: "+group.getPosition(i).getX()+" "+group.getPosition(i).getY()+" "+group.getPosition(i).getZ());
		}
	}

	public void setDronesGroup(ArdroneGroup g) {
		group = g;
	}
}
