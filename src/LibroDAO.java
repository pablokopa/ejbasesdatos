import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    public void agregarLibro(Libro libro) throws SQLException {
        String sql = "INSERT INTO Libros (Titulo, Autor, AnoPublicacion, Disponible) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setInt(3, libro.getAnoPublicacion());
            pstmt.setBoolean(4, libro.isDisponible());
            pstmt.executeUpdate();
        }
    }

    public void actualizarLibro(Libro libro) throws SQLException {
        String sql = "UPDATE Libros SET Titulo = ?, Autor = ?, AnoPublicacion = ?, Disponible = ? WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setInt(3, libro.getAnoPublicacion());
            pstmt.setBoolean(4, libro.isDisponible());
            pstmt.setInt(5, libro.getId());
            pstmt.executeUpdate();
        }
    }

    public void eliminarLibro(int id) throws SQLException {
        String sql = "DELETE FROM Libros WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Libro> consultarLibros(String criterio, String valor) throws SQLException {
        String sql = "SELECT * FROM Libros WHERE " + criterio + " ILIKE ?";
        List<Libro> libros = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + valor + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("ID"));
                libro.setTitulo(rs.getString("Titulo"));
                libro.setAutor(rs.getString("Autor"));
                libro.setAnoPublicacion(rs.getInt("AnoPublicacion"));
                libro.setDisponible(rs.getBoolean("Disponible"));
                libros.add(libro);
            }
        }
        return libros;
    }
}
