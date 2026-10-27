package Enum;

/**
 * Enum que representa los posibles resultados de un disparo.
 */
public enum ResultadoDisparo {
    AGUA("¡Agua!"),
    TOCADO("¡Tocado!"),
    HUNDIDO("¡Tocado y hundido!");

    private final String mensaje;

    ResultadoDisparo(String mensaje) { 
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}