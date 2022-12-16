package modelo;

import controlador.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    ConexionBD conexion = new ConexionBD();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List listar(){
        String sql = "select * from productos";
        List<Producto> lista = new ArrayList<>();
        
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()){
                Producto producto = new Producto();
                producto.setCodigo(rs.getInt(1));
                producto.setNombre(rs.getString(2));
                producto.setPrecio(rs.getDouble(3));
                producto.setInventario(rs.getInt(4));
                lista.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta lista" + e);
        }
        return lista;
    }
    
    //Método agregar producto
    public void agregar(Producto producto){
        String sql = "insert into productos(nombre, precio, inventario) values(?, ?, ?)";
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getInventario());
            ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error al intentar agregar producto. " + e);
        }
    }
    
    //Método actualizar
    public void actualizar(Producto producto){
        String sql = "update productos set nombre=?, precio=?, inventario=? where codigo=?";
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getInventario());
            ps.setInt(4, producto.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto. " + e);
        }
    }
    
    //Método eliminar
    public void eliminar(int id){
        String sql = "delete from productos where codigo" + id;
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto. " + e);
        }
    }
}
