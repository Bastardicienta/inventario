package controlador;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.ProductoDAO;
import vista.Interfaz;

public class ControladorProducto implements ActionListener{
    Producto producto = new Producto();
    ProductoDAO productodao = new ProductoDAO();
    Interfaz vista = new Interfaz();
    DefaultTableModel modeloTabla = new DefaultTableModel();
    
    //Variables globales para recibir las cadenas de las cajas de texto.
    private int codigo = 0;
    private String nombre;
    private double precio;
    private int inventario;
    
    public ControladorProducto (Interfaz vista){
        this.vista = vista;
        vista.setVisible(true);
        agregarEventos();
        listarTabla();
    }
    
    public void agregarEventos() {
        vista.getBtnAgregar().addActionListener(this);
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnBorrar().addActionListener(this);
        vista.getBtnLimpiar().addActionListener(this);
        vista.getTblTabla().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                llenarCampos(e);
            }
        });
    }
    
    private void listarTabla() {
        String [] titulos = new String[] {"Código", "Nombre", "Precio", "Inventario"};
        modeloTabla = new DefaultTableModel(titulos, 0);
        List<Producto> listaProductos = productodao.listar();
        
        for (Producto producto : listaProductos) {
            modeloTabla.addRow(new Object[] {producto.getCodigo(), producto.getNombre(),
                producto.getPrecio(), producto.getInventario()});
        }
        
        vista.getTblTabla().setModel(modeloTabla);
        vista.getTblTabla().setPreferredSize(new  Dimension(350, modeloTabla.getRowCount() * 16));
    }
    
    public void llenarCampos(MouseEvent e) {
        JTable target = (JTable) e.getSource();
        codigo = (int)vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 0);
        vista.getTxtNombre().setText(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 1).toString());
        vista.getTxtPrecio().setText(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 2).toString());
        vista.getTxtInventario().setText(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 3).toString());
    }
    
    //Validar interfaz
    private boolean validarDatos() {
        if("".equals(vista.getTxtNombre().getText()) || "".equals(vista.getTxtPrecio().getText()) || "".equals(vista.getTxtInventario().getText())){
            JOptionPane.showMessageDialog(null, "Cuidado, los campos no pueden quedar vacíos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    /*Método 3 en 1:
    1. Cargar variables globales
    2. Parsear los valores
    3. Validar que precio e inventario sean numéricos*/
    private boolean cargarDatos(){
        try {
            nombre = vista.getTxtNombre().getText();
            precio = Double.parseDouble(vista.getTxtPrecio().getText());
            inventario = Integer.parseInt(vista.getTxtInventario().getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Los campos 'Precio' e 'inventario' son campos numéricos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    //método limpiar
    private  void limpiarCampos(){
        vista.getTxtNombre().setText("");
        vista.getTxtPrecio().setText("");
        vista.getTxtInventario().setText("");
        
        codigo = 0;
        nombre = "";
        precio = 0;
        inventario = 0;
    }

    //Método agregar producto 
    private void agregarProducto(){
        try {
            if(validarDatos()){
                if(cargarDatos()){
                    Producto producto = new Producto(nombre, precio, inventario);
                    productodao.agregar(producto);
                    JOptionPane.showMessageDialog(null, "Registro en base de datos exitoso.");
                    limpiarCampos();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al agregar" + e);
        }
        finally{
            listarTabla();
        }
    }
    
    //Método actualizar producto
    private void actualizarProducto(){
        try {
            if(validarDatos()){
                if(cargarDatos()){
                    Producto producto = new Producto(codigo, nombre, precio, inventario);
                    productodao.actualizar(producto);
                    JOptionPane.showMessageDialog(null, "Actualización exitosa");
                    limpiarCampos();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar" + e);
        }
        finally{
            listarTabla();
        }
    }
    
    //Método eliminar producto
    public void eliminarProducto(){
        try {
            if (codigo != 0){
                productodao.eliminar(codigo);
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
                    limpiarCampos();
            }
            else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally{
            listarTabla();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource()== vista.getBtnAgregar()){
            agregarProducto();
        }
        if (ae.getSource()== vista.getBtnActualizar()){
            actualizarProducto();
        }
        if (ae.getSource()== vista.getBtnBorrar()){
            eliminarProducto();
        }
        if (ae.getSource()== vista.getBtnLimpiar()){
            limpiarCampos();
        }
    }
}
