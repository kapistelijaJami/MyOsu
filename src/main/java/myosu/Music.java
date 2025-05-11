package myosu;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Music {
	public Clip clip;
	public FloatControl volumeControl;
	
	public void playWavFile(String path, int loopCount) { //ex. -1 is continuous, 0 is not looping
		playWavFile(new BufferedInputStream(Music.class.getResourceAsStream(path)), loopCount);
	}
	
	public void playWavFile(BufferedInputStream audio, int loopCount) { //ex. -1 is continuous, 0 is not looping
		try {
			clip = AudioSystem.getClip();
			
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(audio);
			clip.open(inputStream);
			volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
			volumeControl.setValue(0f); //decibels 0f - no effect, -10f - quiet, 6f - max volume

			clip.loop(loopCount);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playWavFile(ByteArrayInputStream audio, int loopCount) { //ex. -1 is continuous
		try {
			clip = AudioSystem.getClip();
			
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(audio);
			clip.open(inputStream);
			volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
			volumeControl.setValue(0f); //decibels 0f - no effect, -10f - quiet, 6f - max volume

			clip.loop(loopCount);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setVolume(int percent) {
		if (percent < 0 || percent > 200) {
			return;
		}
		double volume = percent / 100.0f;
		
		volumeControl.setValue(20f * (float) Math.log10(volume));
	}
	
	public void stopMusic() {
		if (clip != null) {
			clip.stop();
		}
	}
}
