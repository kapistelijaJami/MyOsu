package myosu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.Random;
import myosu.util.MathFunctions;

public class Shatter {
	private int x, y;
	private Color color;
	private int counter = 0;
	private double speed = 6;
	private double size;
	private boolean dead = false;
	
	private int[] angles = new int[6];
	
	public Shatter(int x, int y, Color color, double size) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.size = size / 5;
		
		angles[0] = 16 + getRandomSmallInt();
		angles[1] = 45 + getRandomSmallInt();
		angles[2] = 130 + getRandomSmallInt();
		angles[3] = 220 + getRandomSmallInt();
		angles[4] = 290 + getRandomSmallInt();
		angles[5] = 340 + getRandomSmallInt();
	}
	
	public void update() {
		if (counter > 15) {
			dead = true;
		}
		counter++;
	}
	
	public int getRandomSmallInt() {
		return new Random().nextInt(31) - 15;
	}
	
	public void render(Graphics2D g) {
		if (dead) {
			return;
		}
		g.setColor(color);
		Point2D delta = MathFunctions.getDeltaXAndYFromAngle(angles[0], speed * counter);
		renderPart(g, delta, (int) MathFunctions.correctAngle(counter));
		
		delta = MathFunctions.getDeltaXAndYFromAngle(angles[1], speed * counter);
		renderPart(g, delta, (int) MathFunctions.correctAngle(counter));
		
		delta = MathFunctions.getDeltaXAndYFromAngle(angles[2], speed * counter);
		renderPart(g, delta, (int) MathFunctions.correctAngle(counter));
		
		delta = MathFunctions.getDeltaXAndYFromAngle(angles[3], speed * counter);
		renderPart(g, delta, (int) MathFunctions.correctAngle(counter));
		
		delta = MathFunctions.getDeltaXAndYFromAngle(angles[4], speed * counter);
		renderPart(g, delta, (int) MathFunctions.correctAngle(counter));
		
		delta = MathFunctions.getDeltaXAndYFromAngle(angles[5], speed * counter);
		renderPart(g, delta, (int) MathFunctions.correctAngle(counter));
	}
	
	public void renderPart(Graphics2D g, Point2D delta, int renderOrientation) {
		int newX = (int) (x + delta.getX());
		int newY = (int) (y + delta.getY());
		
		g.rotate(Math.toRadians(renderOrientation), newX, newY);
		
		g.fillRoundRect(newX, newY, (int) (size / 2.0), (int) (size / 2.0), 5, 5);
		
		g.rotate(Math.toRadians(-renderOrientation), newX, newY);
	}
}
