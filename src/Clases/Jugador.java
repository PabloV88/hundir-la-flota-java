package Clases;

import Interfaces.AccionesJugador;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase base abstracta para cualquier jugador.
 * Implementa la interfaz AccionesJugador y define los atributos comunes.
 */
public abstract class Jugador implements AccionesJugador, Serializable {
    
    // atributos
    private String nombre;
    private Tablero tableroPropio;
    private ArrayList<String> historialDisparos;
    
    // Configuración estándar de la flota para todos los jugadores
    private final int[] TAMANOS_BARCOS = {5, 4, 3, 3, 2};
    private final String[] NOMBRES_BARCOS = {"Portaaviones", "Acorazado", "Submarino", "Submarino", "Destructor"};

    // Constructor
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.tableroPropio = new Tablero();
        this.historialDisparos = new ArrayList<>();
    }

    // Getter y setter
    public Tablero getTablero() {
        return tableroPropio;
    }

    public String getNombre() {
        return nombre;
    }

        public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tablero getTableroPropio() {
        return tableroPropio;
    }

    public void setTableroPropio(Tablero tableroPropio) {
        this.tableroPropio = tableroPropio;
    }

    public int[] getTAMANOS_BARCOS() {
        return TAMANOS_BARCOS;
    }

    public String[] getNOMBRES_BARCOS() {
        return NOMBRES_BARCOS;
    }
    
    public ArrayList<String> getHistorialDisparos() {
    return historialDisparos;
    }

    public void setHistorialDisparos(String registro) {
        this.historialDisparos.add(registro);  // utilizamos add. para añadir los registros al arraylist
    }

    // Método abstracto
    // Obligamos a las subclases a definir la implementación específica.
    // Se declara 'throws Exception' para manejar posibles eventos de sistema en las hijas.

    @Override
    public abstract void colocarBarcos() throws Exception; 

    @Override
    public abstract void disparar(Tablero tableroEnemigo) throws Exception;

}