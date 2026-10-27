package Interfaces;

import Clases.Tablero;

/**
 * Interfaz que define el contrato de comportamiento para cualquier entidad que juegue.
 * Obliga a implementar las acciones básicas: colocar la flota y disparar.
 */
public interface AccionesJugador {
    
    /**
     * Método para posicionar los barcos en el tablero al inicio de la partida.
     * @throws Exception Permite gestionar interrupciones durante la colocación.
     */
    void colocarBarcos() throws Exception;
    
    /**
     * Realiza un disparo contra el tablero del oponente.
     * @param tableroEnemigo El tablero del rival donde impactará el disparo.
     * @throws Exception Permite gestionar interrupciones durante el turno.
     */
    void disparar(Tablero tableroEnemigo) throws Exception;
}