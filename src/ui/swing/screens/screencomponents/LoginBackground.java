package ui.swing.screens.screencomponents;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;


public class LoginBackground extends JPanel {
	
	private MediaPlayerFactory factory;
    private EmbeddedMediaPlayer mediaPlayer;

	
	public LoginBackground() {
		init();
	}
	
	private void init() {
		String[] vlcArgs = {
			    "--avcodec-hw=none"  // Disable hardware decoding
			};
		 factory = new MediaPlayerFactory(vlcArgs);
	        mediaPlayer = factory.mediaPlayers().newEmbeddedMediaPlayer();
	        Canvas canvas = new Canvas();
	        mediaPlayer.videoSurface().set(factory.videoSurfaces().newVideoSurface(canvas));
	     
	     setLayout(new BorderLayout());
	     add(canvas);
		
	}
	 
	public void play() {
		
		if(mediaPlayer.status().isPlaying()) {
			mediaPlayer.controls().stop();
		}
		
		mediaPlayer.media().play("src/ui/swing/resources/animations/dragon.mp4");
	
		
	}
	
	public void stop() {
		mediaPlayer.controls().stop();
		mediaPlayer.release();
	}
     

}

/**
* @Author -- H. Sarp Vulas
*/
