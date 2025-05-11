package myosu;

import java.awt.Color;
import java.util.ArrayList;
import myosu.util.StringUtils;

public class Recorder {
	private ArrayList<Circle> circles = new ArrayList<>();
	private boolean recordingActive = false;
	
	private int numberCounter = 1;
	private int colorCounter = 0;
	
	private Color[] colors = { Color.RED, Color.GREEN, Color.BLUE };
	
	public void add(long currentTime, int x, int y) {
		circles.add(new Circle(x, y, currentTime, 100, colors[colorCounter], numberCounter));
		
		numberCounter++;
		if (numberCounter > 8) {
			numberCounter = 1;
			colorCounter++;
			if (colorCounter >= colors.length) {
				colorCounter = 0;
			}
		}
	}
	
	public ArrayList<Circle> getCircles() {
		return circles;
	}

	public boolean isRecordingActive() {
		return recordingActive;
	}

	public void setRecordingActive(boolean recordingActive) {
		this.recordingActive = recordingActive;
	}

	public void printCircles() {
		String[] test = new String[circles.size()];
		for (int i = 0; i < circles.size(); i++) {
			Circle circle = circles.get(i);
			test[i] = circle.toString();
		}
		
		StringUtils.printDelimLn(";", test);
	}

	public void reset() {
		circles.clear();
		recordingActive = false;
		numberCounter = 1;
		colorCounter = 0;
	}
}
