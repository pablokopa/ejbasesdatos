import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class BibliotecaApp {
    private JFrame frame;
    private JTextField txtTitulo, txtAutor, txtAnoPublicacion;
    private JCheckBox chkDisponible;
    private JTable table;
    private LibroDAO libroDAO;
    private LibroTableModel tableModel;

    public BibliotecaApp() {
        libroDAO = new LibroDAO();
        frame = new JFrame("Gestión de Biblioteca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2));
        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("Año de Publicación:"));
        txtAnoPublicacion = new JTextField();
        panelFormulario.add(txtAnoPublicacion);

        panelFormulario.add(new JLabel("Disponible:"));
        chkDisponible = new JCheckBox();
        panelFormulario.add(chkDisponible);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Libro libro = new Libro();
                    libro.setTitulo(txtTitulo.getText());
                    libro.setAutor(txtAutor.getText());
                    libro.setAnoPublicacion(Integer.parseInt(txtAnoPublicacion.getText()));
                    libro.setDisponible(chkDisponible.isSelected());
                    libroDAO.agregarLibro(libro);
                    cargarLibros();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panelFormulario.add(btnAgregar);

        frame.add(panelFormulario, BorderLayout.NORTH);

        tableModel = new LibroTableModel();
        table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBusqueda = new JPanel(new BorderLayout());
        JTextField txtBusqueda = new JTextField();
        panelBusqueda.add(txtBusqueda, BorderLayout.CENTER);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Libro> libros = libroDAO.consultarLibros("Titulo", txtBusqueda.getText());
                    tableModel.setLibros(libros);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panelBusqueda.add(btnBuscar, BorderLayout.EAST);
        frame.add(panelBusqueda, BorderLayout.SOUTH);

        cargarLibros();

        frame.setVisible(true);
    }

    private void cargarLibros() {
        try {
            List<Libro> libros = libroDAO.consultarLibros("Titulo", "");
            tableModel.setLibros(libros);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BibliotecaApp();
            }
        });
    }
}

