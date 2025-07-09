/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class SoundManager {
    private MediaPlayer mediaPlayer;
    
    public void playBackgroundMusic() {
        try {
            // Cargar la canción desde recursos
            URL resource = getClass().getResource("/musica/Plants vs. Zombies (Main Theme)-yt.savetube.me.mp3");
            if (resource != null) {
                Media media = new Media(resource.toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repetir infinitamente
                mediaPlayer.play();
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la música: " + e.getMessage());
        }
    }
    
    public void stopBackgroundMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }   
}
