package Metodos;

import java.util.ArrayList;

public class Utilidades {

    // Recibe un ArrayList de cualquier tipo de objeto y un título para mostrarlo por consola.
    public static <T> void imprimirLista(ArrayList<T> lista, String titulo) {
        System.out.println("\n--- " + titulo + " ---");
        
        // Comprobamos si la colección no tiene registros
        if (lista.isEmpty()) {
            System.out.println(" La lista está vacía.");
        } else {
            for (T elemento : lista) {
                System.out.println("  > " + elemento.toString()); 
            }
        }
        System.out.println("---------------------------\n");
    }
}