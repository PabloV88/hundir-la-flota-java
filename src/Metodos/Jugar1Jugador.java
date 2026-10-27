package Metodos;

import Clases.Jugador;
import Clases.JugadorBot;
import Clases.JugadorReal;
import java.io.FileWriter;
import java.io.IOException;

public class Jugar1Jugador {

    // Recibe el nombre del usuario registrado
    public void unJugador(String nombreUsuario) throws Exception {
        System.out.println("\n--- NUEVA PARTIDA: " + nombreUsuario.toUpperCase() + " VS BOT ---");
        
        Jugador humano = new JugadorReal(nombreUsuario);
        Jugador bot = new JugadorBot();

        humano.colocarBarcos(); 
        
        System.out.println("\n---------------------------------------------");
        System.out.println("El enemigo está movilizando su flota...");
        esperar(2000); 
        bot.colocarBarcos(); 

        continuarPartida(humano, bot); 
    }

    public void continuarPartida(Jugador humano, Jugador bot) throws Exception {
        boolean juegoActivo = true;

        while (juegoActivo) {
            
            System.out.println("\n--- MAPA ENEMIGO ---");
            bot.getTablero().mostrarTablero(false); 
            
            try {
                humano.disparar(bot.getTablero()); 
            } catch (Exception e) {
                if (e.getMessage().equals("COMANDO_GUARDAR")) { 
                    CargarPartida.guardarPartida(humano, bot); 
                    break; 
                }
                System.out.println(e.getMessage());
                continue; 
            }

            if (!bot.getTablero().quedanBarcosVivos()) {
                System.out.println("\n\u001B[32m *********************************");
                System.out.println("\u001B[32m   ¡VICTORIA! HAS HUNDIDO LA FLOTA ENEMIGA");
                System.out.println("\u001B[32m*********************************\u001B[0m\n");
                
                // Registra la victoria
                GestorBaseDatos.registrarResultado(humano.getNombre(), true, humano.getHistorialDisparos().size());
                
                juegoActivo = false;
                continue; 
            }

            esperar(1000); 
            bot.disparar(humano.getTablero());
            
            System.out.println("\n--- TU FLOTA ---");
            humano.getTablero().mostrarTablero(true); 

            if (!humano.getTablero().quedanBarcosVivos()) {
                System.out.println("\n\u001B[31m*********************************");
                System.out.println("\u001B[31m   DERROTA... TU FLOTA HA CAÍDO");
                System.out.println("\u001B[31m*********************************\u001B[0m\n");
                
                // Registra la derrota
                GestorBaseDatos.registrarResultado(humano.getNombre(), false, 0);
                
                juegoActivo = false;
            }
            
            if (juegoActivo) esperar(2000); 
        }
        
        // Guardar historial al terminar
        try {
            FileWriter escritor = new FileWriter("ficheros/historial_partida.txt");
            escritor.write("=======================================\n");
            escritor.write("   HISTORIAL DE DISPAROS DE LA PARTIDA\n");
            escritor.write("=======================================\n\n");
            
            for (String tiro : humano.getHistorialDisparos()) {
                escritor.write(tiro + "\n");
            }
            escritor.close(); 
            System.out.println("\n El historial completo se ha guardado en historial_partida.txt");
            
        } catch (IOException e) {
            System.out.println("\n[Error] No se ha podido guardar el historial en el txt.");
        }
    }
    
    private void esperar(int ms) throws Exception {
        Thread.sleep(ms); 
    }
}