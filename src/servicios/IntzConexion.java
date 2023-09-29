package servicios;

import java.sql.Connection;

public interface IntzConexion {

	// Método para generar la conexion con la base de datos
	public Connection generaConexion();
}
