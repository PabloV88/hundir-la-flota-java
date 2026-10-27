package Clases;

import java.io.Serializable;

/**
 * Gestiona la cuadrícula de juego de 10x10.
 * Controla la colocación de barcos, validación de coordenadas y estado de la flota.
 */
public class Tablero implements Serializable {
    
    // atributos
    private Casilla[][] casillas;
    private final int TAMANO = 10;

    // constructores
    public Tablero() {
        casillas = new Casilla[TAMANO][TAMANO];
        inicializarTablero();
    }

    // Inicializa el tablero
    private void inicializarTablero() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    // Métodos

    /**
     * Imprime el estado actual del tablero en la consola.
     * @param esMio Si es true, muestra mis barcos. Si es false, oculta los barcos intactos.
     */
    public void mostrarTablero(boolean esMio) {
        System.out.println("   1 2 3 4 5 6 7 8 9 10"); 

        for (int i = 0; i < TAMANO; i++) {
            char letraFila = (char) ('A' + i);
            System.out.print(letraFila + "  "); 

            for (int j = 0; j < TAMANO; j++) {
                System.out.print(casillas[i][j].obtenerSimbolo(!esMio) + " ");
            }
            System.out.println(); 
        }
    }

    /**
     * Intenta colocar un barco en el tablero verificando límites y colisiones.
     * @return true si se colocó con éxito, false si hubo algún error.
     */
    public boolean colocarBarco(int fila, int col, int tamano, String orientacion) {
        // 1. Calcular coordenadas finales
        int filaFin = fila;
        int colFin = col;

        if (orientacion.equalsIgnoreCase("H")) {
            colFin = col + tamano - 1;
        } else if (orientacion.equalsIgnoreCase("V")) {
            filaFin = fila + tamano - 1;
        } else {
            System.out.println("Opcion incorrecta");
            return false;
        }

        // 2. Validar límites del tablero
        if (fila < 0 || fila >= TAMANO || col < 0 || col >= TAMANO ||
                filaFin >= TAMANO || colFin >= TAMANO) {
            // No imprimimos error aquí para no ensuciar la consola del bot
            return false;
        }

        // 3. Validar colisiones 
        for (int i = fila - 1; i <= filaFin + 1; i++) {
            for (int j = col - 1; j <= colFin + 1; j++) {
                if (i >= 0 && i < TAMANO && j >= 0 && j < TAMANO) {
                    if (casillas[i][j].getBarco()) {
                        return false;
                    }
                }
            }
        }

        // 4. Colocación efectiva
        for (int i = fila; i <= filaFin; i++) { 
             for (int j = col; j <= colFin; j++) { 
                casillas[i][j].setBarco();
            }
        }
        return true; 
    }

    // getter

    public Casilla getCasilla(int fila, int columna) {
        return casillas[fila][columna];
    }

    /**
     * Verifica si queda algún trozo de barco sin hundir en todo el tablero.
     */
    public boolean quedanBarcosVivos() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                if (casillas[i][j].getBarco() && !casillas[i][j].getEstaDisparada()) {
                    return true; 
                }
            }
        }
        return false; 
    }

    /**
     * Comprueba si el barco ubicado en la coordenada (fila, col) ha sido hundido por completo.
     * Se basa en recorrer las casillas adyacentes que contienen barco.
     * @param fila Fila del impacto.
     * @param col Columna del impacto.
     * @return true si todo el barco está destruido, false si queda alguna parte viva.
     */
    public boolean comprobarHundimiento(int fila, int col) {
        // Estrategia: Recorremos en cruz desde el punto de impacto.
        // Como los barcos no se tocan, cualquier casilla con barco conectada pertenece a la mismo barco.

        // 1. Revisar Horizontal (Izquierda y Derecha)
        for (int j = col; j >= 0 && casillas[fila][j].getBarco(); j--) {
            if (!casillas[fila][j].getEstaDisparada()) return false; // Encontramos una parte sana
        }
        for (int j = col + 1; j < TAMANO && casillas[fila][j].getBarco(); j++) {
            if (!casillas[fila][j].getEstaDisparada()) return false;
        }

        // 2. Revisar Vertical (Arriba y Abajo)
        for (int i = fila; i >= 0 && casillas[i][col].getBarco(); i--) {
            if (!casillas[i][col].getEstaDisparada()) return false;
        }
        for (int i = fila + 1; i < TAMANO && casillas[i][col].getBarco(); i++) {
             if (!casillas[i][col].getEstaDisparada()) return false;
        }

        // Si hemos revisado todo el barco y no hay partes sanas:
        return true;
    }
}