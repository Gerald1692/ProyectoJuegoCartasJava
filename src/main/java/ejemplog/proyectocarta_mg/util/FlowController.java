/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ejemplog.proyectocarta_mg.controller.Controller;
import ejemplog.proyectocarta_mg.controller.FlowController;
import ejemplog.proyectocarta_mg.App;

public class FlowController {

    private static FlowController INSTANCE = null;
    private static Stage mainStage;
    private static ResourceBundle idioma;
    private static final HashMap<String, FXMLLoader> loaders = new HashMap<>();

    private FlowController() {
    }

    public static FlowController getInstance() {
        if (INSTANCE == null) {
            synchronized (FlowController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FlowController();
                }
            }
        }
        return INSTANCE;
    }

    public void initialize(Stage stage, ResourceBundle idioma) {
        FlowController.mainStage = stage;
        FlowController.idioma = idioma;
    }

    private FXMLLoader getLoader(String name) {
        FXMLLoader loader = loaders.get(name);
        if (loader == null) {
            try {
                loader = new FXMLLoader(App.class.getResource("/view/" + name + ".fxml"), idioma);
                loader.load();
                loaders.put(name, loader);
            } catch (IOException ex) {
                Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Error loading view: " + name, ex);
            }
        }
        return loader;
    }

    public void goMain(String viewName) {
        try {
            Parent root = FXMLLoader.load(App.class.getResource("/view/" + viewName + ".fxml"), idioma);
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (IOException ex) {
            Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Error opening main view: " + viewName, ex);
        }
    }

    public void goViewInStage(String viewName, Stage stage) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setStage(stage);
        stage.getScene().setRoot(loader.getRoot());
    }

    public void goViewInWindow(String viewName) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        Stage stage = new Stage();
        stage.setOnHidden((WindowEvent event) -> controller.setStage(null));
        controller.setStage(stage);
        Scene scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void goViewInWindowModal(String viewName, Stage parentStage, boolean resizable) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.initialize();
        Stage stage = new Stage();
        stage.setResizable(resizable);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setOnHidden((WindowEvent event) -> controller.setStage(null));
        controller.setStage(stage);
        Scene scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.showAndWait();
    }

    public Controller getController(String viewName) {
        return getLoader(viewName).getController();
    }

    public void goViewFromEvent(String viewName, ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/" + viewName + ".fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("Error opening view: " + viewName);
            ex.printStackTrace();
        }
    }

    public void exit() {
        if (mainStage != null) {
            mainStage.close();
        }
    }
}