package UI;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
    private Clip clip;

    // Method to start playing a sound
    public void playSound(String soundFilePath) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();  // Stop the currently playing sound
            }

            // Open an audio input stream.
            URL url = this.getClass().getClassLoader().getResource(soundFilePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            
            // Get a sound clip resource.
            clip = AudioSystem.getClip();
            
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to stop the sound
    public void stopSound() {
        if (clip != null) {
            clip.stop();
        }
    }
}
