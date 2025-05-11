package myosu;

import myosu.util.MathFunctions;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import myosu.util.RenderText;
import myosu.util.StringUtils;

public class Circle extends Target {
	private int x, y;
	private long hitTimeInMs;
	private int spawnDelta = 800;
	private int despawnDelta = 50;
	private int displayNumber;
	private double size;
	
	public Circle(int x, int y, long hitTimeInMs, double size, Color color, int displayNumber) {
		this.x = x;
		this.y = y;
		this.hitTimeInMs = hitTimeInMs;
		this.size = size;
		this.color = color;
		this.displayNumber = displayNumber;
	}
	
	public Circle(int x, int y, long hitTimeInMs, double size, Color color, int displayNumber, int spawnDelta) { //spawnDelta is positive delta before hit time
		this(x, y, hitTimeInMs, size, color, displayNumber);
		this.spawnDelta = spawnDelta;
	}

	public Circle(String c) {
		String[] vals = c.split(",");
		
		this.x = Integer.parseInt(vals[0]);
		this.y = Integer.parseInt(vals[1]);
		this.hitTimeInMs = Long.parseLong(vals[2]);
		this.size = Double.parseDouble(vals[3]);
		this.spawnDelta = Integer.parseInt(vals[4]);
		this.color = new Color(Integer.parseInt(vals[5]));
		this.displayNumber = Integer.parseInt(vals[6]);
	}
	
	@Override
	public void update(long currentTime) {
		if (currentTime >= hitTimeInMs - spawnDelta) {
			visible = true;
		}
		
		if (currentTime >= hitTimeInMs + despawnDelta) {
			visible = false;
			dead = true;
		}
	}
	
	@Override
	public void render(Graphics2D g, long currentTime) {
		if (!visible || dead) {
			return;
		}
		
		g.setColor(color);
		g.fillOval((int) (x - size / 2.0), (int) (y - size / 2.0), (int) size, (int) size);
		g.setColor(Color.black);
		
		g.setStroke(new BasicStroke(5f));
		g.drawOval((int) (x - size / 2.0), (int) (y - size / 2.0), (int) size, (int) size);
		
		Font font = new Font("Serif", Font.BOLD, 50);
		
		RenderText.drawStringWithAlignment(g, "" + displayNumber, new Rectangle((int) (x - size / 2.0), (int) (y - size / 2.0), (int) size, (int) size), font);
		
		double outerSize = getOuterCircleSize(currentTime);
		
		g.setStroke(new BasicStroke(3f));
		g.drawOval((int) (x - outerSize / 2.0), (int) (y - outerSize / 2.0), (int) outerSize, (int) outerSize);
	}

	public long getHitTimeInMs() {
		return hitTimeInMs;
	}

	boolean isHitTimePassed(long currentTime) {
		return hitTimeInMs <= currentTime;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getSize() {
		return size;
	}
	
	private double getOuterCircleSize(long currentTime) {
		if (currentTime < hitTimeInMs - spawnDelta || currentTime > hitTimeInMs) {
			return -1;
		}
		
		long delta = hitTimeInMs - currentTime; //spawnDelta and it's at spawn time, lowers to -> 0 is at hit point
		
		double percent = delta / (double) spawnDelta;
		
		double extraSizeStart = 400;
		return size + extraSizeStart * percent;
	}
	
	public boolean isInside(Point p) {
		return MathFunctions.distance(p.x, p.y, x, y) <= size / 2.0;
	}
	
	@Override
	public String toString() {
		return StringUtils.stringDelim(",", x, y, hitTimeInMs, size, spawnDelta, color.getRGB(), displayNumber);
	}
}
