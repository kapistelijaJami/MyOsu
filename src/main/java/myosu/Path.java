package myosu;

import java.awt.Point;
import java.util.ArrayList;

public class Path {
	private ArrayList<Point> path = new ArrayList<>();
	
	public Path(Point start) {
		path.add(start);
	}
	
	public void addPoint(Point p) {
		path.add(p);
	}
}
