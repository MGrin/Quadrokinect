package utils;

public class Calculus {

	public static float getDistance(float[] a, float[] b) {
		return (float) (Math.sqrt(Math.pow(a[0] - b[0], 2)
				+ Math.pow(a[1] - b[1], 2) + Math.pow(a[2] - b[2], 2)));
	}

	public static float calculateAngleBetween(float rightHand) {
		float sin = rightHand / 25f;
		if (rightHand > 0)
			return (float) Math.asin(sin);
		else
			return (float) -Math.asin(sin);
	}
}
