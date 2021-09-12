import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound implements Runnable{

	Thread soundThread;

	
	URL paddleSoundFile = getClass().getResource("paddleSound.wav");
	URL scoreSoundFile = getClass().getResource("scoreSound.wav");
	URL wallSoundFile = getClass().getResource("wallSound.wav");
	
	
	// streams here
	
	AudioInputStream paddleStream;
	AudioInputStream scoreStream;
	AudioInputStream wallStream;
	
	Clip clip1;
	Clip clip2;
	Clip clip3;
	
	
	Sound(){
		
		soundThread = new Thread(this);
		soundThread.start();
	}
	
	@Override
	public void run() {
			try {
				
				
				
				paddleStream = AudioSystem.getAudioInputStream(paddleSoundFile);
				
				clip1 = AudioSystem.getClip();
				
				scoreStream = AudioSystem.getAudioInputStream(scoreSoundFile);
				
				clip2 = AudioSystem.getClip();
				
				wallStream = AudioSystem.getAudioInputStream(wallSoundFile);
				
				clip3 = AudioSystem.getClip();
				
				clip1.open(paddleStream);
				clip2.open(scoreStream);
				clip3.open(wallStream);
				
				//clip3.start();
				
			} catch (UnsupportedAudioFileException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
	}
	
	public void playSound(int clip) { // clip 1 == paddleSound, clip2 == scoreSound, clip3 == wallSound
		if(clip == 1) {
			clip1.start();
			
			try {
				paddleStream = AudioSystem.getAudioInputStream(paddleSoundFile);
				clip1 = AudioSystem.getClip();
				clip1.open(paddleStream);
				
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(clip == 2) {
			clip2.start();
			
			try {
				scoreStream = AudioSystem.getAudioInputStream(scoreSoundFile);
				clip2 = AudioSystem.getClip();
				clip2.open(scoreStream);
				
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(clip == 3) {
			clip3.start();
			
			try {
				wallStream = AudioSystem.getAudioInputStream(wallSoundFile);
				clip3 = AudioSystem.getClip();
				clip3.open(wallStream);
				
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
