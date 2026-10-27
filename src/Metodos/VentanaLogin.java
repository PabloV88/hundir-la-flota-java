package Metodos;

import java.awt.*;
import javax.swing.*;

public class VentanaLogin {
    
    public void mostrar() {
        GestorBaseDatos.inicializarBaseDatos(); // Arranca la BBDD

        // 1. Creamos la ventana (Ahora con 6 filas en lugar de 5 para que quepa el botón)
        JFrame frame = new JFrame("Hundir la Flota - Login");
        frame.setSize(350, 290); // Un poco más alta
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centra la ventana en pantalla
        frame.setLayout(new GridLayout(6, 1, 5, 5)); // 6 filas

        // 2. Creamos los campos de texto
        JPanel panelUsuario = new JPanel();
        panelUsuario.add(new JLabel("Usuario: "));
        JTextField txtUser = new JTextField(15);
        panelUsuario.add(txtUser);

        JPanel panelPass = new JPanel();
        panelPass.add(new JLabel("Contraseña: "));
        JPasswordField txtPass = new JPasswordField(15);
        panelPass.add(txtPass);

        // 3. Creamos los botones
        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistro = new JButton("Registrar Nuevo Usuario");
        JButton btnCambiarPass = new JButton("Cambiar Contraseña"); 

        // 4. Acción al hacer CLIC en Iniciar Sesión
        btnLogin.addActionListener(e -> {
            String u = txtUser.getText();
            String p = new String(txtPass.getPassword());
            
            if (GestorBaseDatos.iniciarSesion(u, p)) {
                JOptionPane.showMessageDialog(frame, "¡Bienvenido al centro de mando, " + u + "!");
                frame.dispose(); // Cierra la ventana de login
                new VentanaMenu().mostrar(u); // Abre el Menú Principal visual
            } else {
                JOptionPane.showMessageDialog(frame, "Error: Credenciales incorrectas", "Fallo", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 5. Acción al hacer CLIC en Registrarse
        btnRegistro.addActionListener(e -> {
            String u = txtUser.getText();
            String p = new String(txtPass.getPassword());
            
            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Rellena los campos primero para registrarte.");
                return;
            }
            if (GestorBaseDatos.registrarUsuario(u, p)) {
                JOptionPane.showMessageDialog(frame, "¡Registrado con éxito! Ya puedes darle a Iniciar Sesión.");
            } else {
                JOptionPane.showMessageDialog(frame, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 6. ACCIÓN al hacer CLIC en Cambiar Contraseña
        btnCambiarPass.addActionListener(e -> {
            String u = txtUser.getText();
            String p = new String(txtPass.getPassword());
            
            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Escribe tu usuario existente y tu NUEVA contraseña.");
                return;
            }
            if (GestorBaseDatos.cambiarContrasena(u, p)) {
                JOptionPane.showMessageDialog(frame, "¡Contraseña actualizada con éxito! Ya puedes iniciar sesión.");
            } else {
                JOptionPane.showMessageDialog(frame, "No se encontró al usuario. Asegúrate de escribirlo bien.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Añadimos todo a la ventana en orden
        frame.add(new JLabel("Identifícate para entrar en combate", SwingConstants.CENTER));
        frame.add(panelUsuario);
        frame.add(panelPass);
        frame.add(btnLogin);
        frame.add(btnRegistro);
        frame.add(btnCambiarPass); // Metemos el nuevo botón al final
        frame.setVisible(true);
    }
}