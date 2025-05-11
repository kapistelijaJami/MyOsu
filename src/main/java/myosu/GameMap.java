package myosu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import myosu.util.RenderText;
import myosu.util.RenderText.Alignment;

public class GameMap {
	private ArrayList<Circle> circles = new ArrayList<>();
	private ArrayList<X> Xs = new ArrayList<>();
	private ArrayList<Shatter> shatters = new ArrayList<>();
	private boolean playingActive = false;
	
	private final int allowedMiss = 50;
	
	private int points = 0;
	
	public void setCircles(ArrayList<Circle> circles) {
		this.circles = circles;
	}
	
	public void update(long currentTime) {
		for (int i = 0; i < circles.size(); i++) { //render first ones on top
			if (circles.get(i) == null) {
				continue;
			}
			circles.get(i).update(currentTime);
		}
		
		for (int i = 0; i < Xs.size(); i++) {
			if (Xs.get(i) == null) {
				continue;
			}
			Xs.get(i).update();
		}
		
		for (int i = 0; i < shatters.size(); i++) {
			if (shatters.get(i) == null) {
				continue;
			}
			shatters.get(i).update();
		}
	}
	
	public void render(Graphics2D g, long currentTime) {
		for (int i = 0; i < shatters.size(); i++) {
			if (shatters.get(i) == null) {
				continue;
			}
			shatters.get(i).render(g);
		}
		
		for (int i = circles.size() - 1; i >= 0; i--) { //render first ones on top
			if (circles.get(i) == null) {
				continue;
			}
			circles.get(i).render(g, currentTime);
		}
		
		for (int i = 0; i < Xs.size(); i++) {
			if (Xs.get(i) == null) {
				continue;
			}
			Xs.get(i).render(g);
		}
		
		printPoints(g, currentTime);
	}

	public boolean isPlayingActive() {
		return playingActive;
	}

	public void setPlayingActive(boolean playingActive) {
		this.playingActive = playingActive;
	}

	public String click(Point p, long currentTime) {
		for (Circle circle : circles) {
			if (!circle.isVisible() || !circle.isInside(p)) {
				continue;
			}
			
			long delta = currentTime - circle.getHitTimeInMs();
			
			if (Math.abs(delta) <= allowedMiss) {
				Xs.add(new X(p.x, p.y, Color.GREEN));
				points += Constants.HIT_POINTS;
				circle.setDead(true);
				
				
				if (Game.GAME.CREATE_SHATTERS) {
					shatters.add(new Shatter(circle.getX(), circle.getY(), circle.getColor(), circle.getSize()));
				}
				
				Music music = new Music();
				music.playWavFile("/sounds/Clax.wav", 0);
				music.setVolume(100);
				return "HIT!";
			} else if (Math.abs(delta) <= allowedMiss + 50) {
				Xs.add(new X(p.x, p.y, Color.ORANGE));
				points += Constants.ALMOST_POINTS;
				circle.setDead(true);
				
				if (Game.GAME.CREATE_SHATTERS) {
					shatters.add(new Shatter(circle.getX(), circle.getY(), circle.getColor(), circle.getSize()));
				}
				
				Music music = new Music();
				music.playWavFile("/sounds/Clax.wav", 0);
				music.setVolume(100);
				return "ALMOST!";
			} else if (circle.getHitTimeInMs() > currentTime) {
				Xs.add(new X(p.x, p.y, Color.RED));
				circle.setDead(true);
				
				Music music = new Music();
				music.playWavFile("/sounds/519422__abdrtar__error.wav", 0);
				music.setVolume(100);
				return "TOO EARLY!";
			}
		}
		
		Xs.add(new X(p.x, p.y, Color.RED));
		
		
		Music music = new Music();
		music.playWavFile("/sounds/519422__abdrtar__error.wav", 0);
		music.setVolume(60);
		
		return "Missed!";
	}
	
	public int countHowManyIsPast(long currentTime) {
		int count = 0;
		for (int i = 0; i < circles.size(); i++) {
			Circle c = circles.get(i);
			if (c.isHitTimePassed(currentTime)) {
				count++;
			}
		}
		
		return count;
	}

	public void setCircles(String s) {
		circles.clear();
		String[] cs = s.split(";");
		for (String c : cs) {
			circles.add(new Circle(c));
		}
	}

	public void printPoints(Graphics2D g, long currentTime) {
		g.setColor(Color.red);
		RenderText.drawStringWithAlignment(g, "Points: " + points + " / " + (countHowManyIsPast(currentTime) * Constants.HIT_POINTS), new Rectangle(15, 15, Game.WIDTH, Game.HEIGHT), null, Alignment.TOP, Alignment.LEFT);
	}

	public void reset() {
		circles.clear();
		Xs.clear();
		shatters.clear();
		playingActive = false;
		points = 0;
	}
}
