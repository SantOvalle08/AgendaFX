package co.uniquindio.pr2.agenda.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import co.uniquindio.pr2.agenda.application.Aplicacion;
import co.uniquindio.pr2.agenda.exceptions.AgendaException;
import co.uniquindio.pr2.agenda.exceptions.ContactoException;
import co.uniquindio.pr2.agenda.model.Agenda;
import co.uniquindio.pr2.agenda.model.Contacto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sun.font.CreatedFontTracker;

public class MainSceneController implements Initializable{


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Contacto, String> columnNombre;

    @FXML
    private TableColumn<Contacto, String> columnEmail;

    @FXML
    private TableColumn<Contacto, String> columnCategoria;

    @FXML
    private TableColumn<Contacto, String> columnAlias;

    @FXML
    private TableColumn<Contacto, String> columnDireccion;

    @FXML
    private TableColumn<Contacto, String> columnTelefono;

    @FXML
    private Button btnAgendaLlena;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TableView<Contacto> tableViewAgenda;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtAlias;

    @FXML
    private Button btnAniadir;

    @FXML
    private Button btnHuecos;

    @FXML
    private Button btnBuscar;

	private Aplicacion aplicacion;

	private Agenda agenda;

	private Stage primaryStage;

	private Contacto contactoSeleccion;

	ObservableList <Contacto> listaContactos = FXCollections.observableArrayList();

    @FXML
    void consultarHuecos(ActionEvent event) {
    	String huecos = agenda.huecosLibre();
    	if(!huecos.equals(String.valueOf(-1))) {
    		mostrarMensaje("Huecos Libres", null, "Hay un total de "+huecos+" huecos disponibles", AlertType.INFORMATION);
    	} else {
    		mostrarMensaje("Error", null, "No se pudo consultar los huecos libres", AlertType.ERROR);

    	}
    }

    @FXML
    void buscarContacto(ActionEvent event) {
    	String nombre = txtNombre.getText();
    	String telefono = agenda.buscarTelefonoContacto(nombre);
    	if(telefono != null){
			mostrarMensaje("Notificación", "Contacto encontrado", telefono, AlertType.INFORMATION);

    	} else {
			mostrarMensaje("Notificación", "Contacto no encontrado", "El numero del contacto "+nombre+" no se encuentra,"
					+ " asegurese de que el contacto exista", AlertType.ERROR);
    	}
    }

    @FXML
    void aniadirContacto(ActionEvent event) throws ContactoException, AgendaException {

    	String nombre = txtNombre.getText();
    	String alias = txtAlias.getText();
    	String direccion = txtDireccion.getText();
    	String telefono = txtTelefono.getText();
    	String email = txtEmail.getText();

    	if(datosValidos(nombre,alias,direccion,telefono,email))
    	{
//    		Contacto contacto = new Contacto(nombre,alias,direccion,telefono,email);
    		crearContacto(nombre,alias, direccion,telefono,email);

    		txtNombre.setText("");
    		txtAlias.setText("");
    		txtTelefono.setText("");
    		txtEmail.setText("");
    		txtDireccion.setText("");
    	}

    }

	private boolean datosValidos(String nombre, String alias, String direccion, String telefono, String email) {
		String notificacion = "";

		if (nombre == null || nombre.equals(""))
			notificacion += "El nombre no es válido, por favor ingrese de nuevo\n";

		if (alias == null || alias.equals(""))
			notificacion += "El alias no es valido, por favor ingrese de nuevo\n";

		if (telefono == null || telefono.equals("")) {
			notificacion += "Campo de telefono erroneo, por favor ingrese de nuevo\n";
		} else if (!validarTelefono(telefono)) {
			notificacion += "El telefono debe de contener solo valores numéricos\n";
		}

		if(email == null || email.equals(""))
			notificacion+= "El E-mail no es valido, por favor ingrese de nuevo\n";


		if(direccion== null || direccion.equals(""))
			notificacion+= "La dirección no es valida, por favor ingrese de nuevo\n";


		if (!notificacion.equals("")) {
			mostrarMensaje("Notificación", "Contacto no creado", notificacion, AlertType.WARNING);
			return false;
		}

		return true;
	}

	private boolean validarTelefono(String telefono) {
	    boolean valido = true;
	    for (int i = 0; i < telefono.length(); i++) {
	        if (!Character.isDigit(telefono.charAt(i))) {
	            valido = false;
	            break;
	        }
	    }
	    return valido;
	}

	@FXML
    void eliminarContacto(ActionEvent event) throws ContactoException {

    	if(contactoSeleccion!=null){
    		int confirmacion= JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar este contacto?");
    		if(confirmacion==0){
	    		if(aplicacion.eliminarContacto(contactoSeleccion)){
	    			listaContactos.remove(contactoSeleccion);
	    			mostrarMensaje("Contacto eliminado", "Eliminacion de contacto", "Se ha eliminado el contacto con exito", AlertType.INFORMATION);
	    		}else{
	    			mostrarMensaje("Contacto eliminado", "Eliminacion de contacto", "No se ha podido eliminar el contacto", AlertType.WARNING);
	    		}
    		}

    	}else
    		mostrarMensaje("Contacto seleccion", "Contacto Seleccion", "No se ha seleccionado ningun contacto", AlertType.WARNING);
    }


    @FXML
    void verificarAgenda(ActionEvent event) {
        if (agenda.agendaLlena()) {
            // La agenda tiene espacios disponibles
        	mostrarMensaje("Notificacion", null, "La agenda está llena", AlertType.CONFIRMATION);
        } else {
        	mostrarMensaje("Notificacion", null, "La agenda NO está llena.", AlertType.INFORMATION);
        }
    }

	private void mostrarInformacionContacto() {
		if(contactoSeleccion != null) {
			txtNombre.setText(contactoSeleccion.getNombre());
			txtAlias.setText(contactoSeleccion.getAlias());
			txtDireccion.setText(contactoSeleccion.getDireccion());
			txtTelefono.setText(contactoSeleccion.getTelefono());
			txtEmail.setText(contactoSeleccion.getEmail());

			//Se desabilitan los textFields necesarios

			txtNombre.setDisable(true);
			txtTelefono.setDisable(true);
		}
	}

    @FXML
    void initialize() {
        assert btnAgendaLlena != null : "fx:id=\"btnAgendaLlena\" was not injected: check your FXML file 'MainSceneView.fxml'.";
        assert txtNombre != null : "fx:id=\"txtNombre\" was not injected: check your FXML file 'MainSceneView.fxml'.";
        assert txtDireccion != null : "fx:id=\"txtDireccion\" was not injected: check your FXML file 'MainSceneView.fxml'.";
        assert tableViewAgenda != null : "fx:id=\"tableViewAgenda\" was not injected: check your FXML file 'MainSceneView.fxml'.";
        assert btnEliminar != null : "fx:id=\"btnEliminar\" was not injected: check your FXML file 'MainSceneView.fxml'.";
        assert txtTelefono != null : "fx:id=\"txtTelefono\" was not injected: check your FXML file 'MainSceneView.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'MainSceneView.fxml'.";
        assert txtAlias != null : "fx:id=\"txtAlias\" was not injected: check your FXML file 'MainSceneView.fxml'.";
        assert btnAniadir != null : "fx:id=\"btnAniadir\" was not injected: check your FXML file 'MainSceneView.fxml'.";
        assert btnHuecos != null : "fx:id=\"btnHuecos\" was not injected: check your FXML file 'MainSceneView.fxml'.";
        assert btnBuscar != null : "fx:id=\"btnBuscar\" was not injected: check your FXML file 'MainSceneView.fxml'.";

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		this.columnAlias.setCellValueFactory(new PropertyValueFactory<>("alias"));
		this.columnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
		this.columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		this.columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		this.columnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

		tableViewAgenda.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null){
				contactoSeleccion= newSelection;
				mostrarInformacionContacto();
			}
		});

	}


    private void crearContacto(String nombre, String alias, String telefono, String email, String direccion) throws ContactoException, AgendaException{
    	if(aplicacion.crearContacto(nombre,alias,telefono,email,direccion)){
    		tableViewAgenda.getItems().clear();
    		tableViewAgenda.setItems(getContactos());
    		mostrarMensaje("Notificación Contacto", "Contacto creado", "Contacto agregado con exito", AlertType.INFORMATION);
		}else{
			mostrarMensaje("Notificación Contacto", "Contacto no creado", "El contacto no pudo ser creado", AlertType.WARNING);
		}

    }

	private void mostrarMensaje(String titulo, String header, String contenido, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(titulo);
		alert.setHeaderText(header);
		alert.setContentText(contenido);
		alert.showAndWait();
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
		this.agenda = aplicacion.getAgenda();

		tableViewAgenda.getItems().clear();
		tableViewAgenda.setItems(getContactos());
	}

	private ObservableList<Contacto> getContactos() {

		listaContactos.addAll(agenda.getListaContactos());
		return listaContactos;
	}

	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;

	}

}

