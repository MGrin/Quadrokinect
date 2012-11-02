package processing.classes;

import jp.nyatla.nyar4psg.MultiMarker;
import monclubelec.javacvPro.Marker;
import monclubelec.javacvPro.OpenCV;
import processing.core.PApplet;
import processing.core.PImage;

public class MarkerDetection {

	private PApplet parent;
	private PImage im;
	private MultiMarker nya;
	private Marker[] markers = new Marker[1];
	private OpenCV cv;
	String camParamPath = "libs/NyARToolkit-4.0.3/Data/camera_para.dat";
	String patternPath = "markers/ARToolKit_Patterns";

	public MarkerDetection(PApplet p) {
		parent = p;
	}

	public void setup() {
		parent.size(320, 240);
		cv = new OpenCV(parent);
	}

	public void draw() {
		if (im != null) {
			parent.image(im, 0, 0);
			cv.allocate(im.width, im.height);
			cv.copy(im);

			nya = new MultiMarker(parent, 320, 240, camParamPath);
			nya.setLostDelay(1);
			nya.setThreshold(MultiMarker.THLESHOLD_AUTO);
			nya.setConfidenceThreshold(MultiMarker.DEFAULT_CF_THRESHOLD);

			markers[0] = new Marker();
			markers[0].realWidth = 384;
			markers[0].name = "4x4_99.patt";

			nya.addARMarker(patternPath + "/" + markers[0].name,
					markers[0].realWidth);

			nya.detect(im);
			cv.updateMarkers(nya, markers, false);
			if (cv.isExistMarker(nya, 0)) {
				cv.draw2DMarkers(nya, markers);
			}
		}
	}

	public void updateImage(PImage videoImage) {
		im = videoImage;
	}
}
