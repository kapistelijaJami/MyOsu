package myosu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class X {
	private int x, y;
	private Counter counter;
	private boolean visible = true;
	private Color color;
	
	public X(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		counter = new Counter(0, 100, true);
	}
	
	public void update() {
		counter.advance();
		if (counter.isDone()) {
			visible = false;
		}
	}
	
	public void render(Graphics2D g) {
		if (!visible) {
			return;
		}
		
		int length = 15;
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(10f));
		g.drawLine(x - length, y - length, x + length, y + length);
		g.drawLine(x + length, y - length, x - length, y + length);
		
		g.setColor(color);
		g.setStroke(new BasicStroke(5f));
		g.drawLine(x - length, y - length, x + length, y + length);
		g.drawLine(x + length, y - length, x - length, y + length);
		
	}
}
