package Metodos;

import Clases.Jugador;
import Clases.JugadorReal;
import java.io.*;

public class CargarPartida {
    // Metodo para guardar
    public static void guardarPartida(Jugador j1, Jugador j2) {
        try {
            FileOutputStream archivoGuardado = new FileOutputStream("ficheros/partida_guardada.dat");
            ObjectOutputStream escritor = new ObjectOutputStream(archivoGuardado);
            
            escritor.writeObject(j1);
            escritor.writeObject(j2);
            
            escritor.close();
            archivoGuardado.close();
            System.out.println("\n¡Partida guardada con éxito! Ya puedes cerrar el juego.");
        } catch (Exception e) {
            System.out.println("\n[Error] No se pudo guardar: " + e.getMessage());
        }
    }

    // Metodo para cargar
   public static Jugador[] cargarPartida() {
        try {
            FileInputStream archivoCargado = new FileInputStream("ficheros/partida_guardada.dat");
            ObjectInputStream lector = new ObjectInputStream(archivoCargado);
            
            // Sacamos a los jugadores
            Jugador j1 = (Jugador) lector.readObject();
            Jugador j2 = (Jugador) lector.readObject();
            
            lector.close();
            archivoCargado.close();
            
            // Le volvemos a enchufar el Scanner al jugador real (porque era transient)
            if (j1 instanceof JugadorReal) {
                ((JugadorReal) j1).setScanner(new java.util.Scanner(System.in));
            }
            if (j2 instanceof JugadorReal) {
                ((JugadorReal) j2).setScanner(new java.util.Scanner(System.in));
            }
            
            System.out.println("\n ¡Partida cargada! Retomando la batalla...\n");
            return new Jugador[]{j1, j2}; 
            
        } catch (Exception e) {
            System.out.println("\n[Error] No hay ninguna partida guardada o el archivo está corrupto.");
            return null;
        }
    }
}
