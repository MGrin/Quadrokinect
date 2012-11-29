package processing.sketch;

import ardrone.ArdroneGroup;
import processing.classes.Axis;
import processing.core.PApplet;
import utils.Constants;

public class PositionsSketch extends PApplet {

	private static final long serialVersionUID = 2624629919624959926L;

	private ArdroneGroup group;

	Axis axis;

	public int w = 700;
	public int h = 700;

	public void setup() {
		size(w, h, P3D);
		axis = new Axis(this);
		background(0);
	}

	public void draw() {
		background(0);
		if (group.getARDrone(0) != null) {
			axis.display(false);

			fill(255, 255, 255, 120);
			rect(-50, -50, 100, 100);
			if (group != null)
				drawDrones();
		} else {
			text("Virtual drones, no positions =(", 0, 20);
		}
	}

	public void drawDrones() {
		for (int i = 0; i < group.getArdroneNumber(); i++) {
			pushMatrix();

			fill(255);
			translate(
					(int) (group.getPosition(i).getX() / Constants.POSITION_SCALE),
					(int) (group.getPosition(i).getY() / Constants.POSITION_SCALE),
					(int) (group.getPosition(i).getZ()
							/ Constants.POSITION_SCALE - group
							.getAltitudeError(i) / Constants.POSITION_SCALE));
			sphere(Constants.DRONE_SIZE);
			fill(0);

			popMatrix();

			// System.out.println("Position: "+group.getPosition(i).getX()+" "+group.getPosition(i).getY()+" "+group.getPosition(i).getZ());
		}
	}

	public void setDronesGroup(ArdroneGroup g) {
		group = g;
	}
}
