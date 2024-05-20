import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class LibroTableModel extends AbstractTableModel {
    private List<Libro> libros;
    private String[] columnNames = {"ID", "Título", "Autor", "Año de Publicación", "Disponible"};

    public LibroTableModel() {
        libros = new ArrayList<>();
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return libros.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Libro libro = libros.get(rowIndex);
        switch (columnIndex) {
            case 0: return libro.getId();
            case 1: return libro.getTitulo();
            case 2: return libro.getAutor();
            case 3: return libro.getAnoPublicacion();
            case 4: return libro.isDisponible();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}