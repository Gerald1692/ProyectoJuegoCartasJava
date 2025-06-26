package ejemplog.proyectocarta_mg;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class PrimaryController {

    
   

    
    

    
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
      @FXML
    private void MostrarMensaje(ActionEvent event){
        Alert comfimar =new Alert(Alert.AlertType.INFORMATION);
        comfimar.setTitle("Bienvenido ");
        comfimar.setHeaderText("Estas seguro?");
        comfimar.setContentText("Aceptar para continuar ");
        comfimar.showAndWait().ifPresent(respuesta->{
        
        if (respuesta==ButtonType.OK){
            System.out.println("Seleccionaste Ok");
            
        }
        else{
            System.out.println("Operacion Cancelada");
        }
            
        
        
        
        });
        
        
        
    }
}


