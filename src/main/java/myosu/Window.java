package myosu;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;

public class Window {
	private JFrame frame;

	public Window(int width, int height, String title, Game game, boolean fullscreen) {
		frame = new JFrame(title);
		
		
		frame.setPreferredSize(new Dimension(width, height));	//laitetaan ikkunan koko
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		
		
		//FULLSCREEN STUFF
		if (fullscreen) {
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setUndecorated(true);
		}
		
		frame.addWindowListener(new WindowAdapter() { //ikkuna sulkeutuu rastista ja pystyy kutsua myös metodia
			@Override
			public void windowClosing(WindowEvent e) {
				game.stop();
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				//minimized
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				//back from minimized
			}
		});
		
		frame.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {}

			@Override
			public void windowLostFocus(WindowEvent e) {
				
			}
		});
		
		//frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	//ikkuna sulkeutuu rastista
		frame.setResizable(false);								//ikkunaa ei voida venyttää
		frame.setLocationRelativeTo(null);						//ikkuna syntyy näytön keskelle
		frame.add(game);										//lisätään peli ikkunaan
		frame.pack();											//tehdään JFrame Windowista halutun kokoinen
		frame.setVisible(true);									//laitetaan ikkuna näkyväksi
		
		Game.WIDTH = width;
		Game.HEIGHT = height;
		
		Insets inset = frame.getInsets();
		Game.WIDTH -= inset.left + inset.right;
		Game.HEIGHT -= inset.top + inset.bottom;
	}
	
	public JFrame getFrame() {
		return this.frame;
	}

	public void setTitle(String string) {
		frame.setTitle(string);
	}
}
