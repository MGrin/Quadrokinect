package utils;

public class Position {

	private float x = 0;
	private float y = 0;
	private float z = 0;

	private float norme = 0;

	public Position() {
		x = 0;
		y = 0;
		z = 0;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getNorme() {
		return norme;
	}

	public void setX(float x) {
		this.x = x;
		calculateNorme();
	}

	public void setY(float y) {
		this.y = y;
		calculateNorme();
	}

	public void setZ(float z) {
		this.z = z;
		calculateNorme();
	}

	private void calculateNorme() {
		norme = (float) Math.sqrt(x * x + y * y + z * z);
	}
}
