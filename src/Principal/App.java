package Principal;

import Metodos.VentanaLogin;

public class App {
    public static void main(String[] args) throws Exception {
        // Arrancamos la nueva interfaz gráfica de Swing
        VentanaLogin loginVisual = new VentanaLogin();
        loginVisual.mostrar();
    }
}