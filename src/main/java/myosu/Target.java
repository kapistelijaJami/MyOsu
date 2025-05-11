package myosu;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Target {
	protected boolean visible = false;
	protected boolean dead = false;
	protected Color color;
	
	public abstract void update(long currentTime);
	public abstract void render(Graphics2D g, long currentTime);

	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public Color getColor() {
		return color;
	}
}
