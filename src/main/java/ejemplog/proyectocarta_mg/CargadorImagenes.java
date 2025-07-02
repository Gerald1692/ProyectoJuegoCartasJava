/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;

import javafx.scene.image.Image;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author admar
 */
public class CargadorImagenes {
    public static List<Image> cargarDesdeCarpeta (String nomCarpeta){
        List <Image> imagenes = new ArrayList<>();
        
        try {
           File carpeta = new File("src/main/resources/"+ nomCarpeta);
           File [] archivos = carpeta.listFiles(((dir, name) -> name.endsWith(".jpg")));
           
           if(archivos != null){
               for(File archivo : archivos){
                   URL url = archivo.toURI().toURL();
                   Image imagen = new Image(url.toExternalForm());
                   imagenes.add(imagen);
               
               }
           }
           
           
           
            
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        Collections.shuffle(imagenes);
        return imagenes;
    }
}
