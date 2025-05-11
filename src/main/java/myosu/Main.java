package myosu;

public class Main {
	//Press Enter to start recording and again to play it back. S prints the current recording so you can save it. Press 1 to play old recording.
	public static void main(String[] args) {
		Game game = new Game();
		game.createWindow();
		game.start();
	}
}
