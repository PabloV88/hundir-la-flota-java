package Clases;

/**
 * Implementación de Jugador controlada por la IA.
 */
public class JugadorBot extends Jugador {

    // Constructor
    public JugadorBot() {
        super("Bot Enemigo");
    }

    // Métodos

    @Override
    public void colocarBarcos() {
        System.out.println("El enemigo está desplegando sus barcos...");
        
        for (int tamano : getTAMANOS_BARCOS()) {
            boolean colocado = false;
            // Intentamos colocar el barco aleatoriamente hasta que encaje
            while (!colocado) {
                // Generamos coordenadas 0-9 usando Math.random
                int fila = (int)(Math.random() * 10);
                int col = (int)(Math.random() * 10);
                
                // Probabilidad del 50% para elegir la orientación. Pregunta si la condicion se cumple, si se cumple saldra H, si no saldra V.
                String orientacion = (Math.random() < 0.5) ? "H" : "V";

                colocado = getTableroPropio().colocarBarco(fila, col, tamano, orientacion);
            }
        }
        System.out.println("¡El enemigo está listo!");
    }

    @Override
    public void disparar(Tablero tableroEnemigo) throws Exception {
        System.out.println("\nEl enemigo está calculando disparo...");
        pausaDramatica(); 

        int fila, col;
        
        // IA Básica:
        // Genera coordenadas al azar hasta encontrar una válida no atacada previamente.
        do {
            fila = (int)(Math.random() * 10);
            col = (int)(Math.random() * 10);
        } while (tableroEnemigo.getCasilla(fila, col).getEstaDisparada());

        tableroEnemigo.getCasilla(fila, col).setDisparo();
        
        // Convertimos el número de fila a letra para mostrarlo (0 -> A)
        char letra = (char)('A' + fila);
        System.out.println("El enemigo dispara a: " + letra + "-" + (col + 1));
    }
    

    // Genera una pausa para mejorar la experiencia de usuario.
    private void pausaDramatica() throws Exception {
        Thread.sleep(1000); 
    }
}