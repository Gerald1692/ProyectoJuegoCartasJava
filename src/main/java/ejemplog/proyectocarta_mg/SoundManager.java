package ejemplog.proyectocarta_mg;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class SoundManager {
    private MediaPlayer mediaPlayer;
    private double volume = 0.5; // Volumen predeterminado (50%)
    
    public void playBackgroundMusic() {
        try {
            URL resource = getClass().getResource("/musica/Plants vs. Zombies (Main Theme)-yt.savetube.me.mp3");
            if (resource != null) {
                Media media = new Media(resource.toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setVolume(volume); // Usar el volumen almacenado
                mediaPlayer.play();
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la música: " + e.getMessage());
        }
    }
    public void playFlipSound() {
    try {
        URL resource = getClass().getResource("/musica/plants-vs-zombies-sun-pickup.mp3"); // Cambia al nombre de tu archivo
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
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
    
    public void setVolume(double volume) {
        this.volume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }
    
    public double getVolume() {
        return volume;
    }
}