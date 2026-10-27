package Metodos;

import Clases.Jugador;
import Clases.JugadorBot;
import java.util.Scanner;

public class MenuPrincipal {

    // 1. EL ARREGLO: Ahora recibe el String 'nombreUsuario'
    public void iniciarMenu(String nombreUsuario) throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        int opcion;

        while (!salir) {
            try { 
                // 2. PERSONALIZAMOS EL MENÚ CON SU NOMBRE
                System.out.println("\n--- CENTRO DE MANDO: " + nombreUsuario.toUpperCase() + " ---");
                System.out.println("1- Nueva Partida");
                System.out.println("2- Cargar Partida");
                System.out.println("3- Ver Mis Estadísticas"); // NUEVA OPCIÓN
                System.out.println("4- Reglas");
                System.out.println("5- Cerrar Sesión");
                System.out.print("Elija una opcion: ");
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        JugarMenu jugar = new JugarMenu();
                        jugar.menuJugar(nombreUsuario); // Pasa el nombre a la siguiente pantalla
                        break;

                    case 2: 
                        Jugador[] cargados = CargarPartida.cargarPartida(); 
                        if (cargados != null) {
                            Jugador j1 = cargados[0];
                            Jugador j2 = cargados[1];
                            
                            if (j2 instanceof JugadorBot) {
                                System.out.println("\n[Retomando partida de 1 Jugador...]");
                                Jugar1Jugador partida = new Jugar1Jugador();
                                partida.continuarPartida(j1, j2); 
                            } else {
                                System.out.println("\n[Todavía no hemos adaptado el modo 2 jugadores]");
                            }
                        }
                        break;

                    case 3: 
                        // 3. MOSTRAMOS EL PERFIL CON LOS DATOS DE LA BASE DE DATOS
                        GestorBaseDatos.verPerfil(nombreUsuario);
                        break;

                    case 4:
                        ReglasJuego.mostrarReglas();
                        System.out.println("Para salir al menu, pulse la tecla Enter.");
                        sc.nextLine();
                        break;

                    case 5:
                        System.out.println("Cerrando sesión de " + nombreUsuario + "...");
                        salir = true; // Esto romperá el bucle y nos devolverá al MenuLogin
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