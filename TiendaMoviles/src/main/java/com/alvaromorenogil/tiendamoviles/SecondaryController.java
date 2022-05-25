package com.alvaromorenogil.tiendamoviles;

import com.alvaromorenogil.tiendamoviles.entities.Marca;
import com.alvaromorenogil.tiendamoviles.entities.Movil;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.persistence.Query;
import javax.persistence.RollbackException;

public class SecondaryController implements Initializable{
    
    private Movil movil;
    private boolean nuevoMovil;
    
    private static final String CARPETA_FOTOS = "Fotos";

    
    @FXML
    private ComboBox<Marca> comboBoxMarca;
    @FXML
    private CheckBox checkBox5G;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldModelo;
    @FXML
    private TextField textFieldCapacidad;
    @FXML
    private TextField textFieldPantalla;
    @FXML
    private TextField textFieldPrecio;
    @FXML
    private TextField textFieldCamaras;
    @FXML
    private BorderPane rootSecondary;
    @FXML
    private ImageView imageViewFoto;
    
    
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){  
    }
    
    public void setMovil(Movil movil, boolean nuevoMovil) {
        App.em.getTransaction().begin();
        if (!nuevoMovil) {
            this.movil = App.em.find(Movil.class, movil.getId());
        } else {
            this.movil = movil;
        }
        this.nuevoMovil = nuevoMovil;
        
        mostrarDatos();
    }
    
    private void mostrarDatos(){
        textFieldNombre.setText(movil.getNombre());
        textFieldModelo.setText(movil.getModelo());
        textFieldCapacidad.setText(movil.getCapacidad());
        if (movil.getCamaras() != null) {
            textFieldCamaras.setText(String.valueOf(movil.getCamaras()));
        } 
        textFieldPantalla.setText(movil.getPantalla());
        textFieldPrecio.setText(movil.getPrecio());
        if (movil.getCincoG() != null) {
            checkBox5G.setSelected(movil.getCincoG());
        }
        
        Query queryMarcaFindAll = App.em.createNamedQuery("Marca.findAll");
        List<Marca> listMarca = queryMarcaFindAll.getResultList();
        
        comboBoxMarca.setItems(FXCollections.observableList(listMarca));
        if(movil.getMarca() != null){
            comboBoxMarca.setValue(movil.getMarca());
        }
        comboBoxMarca.setCellFactory((ListView<Marca> l) -> new ListCell<Marca>(){
            @Override
            protected void updateItem (Marca marca, boolean empty){
                super.updateItem(marca, empty);
                if(marca == null || empty){
                    setText("");
                }else {
                    setText(marca.getId() + "-" + marca.getNombre());
                } 
            }
        });
        
        // FORMATO PARA EL VALOR MOSTRADO ACTUALMENTE COMO SELECCIONADO
        comboBoxMarca.setConverter(new StringConverter<Marca>(){
            @Override
            public String toString (Marca marca){
                if(marca == null ){
                    return null;
                }else {
                    return marca.getId() + "-" + marca.getNombre();
                } 
            }

            @Override
            public Marca fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        
        // GUARDAR FOTO EN LOS DETALLES DE CADA MÓVIL
        if(movil.getMarca().getImagen() != null){
            String imageFileName = movil.getMarca().getImagen();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if (file.exists()){
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encuentra la imágen");
                alert.showAndWait();
            }
        }
    }
    
    // BOTÓN GUARDAR
    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        boolean errorFormato = false;
        
        movil.setNombre(textFieldNombre.getText());
        movil.setModelo(textFieldModelo.getText());
        movil.setCapacidad(textFieldCapacidad.getText());
        movil.setPantalla(textFieldPantalla.getText());
        movil.setPrecio(textFieldPrecio.getText());
        if (movil.getCamaras() != null) {
            textFieldCamaras.setText(String.valueOf(movil.getCamaras()));
        } 
        movil.setCincoG(checkBox5G.isSelected());
        
        if (!errorFormato){
            try{
                if(movil.getId() == null){
                    System.out.println("Guardando nuevo movil en BD");
                    App.em.persist(movil);
                }else{
                    System.out.println("Actualizando movil en BD");
                    App.em.merge(movil);
                }
                App.em.getTransaction().commit();
                
                App.setRoot("primary");               
            }catch(RollbackException ex){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No se ha podido guardar los cambios. "
                    + "Compruebe que los datos cumplen los requisitos");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            } catch (IOException ex) {               
                //NO SE HA PODIDO CARGAR LA VENTANA PRIMARY
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    // BOTÓN CANCELAR
    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        App.em.getTransaction().rollback();
        
        try{
            App.setRoot("primary");
        }catch (IOException ex){
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // BOTÓN EXAMINAR
    @FXML
    private void onActionButtonExaminar(ActionEvent event) throws IOException {
        File carpertaFotos = new File(CARPETA_FOTOS);
        if(!carpertaFotos.exists()){
            carpertaFotos.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes (jpg, jpeg, png)", "*.png"),
                 new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        
        File file = fileChooser.showOpenDialog(rootSecondary.getScene().getWindow());
        if (file != null){
            try{
                Files.copy(file.toPath(), new File(CARPETA_FOTOS + "/" + file.getName()).toPath());
                movil.getMarca().setImagen(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } catch (FileAlreadyExistsException ex){
                Alert alert = new Alert(Alert.AlertType.WARNING,"Nombre de archivo duplicado.");
                alert.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING,"No se ha podido guardar la imagen.");
                alert.showAndWait();
            }
        }
    }
    
    // BOTÓN SUPRIMIR
    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar supresion ed imagen.");
        alert.setHeaderText("¿Desea SUPRIMIR el archivo asociado a la imagen, \n"
                + "quitar la foto pero MANTENER el archivo, \no CANCELAR la operación?");
        alert.setContentText("Elija a opción deseada.");
        
        ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
        ButtonType buttonTypeMantener = new ButtonType("Mantener");
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeMantener, buttonTypeCancelar);
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == buttonTypeEliminar) {
            String imageFileName = movil.getMarca().getImagen();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if(file.exists()){
                file.delete();
            }
            movil.getMarca().getImagen();
            imageViewFoto.setImage(null);
        } else if (result.get() == buttonTypeMantener){
            movil.getMarca().getImagen();
            imageViewFoto.setImage(null);
        }
    }
}