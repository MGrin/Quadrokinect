package listeners;

public interface GeneralCommandListener {

	public void hand(float[] controller, float[] leftHand, float[] rightHand);
	public void performClack();
	
}
