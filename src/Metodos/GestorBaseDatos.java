package Metodos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GestorBaseDatos {

    private static final String URL = "jdbc:sqlite:ficheros/usuarios_batalla.db";

private static Connection conectar() {
        Connection conexion = null;
        try {
            // Fuerza a Java a buscar el archivo .jar
            Class.forName("org.sqlite.JDBC"); 
            
            conexion = DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.out.println("[Error BD] " + e.getMessage());
        }
        return conexion;
    }
    // Crea la tabla de usuarios
    public static void inicializarBaseDatos() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                   + "nombre_usuario TEXT PRIMARY KEY, "
                   + "password TEXT NOT NULL, "
                   + "ganadas INTEGER DEFAULT 0, "
                   + "perdidas INTEGER DEFAULT 0, "
                   + "mejor_partida INTEGER DEFAULT 999" // 999 disparos como valor por defecto
                   + ");";
        
        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) { }
    }

    // Registra un nuevo usuario
    public static boolean registrarUsuario(String nombre, String password) {
        String sql = "INSERT INTO usuarios(nombre_usuario, password) VALUES(?, ?)";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true; // Se registró con éxito
        } catch (Exception e) {
            // Si el usuario ya existe, SQLite lanzará un error y devolveremos false
            return false; 
        }
    }

    // 3. INICIAR SESIÓN (Comprobar contraseña)
    public static boolean iniciarSesion(String nombre, String password) {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND password = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Devuelve true si encontró una coincidencia exacta
        } catch (Exception e) {
            return false;
        }
    }

    // 4. CAMBIAR CONTRASEÑA
    public static boolean cambiarContrasena(String nombre, String nuevaPassword) {
        String sql = "UPDATE usuarios SET password = ? WHERE nombre_usuario = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevaPassword);
            pstmt.setString(2, nombre);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0; // True si se cambió correctamente
        } catch (Exception e) {
            return false;
        }
    }

    // 5. ACTUALIZAR ESTADÍSTICAS AL TERMINAR LA PARTIDA
    public static void registrarResultado(String nombre, boolean esVictoria, int disparosUsados) {
        String sqlUpdate = "";

        if (esVictoria) {
            sqlUpdate = "UPDATE usuarios SET ganadas = ganadas + 1, mejor_partida = MIN(mejor_partida, ?) WHERE nombre_usuario = ?";
        } else {
            sqlUpdate = "UPDATE usuarios SET perdidas = perdidas + 1 WHERE nombre_usuario = ?";
        }

        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            if (esVictoria) {
                pstmt.setInt(1, disparosUsados);
                pstmt.setString(2, nombre);
            } else {
                pstmt.setString(1, nombre);
            }
            pstmt.executeUpdate();
        } catch (Exception e) { }
    }
    
    // 6. VER PERFIL DEL JUGADOR
    public static void verPerfil(String nombre) {
        String sql = "SELECT ganadas, perdidas, mejor_partida FROM usuarios WHERE nombre_usuario = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("\n === PERFIL DE COMANDANTE: " + nombre.toUpperCase() + " ===");
                System.out.println(" > Partidas Ganadas:  " + rs.getInt("ganadas"));
                System.out.println(" > Partidas Perdidas: " + rs.getInt("perdidas"));
                
                int mejor = rs.getInt("mejor_partida");
                if (mejor == 999) {
                    System.out.println(" > Mejor Partida: Aún no ha ganado ninguna batalla.");
                } else {
                    System.out.println(" > Mejor Partida: Ganó en solo " + mejor + " disparos.");
                }
                System.out.println(" =======================================\n");
            }
        } catch (Exception e) { }
    }
    
    // Devuelve las estadísticas como Texto para el Pop-Up
    public static String obtenerEstadisticas(String nombre) {
        String sql = "SELECT ganadas, perdidas, mejor_partida FROM usuarios WHERE nombre_usuario = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int ganadas = rs.getInt("ganadas");
                int perdidas = rs.getInt("perdidas");
                int mejor = rs.getInt("mejor_partida");
                String textoMejor = (mejor == 999) ? "Aún no has ganado ninguna." : mejor + " disparos.";
                
                return "Comandante: " + nombre.toUpperCase() + "\n\n" +
                       "Partidas Ganadas: " + ganadas + "\n" +
                       "Partidas Perdidas: " + perdidas + "\n" +
                       "Mejor Partida: " + textoMejor;
            }
        } catch (Exception e) { }
        return "No se encontraron estadísticas.";
    }
}