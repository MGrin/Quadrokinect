package processing.classes;

public interface ControllerInterface {

	public void setup();
	public void display();
	public void updateLeftHand(float x, float y, float z);
	public void updateRightHand(float x, float y, float z);
	public void resetController();
	
	public float[] getLeftHand();
	public float[] getRightHand();
}
