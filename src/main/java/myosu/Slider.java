package myosu;

import java.awt.Graphics2D;

public class Slider extends Target {
	private Path path;
	private Circle circle;
	
	public Slider() {
		
	}

	@Override
	public void update(long currentTime) {
		
	}

	@Override
	public void render(Graphics2D g, long currentTime) {
		
		
		circle.render(g, currentTime);
	}
}
