package listeners;

import oscP5.OscMessage;
import oscP5.OscP5;
import processing.sketch.QuadroKinectSketch;
import utils.ControllerEnum;

public class OscListener{

	QuadroKinectSketch parent;
	OscP5 oscP5;

	public OscListener(QuadroKinectSketch parent) {
		this.parent = parent;
		oscP5 = new OscP5(this, 7110);
	}

	public void oscEvent(OscMessage msg) {
		if (msg.checkAddrPattern("/new_user") && msg.checkTypetag("i")) {
			System.out.println("New user found, id=" + msg.get(0).intValue()
					+ ", calibration started");
			if (parent.getUserTrackedID() != -1) {
				System.out.println("Already has a user to track");
			}
		} else if (msg.checkAddrPattern("/new_skel") && msg.checkTypetag("i")) {
			int id = msg.get(0).intValue();
			if (parent.getUserTrackedID() == -1) {
				parent.setUserTrackedID(id);
				System.out.println("Calibration for user " + id
						+ " finished, tracking");
			}
		} else if (msg.checkAddrPattern("/joint") && msg.checkTypetag("sifff")) {
			String bodyPart = msg.get(0).stringValue();
			int userId = msg.get(1).intValue();
			if (userId == parent.getUserTrackedID()) {
				if (bodyPart.equals("r_hand")) {
					parent.updateController(ControllerEnum.LEFT_HAND, msg
							.get(2).floatValue(), msg.get(3).floatValue(), msg
							.get(4).floatValue());
				}
				if (bodyPart.equals("l_hand")) {
					parent.updateController(ControllerEnum.RIGHT_HAND,
							msg.get(2).floatValue(), msg.get(3).floatValue(),
							msg.get(4).floatValue());
				}

			}
		} else if (msg.checkAddrPattern("/lost_user") && msg.checkTypetag("i")) {
			int id = msg.get(0).intValue();
			if (id == parent.getUserTrackedID()) {
				System.out.println("Lost user, stop tracking");
				parent.setUserTrackedID(-1);
				parent.resetController();
			}
		}
	}

}
