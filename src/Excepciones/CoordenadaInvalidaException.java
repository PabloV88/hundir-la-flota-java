package Excepciones;

/**
 * Excepción personalizada para gestionar errores en la introducción de coordenadas.
 */
public class CoordenadaInvalidaException extends Exception {
    
    // Constructor que recibe el mensaje de error
    public CoordenadaInvalidaException(String mensaje) {
        super(mensaje);
    }
} 