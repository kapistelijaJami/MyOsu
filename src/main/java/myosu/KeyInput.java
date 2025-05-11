package myosu;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import lc.kra.system.mouse.event.GlobalMouseEvent;
import lc.kra.system.mouse.event.GlobalMouseListener;

public class KeyInput extends KeyAdapter implements MouseListener, MouseMotionListener, MouseWheelListener, GlobalMouseListener {
	private Game game;
	
	public KeyInput(Game game) {
		this.game = game;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		game.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		game.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		game.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		game.mouseMoved(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}

	@Override
	public void mousePressed(GlobalMouseEvent gme) {
		
	}

	@Override
	public void mouseReleased(GlobalMouseEvent gme) {
		
	}

	@Override
	public void mouseMoved(GlobalMouseEvent gme) {
		if (!Game.LOCK_MOUSE_TO_SCREEN || !game.hasFocus() || !game.getRunning()) {
			return;
		}
		
		int titleBarHeight = game.getWindowTitleBarHeight();
		Point p = game.getWindowLocationOnScreen();
		
		if (gme.getButtons() > 0) {
			if (gme.getX() > p.x && gme.getX() < p.x + Game.WIDTH - 1 && gme.getY() > p.y - titleBarHeight && gme.getY() < p.y - 1) {
				return;
			}
		}
		
		Point corrected = new Point(gme.getX() - p.x, gme.getY() - p.y);
		try {
			Robot robot = new Robot();
			if (corrected.x <= 0) {
				robot.mouseMove(p.x + 1, gme.getY());
			} else if (corrected.x >= Game.WIDTH - 1) {
				robot.mouseMove(p.x + Game.WIDTH - 2, gme.getY());
			} else if (corrected.y <= 0) {
				robot.mouseMove(gme.getX(), p.y + 1);
			} else if (corrected.y >= Game.HEIGHT - 1) {
				robot.mouseMove(gme.getX(), p.y + Game.HEIGHT - 2);
			}
		} catch (AWTException error) {
			error.printStackTrace();
		}
		
		//game.requestFocus();
	}
	
	@Override
	public void mouseWheel(GlobalMouseEvent gme) {
		
	}
}