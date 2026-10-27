package Clases;

import java.io.Serializable;
/**
 * Representa un tipo de nave dentro del juego.
 * Almacena su nombre y su longitud en casillas.
 */
public class Barco implements Serializable {
    
    //atributos
    private int tamano;
    private String nombre;

    //constructor
    /**
     * Crea un nuevo barco.
     * @param tamano Longitud del barco en casillas.
     * @param nombre Nombre identificativo (ej: "Portaaviones").
     */
    public Barco(int tamano, String nombre) {
        this.tamano = tamano;
        this.nombre = nombre;
    }

    //getters
    /**
     * @return El tamaño del barco.
     */
    public int getTamano() {
        return tamano;
    }

    /**
     * @return El nombre del barco.
     */
    public String getNombre() {
        return nombre;
    }
}