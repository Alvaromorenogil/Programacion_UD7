package com.alvaromorenogil.tiendamoviles;

import com.alvaromorenogil.tiendamoviles.entities.Movil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static EntityManager em;
    public static FXMLLoader fxmlLoader;
    
    @Override
    public void start(Stage stage) throws IOException {
        
        // CONEXIÓN CON LA BBDD
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiendaMovilesPU");
            em = emf.createEntityManager();
        } catch(PersistenceException ex){
            Logger.getLogger(App.class.getName()).log(Level.WARNING, ex.getMessage(), ex);
            
            // ALERTA
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No se ha podidio abrir la base de datos\n"
                + "Compruebe si la aplicación ya se encuentra abierta.");
            alert.showAndWait();
        }
        
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
        
//        Movil m = new Movil(0, "Apple", "12", "128 G", "7.60 cm", "16.02 cm", "0.85 cm", "200 g", "A14", "6", "IOS", "500.00 €");
//        em.getTransaction().begin();
//        em.persist(m);
//        em.getTransaction().commit();
    }

    @Override
    public void stop() throws Exception {
        em.close();
        try {
            DriverManager.getConnection("jdbc:derby:BDTiendaMoviles;shutdown=true");
        } catch(SQLException ex){
        }
    }
    
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}