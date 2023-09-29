package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;

public class ImplConexion implements IntzConexion {

    // Método para generar una conexión a la base de datos
    @Override
    public Connection generaConexion() {

        Connection conexion = null;
        String[] parametrosConexion = configuracionConexion();

        // Verificar si los parámetros de conexión no están vacíos
        if (!parametrosConexion[2].isEmpty()) {
            try {
                // Registrar el controlador JDBC de PostgreSQL
                Class.forName("org.postgresql.Driver");

                // Establecer la conexión con la base de datos PostgreSQL
                conexion = DriverManager.getConnection(parametrosConexion[0], parametrosConexion[1], parametrosConexion[2]);

                // Verificar si la conexión es válida
                boolean esValida = conexion.isValid(50000);
                if (!esValida) {
                    conexion = null;
                }

                // Mostrar información sobre la validez de la conexión
                System.out.println(esValida ? "[INFORMACIÓN-ConexionPostgresqlImplementacion-generaConexion] Conexión a PostgreSQL válida" : "[ERROR-ConexionPostgresqlImplementacion-generaConexion] Conexión a PostgreSQL no válida");
                return conexion;

            } catch (ClassNotFoundException cnfe) {
                // Capturar error si no se puede registrar el controlador
                System.out.println("[ERROR-ConexionPostgresqlImplementacion-generaConexion] Error en registro driver PostgreSQL: " + cnfe);
                return conexion = null;
            } catch (SQLException jsqle) {
                // Capturar error si no se puede establecer la conexión
                System.out.println("[ERROR-ConexionPostgresqlImplementacion-generaConexion] Error en conexión a PostgreSQL (" + parametrosConexion[0] + "): " + jsqle);
                return conexion = null;
            }

        } else {
            // Informar si los parámetros de conexión no están configurados correctamente
            System.out.println("[ERROR-ConexionPostgresqlImplementacion-generaConexion] Los parámetros de conexión no se han establecido correctamente");
            return conexion;
        }

    }

    // Método para obtener los parámetros de conexión desde un archivo de configuración
    private String[] configuracionConexion() {

        String user = "", pass = "", port = "", host = "", db = "", url = "";

        // Cargar las propiedades de conexión desde un archivo de configuración
        Properties propiedadesConexionPostgresql = new Properties();
        try {
            // Ruta del archivo de configuración
            String filePath = "C:\\Users\\DaniT\\Clases\\EjercicioConexionbdCRUDtest\\src\\utils\\conexion.properties";
            propiedadesConexionPostgresql.load(new FileInputStream(new File(filePath)));

            // Obtener los parámetros de conexión desde el archivo de configuración
            user = propiedadesConexionPostgresql.getProperty("user");
            pass = propiedadesConexionPostgresql.getProperty("pass");
            port = propiedadesConexionPostgresql.getProperty("port");
            host = propiedadesConexionPostgresql.getProperty("host");
            db = propiedadesConexionPostgresql.getProperty("db");

            // Construir la URL de conexión a PostgreSQL
            url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

            // Almacenar los parámetros de conexión en un arreglo
            String[] stringConfiguracion = { url, user, pass };

            return stringConfiguracion;

        } catch (Exception e) {
            // Capturar error si no se puede acceder al archivo de configuración
            System.out.println("[ERROR-ConexionPostgresqlImplementacion-configuracionConexion] - Error al acceder al fichero propiedades de conexión.");
            return null;
        }

    }

}
