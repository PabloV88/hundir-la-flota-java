package Clases;

import java.io.Serializable;

/**
 * Representa la unidad mínima del tablero.
 * Cada casilla conoce si contiene una parte de barco y si ha sido atacada.
 */
public class Casilla implements Serializable {
    
    //atributo
    private boolean hayBarco;
    private boolean fueDisparada;

    //constructor
    public Casilla() {
        this.hayBarco = false;
        this.fueDisparada = false;
    }

    //setters
    public void setBarco() {
        this.hayBarco = true;
    }

    public void setDisparo() {
        this.fueDisparada = true;
    }

    //getters
    public boolean getBarco() {
        return hayBarco;
    }
    
    public boolean getEstaDisparada() {
        return fueDisparada;
    }

    /**
     * Determina qué carácter se debe imprimir en pantalla según el estado de la casilla.
     * @param oculto Si es true oculta barcos no tocados.
     * @return El símbolo gráfico de la casilla (Agua, Barco, Tocado o Fallo).
     */
    public String obtenerSimbolo(boolean oculto) {
        if (!fueDisparada) {
            // Si la casilla no ha sido atacada:
            if (!oculto && hayBarco) return "B"; 
            return "~"; 
        } else {
            // Si ya fue disparada:
            if (hayBarco) return "X"; // Impacto directo
            return "O"; // Agua / Fallo
        }
    }
}