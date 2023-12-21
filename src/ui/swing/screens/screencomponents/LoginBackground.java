package ui.swing.screens.screencomponents;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.util.UIScale;

import ui.swing.screens.LoginOverlay;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;


public class LoginBackground extends JPanel {
	
	private MediaPlayerFactory factory;
    private EmbeddedMediaPlayer mediaPlayer;

    private LoginOverlay loginOverlay;

	
	public LoginBackground() {
		init();
	}
	
	private void init() {
	
		
		
		MediaPlayerFactory factory = new MediaPlayerFactory();
		
		factory = new MediaPlayerFactory();
	    mediaPlayer = factory.mediaPlayers().newEmbeddedMediaPlayer();
	    Canvas canvas = new Canvas();
	    mediaPlayer.videoSurface().set(factory.videoSurfaces().newVideoSurface(canvas));
	    
	    mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                if (newTime >= mediaPlayer.status().length() - 1000) {
                    mediaPlayer.controls().setPosition(0);
                }
            }
        });

	    setLayout(new BorderLayout());

	    add(canvas);

	}
	 

		
	public void initOverlay(JFrame frame) {
		
		loginOverlay = new LoginOverlay(frame);
		mediaPlayer.overlay().set(loginOverlay);
		mediaPlayer.overlay().enable(true);
		
		
	}

	public void play() {
		
		if(mediaPlayer.status().isPlaying()) {
			mediaPlayer.controls().stop();

		}
		
		mediaPlayer.media().play("src/ui/swing/resources/animations/video 3.mp4");

		
	}
	
	public void stop() {
		mediaPlayer.controls().stop();
		mediaPlayer.release();
	}
     

}

/**
* @Author -- H. Sarp Vulas
*/
