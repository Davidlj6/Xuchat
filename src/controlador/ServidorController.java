package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorController implements Runnable{
	
	@FXML private TextArea areaChat;
	/*
	@FXML
	public void initialize() {
		Thread hilo = new Thread(this);
		hilo.start();
	}
	*/
	public ServidorController() {
		Thread hilo = new Thread(this);
		hilo.start();
	}
	
	//Hilo
	@Override
	public void run() {
		ServerSocket socketServidor = null;
		Socket socket = null, socketDestinatario =null;
		ObjectOutputStream mensajeReenvio = null;	
		try {
			
			String nombre, ip, mensaje;
			Mensaje mensajeRecibido;
			socketServidor = new ServerSocket(9999);
			
			while (true) {
				socket = socketServidor.accept();

				ObjectInputStream datosMensaje = new ObjectInputStream(socket.getInputStream());
				mensajeRecibido = (Mensaje) datosMensaje.readObject();
				
				nombre = mensajeRecibido.getNombre();
				ip = mensajeRecibido.getIp();
				mensaje = mensajeRecibido.getMensaje();

				areaChat.appendText("\n"+nombre + ": " + mensaje + " para " + ip);
				
				//Reenviar mensaje
				
				Socket enviaDestinatario = new Socket(ip, 9090);
				
				ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
				
				paqueteReenvio.writeObject(mensajeRecibido);
				
				paqueteReenvio.close();
				
				paqueteReenvio.close();
				
				socket.close();
				
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
}

