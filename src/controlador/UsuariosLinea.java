package controlador;

public class UsuariosLinea {
	private String nombre;
	private String ip;
	
	public UsuariosLinea(String nombre, String ip) {
		this.nombre=nombre;
		this.ip=ip;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
