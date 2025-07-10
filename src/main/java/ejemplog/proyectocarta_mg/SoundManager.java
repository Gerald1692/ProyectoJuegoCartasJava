package ejemplog.proyectocarta_mg;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class SoundManager {
    private MediaPlayer backgroundPlayer;
    private MediaPlayer endGamePlayer;
    private double volume = 0.5;
    
    public void playBackgroundMusic() {
        try {
            // Detener cualquier sonido de fin de juego que esté reproduciéndose
            stopEndGameSound();
            
            if (backgroundPlayer != null && backgroundPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                return; // Ya está reproduciéndose
            }
            
            URL resource = getClass().getResource("/musica/Plants vs. Zombies (Main Theme)-yt.savetube.me.mp3");
            if (resource != null) {
                Media media = new Media(resource.toString());
                backgroundPlayer = new MediaPlayer(media);
                backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                backgroundPlayer.setVolume(volume);
                backgroundPlayer.play();
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la música: " + e.getMessage());
        }
    }
    
    public void playGameEndSound() {
        try {
            // Detener música de fondo primero
            stopBackgroundMusic();
            
            URL resource = getClass().getResource("/musica/plants-vs-zombies-victory-jingle.mp3");
            if (resource != null) {
                Media sound = new Media(resource.toString());
                endGamePlayer = new MediaPlayer(sound);
                endGamePlayer.setVolume(volume);
                endGamePlayer.play();
            }
        } catch (Exception e) {
            System.err.println("Error al reproducir sonido de fin de juego: " + e.getMessage());
        }
    }
    
    public void playLoseSound() {
        try {
            // Detener música de fondo primero
            stopBackgroundMusic();
            
            URL resource = getClass().getResource("/musica/plants-vs.mp3");
            if (resource != null) {
                Media sound = new Media(resource.toString());
                endGamePlayer = new MediaPlayer(sound);
                endGamePlayer.setVolume(volume);
                endGamePlayer.play();
            }
        } catch (Exception e) {
            System.err.println("Error al reproducir sonido de derrota: " + e.getMessage());
        }
    }
    
    public void playFlipSound() {
        try {
            URL resource = getClass().getResource("/musica/girarCarta.mp3");
            if (resource != null) {
                Media sound = new Media(resource.toString());
                MediaPlayer flipPlayer = new MediaPlayer(sound);
                flipPlayer.setVolume(volume);
                flipPlayer.play();
            }
        } catch (Exception e) {
            System.err.println("Error al reproducir sonido de volteo: " + e.getMessage());
        }
    }
    
    public void stopBackgroundMusic() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
        }
    }
    
    public void stopEndGameSound() {
        if (endGamePlayer != null) {
            endGamePlayer.stop();
        }
    }
    
    public void resumeBackgroundMusic() {
        stopEndGameSound();
        playBackgroundMusic();
    }
    
    public void setVolume(double volume) {
        this.volume = volume;
        if (backgroundPlayer != null) {
            backgroundPlayer.setVolume(volume);
        }
        if (endGamePlayer != null) {
            endGamePlayer.setVolume(volume);
        }
    }
    
    public double getVolume() {
        return volume;
    }
    
    public void stopAllSounds() {
        stopBackgroundMusic();
        stopEndGameSound();
    }
}