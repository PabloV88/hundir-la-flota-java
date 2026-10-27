package Metodos;

import Clases.Jugador;
import Clases.JugadorReal;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Jugar2Jugadores {

    public void dosJugadores(String nombreUsuario) throws Exception {
        Scanner sc = new Scanner(System.in);

        limpiarPantalla();
        String Nj1 = nombreUsuario; // El Jugador 1 es el dueño de la cuenta
        System.out.println("Jugador 1: " + Nj1);
        
        System.out.print("\nIntroduce el nombre del Jugador 2 (Invitado): ");
        String Nj2 = sc.nextLine();
        
        limpiarPantalla();
        System.out.println("\n--- NUEVA PARTIDA: "+Nj1+" VS "+Nj2+" ---");
        
        Jugador j1 = new JugadorReal(Nj1);
        Jugador j2 = new JugadorReal(Nj2);

        // 1. FASE DE COLOCACIÓN 
        
        // Turno J1
        System.out.println("\nTURNO DE " + j1.getNombre() + " PARA COLOCAR BARCOS.");
        System.out.println(j2.getNombre()+", por favor, NO MIRES LA PANTALLA.");
        esperarEnter(sc);
        
        j1.colocarBarcos();
        limpiarPantalla();

        // Turno J2
        System.out.println("\nTURNO DE " + j2.getNombre() + " PARA COLOCAR BARCOS.");
        System.out.println(j1.getNombre()+", por favor, NO MIRES LA PANTALLA.");
        esperarEnter(sc);
        
        j2.colocarBarcos();
        limpiarPantalla();

        // 2. BUCLE DE BATALLA
        boolean juegoActivo = true;
        Jugador atacante = j1;
        Jugador defensor = j2;

        while (juegoActivo) {
            System.out.println("\n-----------------------------------------");
            System.out.println("TURNO DE ATAQUE: " + atacante.getNombre());
            System.out.println("-----------------------------------------");

            System.out.println("\n--- MAPA DE " + defensor.getNombre() + " (Tus disparos) ---");
            defensor.getTablero().mostrarTablero(false);

            try {
                atacante.disparar(defensor.getTablero());
            } catch (Exception e) {
                if (e.getMessage().equals("COMANDO_GUARDAR")) {
                    CargarPartida.guardarPartida(j1, j2); 
                    break; 
                }
                System.out.println(e.getMessage());
                continue; 
            }

            if (!defensor.getTablero().quedanBarcosVivos()) {
                System.out.println("\n\u001B[32m*********************************");
                System.out.println("\u001B[32m   ¡VICTORIA PARA " + atacante.getNombre().toUpperCase() + "!");
                System.out.println("\u001B[32m*********************************\u001B[0m\n");
                juegoActivo = false;
            } else {
                System.out.println("\nFin del turno. Pulsa ENTER y pasa el ordenador.");
                sc.nextLine(); 
                
                Jugador temp = atacante;
                atacante = defensor;
                defensor = temp;
            }
        }

        try {
            FileWriter escritor = new FileWriter("ficheros/historial_partida.txt");
            escritor.write("=======================================\n");
            escritor.write("   RESUMEN DEL DUELO: " + j1.getNombre() + " VS " + j2.getNombre() + "\n");
            escritor.write("=======================================\n\n");
            
            escritor.write("--- DISPAROS DE " + j1.getNombre().toUpperCase() + " ---\n");
            for (String tiro : j1.getHistorialDisparos()) {
                escritor.write(tiro + "\n");
            }
            
            escritor.write("\n---------------------------------------\n\n");
            
            escritor.write("--- DISPAROS DE " + j2.getNombre().toUpperCase() + " ---\n");
            for (String tiro : j2.getHistorialDisparos()) {
                escritor.write(tiro + "\n");
            }
            
            escritor.close(); 
            System.out.println("\nEl informe completo del duelo se ha guardado en ficheros/historial_partida.txt");
            
        } catch (IOException e) {
            System.out.println("\n[Error] No se ha podido guardar el historial en el txt.");
        }
    }

    private void limpiarPantalla() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private void esperarEnter(Scanner sc) {
        System.out.print("Presiona ENTER cuando estés listo...");
        sc.nextLine();
    }
}