package Metodos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Clase encargada de mostrar las reglas del juego.

public class ReglasJuego {

    public static void mostrarReglas() {
        try {
            // Abrimos el archivo de texto
            BufferedReader lector = new BufferedReader(new FileReader("ficheros/reglas.txt"));
            String linea;
            
            System.out.println(); // Un salto de línea para que quede limpio
            
                // Leemos línea por línea hasta que no quede nada
            while ((linea = lector.readLine()) != null) {
                    System.out.println(linea); // Imprimimos cada línea
            }
            
            // Cerramos el archivo 
            lector.close();
            System.out.println();
            
        } catch (IOException e) {
            // Si el archivo se ha borrado o no se encuentra, avisamos para que no explote
            System.out.println("Error: No se ha podido cargar el archivo 'reglas.txt'.");
        }
    }
        // Devuelve las reglas juntas en un bloque de texto para la ventana
    public static String obtenerReglasTexto() {
        try {
            // Lee todo el archivo de golpe y lo devuelve
            return java.nio.file.Files.readString(java.nio.file.Paths.get("ficheros/reglas.txt"));
        } catch (IOException e) {
            return "Error: No se ha podido cargar el archivo 'reglas.txt'.";
        }
    }
}