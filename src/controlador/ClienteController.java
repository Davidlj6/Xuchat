package controlador;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ClienteController implements Runnable {
	
	@FXML private ImageView btnExit, btnEnviar;
	@FXML private Pane pInicio, pChat;
	@FXML private Button btnIngresar;
	@FXML private TextField tfUsuario, tfContra, mensajeChat, tfIp;
	@FXML private ComboBox usuariosLinea;
	@FXML private Label lblError, lblError1, nombreUser, ipUser, lblUser;
	@FXML private TextArea campoChat;
	 
	//ObservableList<String> list;	<- lista usuarios online
	/*
	@FXML
	public void initialize() {
		
		try {
			
			Socket socket = new Socket("172.16.8.138", 9999);//Direccion ip donde se encuentra el servidor
			
			Mensaje datos = new Mensaje();
			
			datos.setMensaje("Online");
			
			ObjectOutputStream paqueteDatos = new ObjectOutputStream(socket.getOutputStream());
			paqueteDatos.writeObject(datos);
			
			socket.close();
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
	}
	*/
	
	public ClienteController() {
		Thread hilo1 = new Thread(this);
		hilo1.start();
	}
	

	@Override
	public void run() {
		try {
			
			ServerSocket servidorCliente = new ServerSocket(9090);
			Socket cliente;
			Mensaje mensajeRecibido;
				
			while (true) {
				
				cliente = servidorCliente.accept();
					
				ObjectInputStream flujoEntrada = new ObjectInputStream(cliente.getInputStream());
				
				mensajeRecibido=(Mensaje) flujoEntrada.readObject();
					
				campoChat.appendText("\n" + mensajeRecibido.getNombre() + ": " + mensajeRecibido.getMensaje());
			}
		
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}	
				
	}
	
	
	//Botón que envia el mensaje 
	public void enviar(MouseEvent ev) throws IOException {	
		
		try {
			Socket socket = new Socket("172.16.8.138", 9999);
			
			Mensaje mensaje = new Mensaje();
			mensaje.setNombre(lblUser.getText());
			mensaje.setIp(tfIp.getText());
			mensaje.setMensaje(mensajeChat.getText());
			
			ObjectOutputStream datosMensaje = new ObjectOutputStream(socket.getOutputStream());
			
			datosMensaje.writeObject(mensaje);

			campoChat.appendText("\n Yo: " + mensajeChat.getText());
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			mensajeChat.setText("");
		}
	}
	
	//Boton salir al inicio
	public void exit() {
		if (pChat.isVisible() == true) {
			pChat.setVisible(false);
			pInicio.setVisible(true);
			tfUsuario.setText("");
			tfContra.setText("");
		}	
	}
	
	
	//Comprueba si la contraseña es correcta
	public void ingresar(MouseEvent ev) {
		lblError1.setVisible(false);
		lblError.setVisible(false);
		if (tfContra.getText().equals("1234")) {
			if (tfUsuario.getText().equals("")) {
				lblError1.setVisible(true);
			} else {
				if (pInicio.isVisible() == true) {
					pChat.setVisible(true);
					pInicio.setVisible(false);
				
					lblUser.setText(tfUsuario.getText());
					lblError.setVisible(false);
					//usuariosLinea.setItems(list);
				}	
			}
		} else {
			lblError.setVisible(true);
		} 
	}

}