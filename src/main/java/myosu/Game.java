package myosu;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import lc.kra.system.mouse.GlobalMouseHook;

public class Game extends Canvas implements Runnable {
	public static int WIDTH;
	public static int HEIGHT;
	
	public static final double FPS = 120.0;
	public static Game GAME;
	
	public static boolean LOCK_MOUSE_TO_SCREEN = true;
	public static boolean FULLSCREEN = true;
	
	public static boolean CREATE_SHATTERS = true;

	private boolean unlimitedFPS = false;
	
	public int volume = 40; //def: 40
	
	private boolean running = false;
	private Thread thread;
	private Window window;
	
	private boolean recorded = false;
	private Recorder recorder = new Recorder();
	private GameMap gameMap = new GameMap();
	
	private long startTime;
	
	private Music music = new Music();
	
	public Game() {
		GAME = this;
	}
	
	public void createWindow() {
		window = new Window(Constants.WIDTH, Constants.HEIGHT, "My Osu", this, FULLSCREEN);
	}
	
	public synchronized void start() {
		if (running) {
			return;
		}

		running = true;

		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		try {
			running = false;
			//frame.dispose();
			System.exit(0);
			thread.join();
		} catch (Exception e) {
		}
	}
	
	public void init() {
		KeyInput input = new KeyInput(this);
		this.addKeyListener(input);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
		this.addMouseWheelListener(input);
		setFocusTraversalKeysEnabled(false);
		
		GlobalMouseHook mouseHook = new GlobalMouseHook();
		mouseHook.addMouseListener(input);
	}
	
	@Override
	public void run() {
		init();
		this.requestFocus();
		
		long now = System.nanoTime();
		long nsBetweenUpdates = (long) (1e9 / FPS);
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		
		while (running) {
			if (now + nsBetweenUpdates <= System.nanoTime()) {
				now += nsBetweenUpdates;
				update();
				updates++;
				render();
				frames++;
			}
			
			
			if (System.currentTimeMillis() - timer > 1000) { //sekunnin välein
				timer += 1000;
				System.out.println("Updates: " + updates + ", Frames: " + frames);
				updates = 0;
				frames = 0;
			}
		}
	}
	
	public void update() {
		if (gameMap.isPlayingActive()) {
			gameMap.update(getCurrentTime());
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR); //for image scaling to look sharp, might not be good for all images
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if (gameMap.isPlayingActive()) {
			gameMap.render(g, getCurrentTime());
		}
		
		
		g.dispose();
		bs.show();
	}
	
	public long getCurrentTime() {
		return System.currentTimeMillis() - startTime;
	}

	public boolean getRunning() {
		return running;
	}
	
	public Point getWindowLocationOnScreen() {
		return getLocationOnScreen();
	}
	
	public int getWindowTitleBarHeight() {
		return window.getFrame().getInsets().top;
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		click(p);
	}
	
	private void click(Point p) {
		if (recorder.isRecordingActive()) {
			recorder.add(getCurrentTime(), p.x, p.y);
		} else if (gameMap.isPlayingActive()) {
			System.out.println(gameMap.click(p, getCurrentTime()));
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
			case KeyEvent.VK_1:
				resetAll();
				playSave("910,518,3620,100.0,800,-65536,1;495,349,4038,100.0,800,-65536,2;1337,216,4464,100.0,800,-65536,3;1342,664,4698,100.0,800,-65536,4;619,829,5092,100.0,800,-65536,5;610,432,5320,100.0,800,-65536,6;1449,450,5742,100.0,800,-65536,7;1160,245,5966,100.0,800,-65536,8;873,231,6191,100.0,800,-16711936,1;779,611,6396,100.0,800,-16711936,2;1389,730,6802,100.0,800,-16711936,3;1488,531,7031,100.0,800,-16711936,4;1040,787,7477,100.0,800,-16711936,5;825,455,7881,100.0,800,-16711936,6;1197,417,8088,100.0,800,-16711936,7;1341,628,8474,100.0,800,-16711936,8;959,760,8706,100.0,800,-16776961,1;715,646,8937,100.0,800,-16776961,2;1049,488,9141,100.0,800,-16776961,3;1471,440,9358,100.0,800,-16776961,4;1606,747,9568,100.0,800,-16776961,5;1127,849,9816,100.0,800,-16776961,6;982,577,10235,100.0,800,-16776961,7;952,382,10478,100.0,800,-16776961,8;671,568,10889,100.0,800,-65536,1;1000,902,11309,100.0,800,-65536,2;1350,724,11534,100.0,800,-65536,3;1238,454,11935,100.0,800,-65536,4;844,396,12175,100.0,800,-65536,5;1211,296,12572,100.0,800,-65536,6;1476,440,12786,100.0,800,-65536,7;1606,782,12992,100.0,800,-65536,8;938,841,13222,100.0,800,-16711936,1;837,495,13620,100.0,800,-16711936,2;384,261,13877,100.0,800,-16711936,3;221,124,14272,100.0,800,-16711936,4;266,585,14715,100.0,800,-16711936,5;674,548,14918,100.0,800,-16711936,6;1144,233,15347,100.0,800,-16711936,7;1607,407,15572,100.0,800,-16711936,8;742,800,16471,100.0,800,-16776961,1;900,215,17317,100.0,800,-16776961,2;1416,522,17741,100.0,800,-16776961,3;659,602,18164,100.0,800,-16776961,4;1067,928,18393,100.0,800,-16776961,5;1543,886,18790,100.0,800,-16776961,6;1594,474,19024,100.0,800,-16776961,7;924,428,19414,100.0,800,-16776961,8;768,179,19639,100.0,800,-65536,1;435,152,19847,100.0,800,-65536,2;405,723,20093,100.0,800,-65536,3;1061,584,20487,100.0,800,-65536,4;1203,238,20737,100.0,800,-65536,5;1680,569,21143,100.0,800,-65536,6;1524,884,21549,100.0,800,-65536,7;1079,763,21783,100.0,800,-65536,8;626,507,22212,100.0,800,-16711936,1;437,276,22473,100.0,800,-16711936,2;1603,256,23307,100.0,800,-16711936,3;1052,618,24168,100.0,800,-16711936,4;728,628,24575,100.0,800,-16711936,5;702,328,25023,100.0,800,-16711936,6;1170,182,25247,100.0,800,-16711936,7;1463,616,25653,100.0,800,-16711936,8;1136,866,25883,100.0,800,-16776961,1;533,868,26299,100.0,800,-16776961,2;522,601,26514,100.0,800,-16776961,3;732,302,26727,100.0,800,-16776961,4;1246,363,26963,100.0,800,-16776961,5;1280,686,27362,100.0,800,-16776961,6;915,537,27598,100.0,800,-16776961,7;1409,863,28025,100.0,800,-16776961,8;817,784,28441,100.0,800,-65536,1;1337,801,28647,100.0,800,-65536,2;1397,422,29080,100.0,800,-65536,3;999,308,29324,100.0,800,-65536,4;611,597,30207,100.0,800,-65536,5;1023,565,31034,100.0,800,-65536,6");
				break;
			case KeyEvent.VK_Z:
				Point p = MouseInfo.getPointerInfo().getLocation();
				click(p);
				break;
			case KeyEvent.VK_X:
				p = MouseInfo.getPointerInfo().getLocation();
				click(p);
				break;
			case KeyEvent.VK_S:
				save();
				break;
			case KeyEvent.VK_ENTER:
				if (!recorder.isRecordingActive()) {
					resetAll();
					System.out.println("Start record!");
					startRecord();
				} else {
					System.out.println("Start playback!");
					startPlayback();
				}
				break;
			case KeyEvent.VK_ESCAPE:
				stop();
				break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
	}

	private void startRecord() {
		recorder.setRecordingActive(true);
		startTime = System.currentTimeMillis();
		
		music.playWavFile("/sounds/Butchers.wav", 0);
		startTime = System.currentTimeMillis();
		music.setVolume(volume);
	}

	private void startPlayback() {
		recorder.setRecordingActive(false);
		startTime = System.currentTimeMillis();
		gameMap.setCircles(recorder.getCircles());
		gameMap.setPlayingActive(true);
		
		music.stopMusic();
		music.playWavFile("/sounds/Butchers.wav", 0);
		startTime = System.currentTimeMillis();
		music.setVolume(volume);
	}

	private void save() {
		recorder.printCircles();
	}

	private void playSave(String s) {
		recorder.setRecordingActive(false);
		startTime = System.currentTimeMillis();
		gameMap.setCircles(s);
		gameMap.setPlayingActive(true);
		
		music.stopMusic();
		music.playWavFile("/sounds/Butchers.wav", 0);
		startTime = System.currentTimeMillis();
		music.setVolume(volume);
	}
	
	private void resetAll() {
		recorded = false;
		recorder.reset();
		gameMap.reset();
		music.stopMusic();
	}
}
