package utils;

public class Constants {

	public static final float ROTATION_DELTA = 0.05f;

	public static final float CONTROLLER_SIZE = 2;

	public static final int SAFE_ZONE_STEP = 3;
	public static final float SAFE_ZONE_DELTA = (float) (Math.PI / SAFE_ZONE_STEP);
	public static final float SAFE_ZONE_SIZE = CONTROLLER_SIZE * 8f;

	public static final float MIN_DISTANCE_BETWEEN_HANDS = 5;

	public static final float SPEED_SCALE = 1.8f;

	public static final int MENU_ZONE_STEP = 2;
	public static final float MENU_ZONE_DELTA = (float) (Math.PI / MENU_ZONE_STEP);
	public static final float MENU_ZONE_SIZE = CONTROLLER_SIZE * 1.5f;
	public static final float MENU_LEFT_POSITION[] = new float[] { -20, 0, 0 };
	public static final float MENU_RIGHT_POSITION[] = new float[] { 20, 0, 0 };
	public static final float HAND_SIZE = 1;

	public static final float SAFE_ANGLE = 0.25f;

	public static final int SPIN_SCALE = 45;
	
	public static final String IP_ADDRESS_MASK = "192.168.0.";

	public static final int STARTING_PORT = 5550;

	public static final int NAV_PORT = 5660;

	public static final int VIDEO_PORT = 5760;

	public static final byte FRONT_CAMERA = 0;

	public static final byte BOTTOM_CAMERA = 1;

	public static final float DRONE_SIZE = 2;

	public static final float POSITION_SCALE = 100f;

	public static final int MINIMAL_SAFE_DISTANCE = 1;
}
