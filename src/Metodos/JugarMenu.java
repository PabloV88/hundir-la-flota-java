package Metodos;

import java.util.Scanner;

public class JugarMenu {
    
    // Ahora recibe el nombre de usuario
    public void menuJugar(String nombreUsuario) throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        int opcion;

        while (!salir) {
            try { 
                System.out.println("\n--- Elija el número de jugadores ---");
                System.out.println("1- 1 Jugador");
                System.out.println("2- 2 Jugadores");
                System.out.println("3- Volver");
                System.out.print("Elija una opcion: ");
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        Jugar1Jugador uno = new Jugar1Jugador();
                        uno.unJugador(nombreUsuario); // Le pasa el nombre al modo de 1 Jugador
                        break;

                    case 2:
                        Jugar2Jugadores dos = new Jugar2Jugadores();
                        dos.dosJugadores(nombreUsuario); // Le pasa el nombre al modo de 2 Jugadores
                        break;

                    case 3:
                        salir = true;
                        break;
                        
                    default:
                        System.out.println("Opcion incorecta, introduce un valor valido.");
                        break;
                }
            } catch (NumberFormatException nfe){ 
                System.out.println("Error, el valor introducido debe de ser un número");
            }
        }
    }
}