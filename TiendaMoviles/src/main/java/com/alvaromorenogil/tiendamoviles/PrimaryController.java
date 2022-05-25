package com.alvaromorenogil.tiendamoviles;

import com.alvaromorenogil.tiendamoviles.entities.Movil;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.persistence.Query;

public class PrimaryController implements Initializable {

    private Movil movilSeleccionado;
    @FXML
    private TableView<Movil> tableViewMoviles;
    @FXML
    private TableColumn<Movil, String> columnNombre;
    @FXML
    private TableColumn<Movil, String> columnModelo;
    @FXML
    private TableColumn<Movil, String> columnCapacidad;
    @FXML
    private TableColumn<Movil, String> columnCamaras;
    @FXML
    private TableColumn<Movil, String> columnPantalla;
    @FXML
    private TableColumn<Movil, String> columnPrecio;
    @FXML
    private TableColumn<Movil, String> column5G;
    @FXML
    private TableColumn<Movil, String> columnMarca;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldModelo;
    @FXML
    private TextField textFieldBuscar;
    @FXML
    private CheckBox checkBoxCoincide;
    @FXML
    private Button buttonBuscar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        columnCamaras.setCellValueFactory(new PropertyValueFactory<>("camaras"));
        columnPantalla.setCellValueFactory(new PropertyValueFactory<>("pantalla"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        column5G.setCellValueFactory(new PropertyValueFactory<>("cincoG"));
        columnMarca.setCellValueFactory(
                cellData -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if (cellData.getValue().getMarca() != null) {
                        String nombreMarc = cellData.getValue().getMarca().getNombre();
                        property.setValue(nombreMarc);
                    }
                    return property;
                });
        tableViewMoviles.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    movilSeleccionado = newValue;
                        if (movilSeleccionado != null){
                            textFieldNombre.setText(movilSeleccionado.getNombre());
                            textFieldModelo.setText(movilSeleccionado.getModelo());
                        }else{
                            textFieldNombre.setText("");
                            textFieldModelo.setText("");
                        }
                });
        
        // TECLA ENTER
        textFieldBuscar.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                buttonBuscar.fire();
                event.consume();
            }            
        });
            cargarTodosMoviles();
            
    }
    
    private void cargarTodosMoviles() {
        Query queryMovilFindAll = App.em.createNamedQuery("Movil.findAll");
        List<Movil> listMovil = queryMovilFindAll.getResultList();
        tableViewMoviles.setItems(FXCollections.observableArrayList(listMovil));
    }
    
    // BOTÓN GUARDAR NUEVO MÓVIL
    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        if (movilSeleccionado != null) {
            movilSeleccionado.setNombre(textFieldNombre.getText());
            movilSeleccionado.setModelo(textFieldModelo.getText());
            App.em.getTransaction().begin();
            App.em.merge(movilSeleccionado);
            App.em.getTransaction().commit();
            
            int numFilaSeleccionada = tableViewMoviles.getSelectionModel().getSelectedIndex();
            tableViewMoviles.getItems().set(numFilaSeleccionada, movilSeleccionado);
            TablePosition pos = new TablePosition(tableViewMoviles, numFilaSeleccionada, null);
            tableViewMoviles.getFocusModel().focus(pos);
            tableViewMoviles.requestFocus();
        }
    }

    // BOTÓN BORRAR MÓVIL
    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
        if (movilSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar");
            alert.setHeaderText("¿Deseas suprimir el siguiente registro?");
            alert.setContentText(movilSeleccionado.getNombre() + ""
                + movilSeleccionado.getModelo());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                App.em.getTransaction().begin();
                App.em.remove(movilSeleccionado);
                App.em.getTransaction().commit();
                tableViewMoviles.getItems().remove(movilSeleccionado);
                tableViewMoviles.getFocusModel().focus(null);
                tableViewMoviles.requestFocus();
            }else {
                int numFilaSeleccionada = tableViewMoviles.getSelectionModel().getSelectedIndex();
                tableViewMoviles.getItems().set(numFilaSeleccionada, movilSeleccionado);
                TablePosition pos = new TablePosition(tableViewMoviles, numFilaSeleccionada, null);
                tableViewMoviles.getFocusModel().focus(pos);
                tableViewMoviles.requestFocus();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }
        
    }

    // BOTÓN NUEVO MÓVIL
    @FXML
    private void onActionButtonNuevo(ActionEvent event) {
        try{
           App.setRoot("secondary");
           SecondaryController secondaryController = (SecondaryController)App.fxmlLoader.getController(); 
           movilSeleccionado = new Movil();
           secondaryController.setMovil(movilSeleccionado, true);
        }catch(IOException ex){
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    // BOTÓN EDITAR MÓVIL
    @FXML
    private void onActionButtonEditar(ActionEvent event) {
        if(movilSeleccionado != null) {
            try{
                App.setRoot("secondary");
                SecondaryController secondaryController = (SecondaryController)App.fxmlLoader.getController(); 
                secondaryController.setMovil(movilSeleccionado, false);
            }catch(IOException ex){
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }     
    }
    
    
    // BOTÓN BUSCAR NOMBRE
    @FXML
    private void onActionButtonBuscar(ActionEvent event) {
        if (!textFieldBuscar.getText().isEmpty()){
            if(checkBoxCoincide.isSelected()){
                Query queryMovilFindByNombre = App.em.createNamedQuery("Movil.findByNombre");
                queryMovilFindByNombre.setParameter("nombre", textFieldBuscar.getText());
                List<Movil> listMovil = queryMovilFindByNombre.getResultList();
                tableViewMoviles.setItems(FXCollections.observableArrayList(listMovil));
            } else {
                String strQuery = "SELECT * FROM Movil WHERE LOWER(nombre) LIKE ";
                strQuery += "\'%" + textFieldBuscar.getText().toLowerCase() + "%\'";
                Query queryMovilFindLikeNombre = App.em.createNativeQuery(strQuery, Movil.class);

                List<Movil> listMovil = queryMovilFindLikeNombre.getResultList();
                tableViewMoviles.setItems(FXCollections.observableArrayList(listMovil));
            }            
        } else {
            cargarTodosMoviles();
        }
        
    }
}

