package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBD {
    Connection con;
    String driver = "org.mariadb.jdbc.Driver";
    String dbName = "productos";
    String url = "jdbc:mariadb://localhost:3306/"+dbName;
    String usuario = "root";
    String clave = "123654";
    
    public Connection conectarBaseDatos (){
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, usuario, clave);
            System.out.println("Conexión a la base de datos exitosa");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error a la conexión en la base de datos: " + e);
        }
        return con;
    }
}

class prueba{
    public static void main(String[] args) {
        Connection con;
        ConexionBD conexion = new ConexionBD();
        con = conexion.conectarBaseDatos();
    }
}
