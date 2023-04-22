package co.uniquindio.pr2.agenda.application;

import java.io.IOException;

import co.uniquindio.pr2.agenda.controllers.MainSceneController;
import co.uniquindio.pr2.agenda.exceptions.ContactoException;
import co.uniquindio.pr2.agenda.model.Agenda;
import co.uniquindio.pr2.agenda.model.Contacto;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Aplicacion extends Application {

	private Stage primaryStage;
	private Agenda agenda;


	@Override

	public void start(Stage primaryStage) throws Exception{
		try {

			this.primaryStage = primaryStage;
			this.agenda = new Agenda("Mi Agenda",3,20,22);

			mostrarVentanaPrincipal();
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void mostrarVentanaPrincipal() throws IOException {

		try {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/co/uniquindio/pr2/agenda/views/MainSceneView.fxml"));
		AnchorPane anchorPane =(AnchorPane) loader.load();
		MainSceneController mainSceneController = loader.getController();
		mainSceneController.setAplicacion(this);

		Scene scene = new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		MainSceneController controller = loader.getController();
		controller.setStage(primaryStage);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public static void main(String[] args) {
		launch(args);
	}

//-------------------- METODOS DE CONTACTO -------------------------------//

	public boolean eliminarContacto(Contacto contactoSeleccion) throws ContactoException {
		return agenda.eliminarContacto(contactoSeleccion);
	}

	public boolean crearContacto(String nombre, String alias, String telefono, String email, String direccion) throws ContactoException {
		Contacto contacto= new Contacto(nombre, alias, direccion, telefono, email);
		return agenda.aniadirContacto(contacto);
	}



}
