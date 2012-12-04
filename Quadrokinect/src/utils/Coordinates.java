package utils;

public class Coordinates {

	private double[] coords = new double[3];

	public Coordinates(double x, double y, double z) {
		coords[0] = x;
		coords[1] = y;
		coords[2] = z;
	}

	public double[] getCoordinates() {
		return coords;
	}

	public double getX() {
		return coords[0];
	}

	public double getY() {
		return coords[1];
	}

	public double getZ() {
		return coords[2];
	}

}
