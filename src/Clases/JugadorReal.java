package Clases;

import Enum.ResultadoDisparo;
import Excepciones.CoordenadaInvalidaException;
import Metodos.Utilidades;
import java.util.HashMap;
import java.util.Scanner;


public class JugadorReal extends Jugador {

    private transient Scanner sc;
    private HashMap<Character, Integer> conversorLetras; 

    public JugadorReal(String nombre) {
        super(nombre); 
        this.sc = new Scanner(System.in);
        this.conversorLetras = new HashMap<>();
        conversorLetras.put('A', 0); conversorLetras.put('B', 1);
        conversorLetras.put('C', 2); conversorLetras.put('D', 3);
        conversorLetras.put('E', 4); conversorLetras.put('F', 5);
        conversorLetras.put('G', 6); conversorLetras.put('H', 7);
        conversorLetras.put('I', 8); conversorLetras.put('J', 9);
    }

    @Override
    public void colocarBarcos() throws Exception {
        System.out.println("\n--- FASE DE DESPLIEGUE: " + getNombre().toUpperCase() + " ---");
        getTableroPropio().mostrarTablero(true);

        for (int i = 0; i < getTAMANOS_BARCOS().length; i++) {
            boolean colocado = false;
            while (!colocado) {
                System.out.println("\nColocando " + getNOMBRES_BARCOS()[i] + " (" + getTAMANOS_BARCOS()[i] + " casillas)");
                
                int[] coords = null;
                
                try {
                    coords = pedirCoordenadas(false); 
                } catch (CoordenadaInvalidaException e) {
                    System.out.println(e.getMessage()); 
                    continue; 
                }

                System.out.print("Orientación (H = Horizontal / V = Vertical): ");
                String orientacion = sc.nextLine().toUpperCase();

                colocado = getTableroPropio().colocarBarco(coords[0], coords[1], getTAMANOS_BARCOS()[i], orientacion);
                
                if (colocado) {
                    getTableroPropio().mostrarTablero(true);
                }
            }
        }
        System.out.println("¡Tu flota está lista!");
    }

    @Override
    public void disparar(Tablero tableroEnemigo) throws Exception {
        boolean disparoValido = false;
        
        while (!disparoValido) {
            System.out.println("\n--- TU TURNO DE DISPARO ---");
            
            int[] coords = null;
            
            try {
                coords = pedirCoordenadas(true);
            } catch (CoordenadaInvalidaException e) {
                if (e.getMessage().equals("COMANDO_GUARDAR")) { // Mira haber si el mensaje de la excepcion es "COMANDO_GUARDAR", si lo es laza la excepcion
                    throw e; 
                }
                System.out.println(e.getMessage());
                continue; 
            }
            
            Casilla objetivo = tableroEnemigo.getCasilla(coords[0], coords[1]);
            
            if (objetivo.getEstaDisparada()) {
                System.out.println("¡Ya has disparado a esa casilla antes! Elige otra.");
            } else {
                objetivo.setDisparo();
                disparoValido = true; 
                
                char letraFila = (char) (coords[0] + 'A'); 
                int numeroCol = coords[1] + 1; 
                String coordenadaFinal = "" + letraFila + numeroCol;
                
                // Usamos el enum
                if (objetivo.getBarco()) {
                    if (tableroEnemigo.comprobarHundimiento(coords[0], coords[1])) {
                         System.out.println(ResultadoDisparo.HUNDIDO.getMensaje()); // mostramos el mensaje de hundimiento que esta en el enum
                         setHistorialDisparos(coordenadaFinal + " -> " + ResultadoDisparo.HUNDIDO.name()); //añadimos el resultado al arrayList
                    } else {
                         System.out.println(ResultadoDisparo.TOCADO.getMensaje()); // mostramos el mensaje de tocado que esta en el enum
                         setHistorialDisparos(coordenadaFinal + " -> " + ResultadoDisparo.TOCADO.name()); //añadimos el resultado al arrayList
                    }
                } else {
                    System.out.println(ResultadoDisparo.AGUA.getMensaje()); // mostramos el mensaje de agua que esta en el enum
                    setHistorialDisparos(coordenadaFinal + " -> " + ResultadoDisparo.AGUA.name()); //añadimos el resultado al arrayList
                }
            }
        }
    }

    public int[] pedirCoordenadas(boolean esDisparo) throws CoordenadaInvalidaException {
        if (esDisparo) {
            System.out.print("Introduce coordenada (Ej: A5), escribe 'R' para ver historial o 'G' para guardar la partida: ");
        } else {
            System.out.print("Introduce coordenada (Ej: A5): ");
        }
        
        String entrada = sc.nextLine().trim().toUpperCase();

        if (esDisparo && entrada.equals("R")) {
            // Usamos imprimir lista para generar el historial de disparos
            Utilidades.imprimirLista(getHistorialDisparos(), "HISTORIAL DE DISPAROS");
            throw new CoordenadaInvalidaException("Historial mostrado. Ahora, elige dónde disparar.");
        }

        if (esDisparo && entrada.equals("G")) {
            throw new CoordenadaInvalidaException("COMANDO_GUARDAR");
        }
        
        if (entrada.length() < 2) {
            throw new CoordenadaInvalidaException("Formato incorrecto. Debe ser Letra + Número (Ej: B4).");
        }

        char letra = entrada.charAt(0); // Extrae el primer caracter de entrada
        String parteNumero = entrada.substring(1); // Extrae el segundo caracter de la cadena entrada hasta el final

        if (!parteNumero.matches("[0-9]+")) { // Comprueba si el texto parteNumero está formado exclusivamente por números del 0 al 9, sin letras ni espacios.
            throw new CoordenadaInvalidaException("Formato incorrecto. La segunda parte debe ser numérica.");
        }

        if (!conversorLetras.containsKey(letra)) { // Utilizamos el containsKey para saber si la letra que nos ha dado esta fuera del tablero
            throw new CoordenadaInvalidaException("¡Coordenada fuera del tablero! La letra debe ser entre A y J.");
        }

        int fila = conversorLetras.get(letra); // utilizamos el metodo get del hasmap para darle la letra que introduce el usuario y cambiarla por su nº de fila correspondiente
        int col = Integer.parseInt(parteNumero) - 1;

        if (fila < 0 || fila >= 10 || col < 0 || col >= 10) {
            throw new CoordenadaInvalidaException("¡Coordenada fuera del tablero! Debe ser entre A-J y 1-10.");
        }
        
        return new int[]{fila, col};
    }

    public void setScanner(Scanner sc) {
        this.sc = sc;
    }
}