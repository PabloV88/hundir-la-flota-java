package Metodos;

import java.awt.*;
import javax.swing.*;

public class VentanaMenu {
    
    public void mostrar(String nombreUsuario) {
        JFrame frame = new JFrame("Centro de Mando - " + nombreUsuario);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnJugar = new JButton("Nueva Partida");
        JButton btnEstadisticas = new JButton("Ver Mis Estadísticas");
        JButton btnReglas = new JButton("Reglas");
        JButton btnSalir = new JButton("Cerrar Sesión");

        // ACCIÓN: VER ESTADÍSTICAS (Ventana Emergente)
        btnEstadisticas.addActionListener(e -> {
            String stats = GestorBaseDatos.obtenerEstadisticas(nombreUsuario);
            // El JOptionPane crea el Pop-Up automáticamente
            JOptionPane.showMessageDialog(frame, stats, "Perfil de " + nombreUsuario, JOptionPane.INFORMATION_MESSAGE);
        });

        // ACCIÓN: VER REGLAS (Ventana Emergente)
// ACCIÓN: VER REGLAS (Usando tu clase ReglasJuego)
        btnReglas.addActionListener(e -> {
            String reglas = ReglasJuego.obtenerReglasTexto();
            JOptionPane.showMessageDialog(frame, reglas, "Reglas", JOptionPane.INFORMATION_MESSAGE);
        });

        // ACCIÓN: CERRAR SESIÓN
        btnSalir.addActionListener(e -> {
            frame.dispose(); // Cierra el menú
            new VentanaLogin().mostrar(); // Vuelve a abrir el login
        });

        // ACCIÓN: JUGAR 
        btnJugar.addActionListener(e -> {
            frame.dispose(); // Cierra el menú visual
            try {
                new JugarMenu().menuJugar(nombreUsuario); // Vas a jugar a la consola
                
                // LA LÍNEA NUEVA: Cuando terminas de jugar, la ventana vuelve a abrirse
                new VentanaMenu().mostrar(nombreUsuario); 
                
            } catch (Exception ex) { }
        });

        frame.add(btnJugar);
        frame.add(btnEstadisticas);
        frame.add(btnReglas);
        frame.add(btnSalir);
        frame.setVisible(true);
    }
}
