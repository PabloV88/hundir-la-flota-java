# Hundir la Flota - Java

Proyecto desarrollado en Java durante el 1.º curso del ciclo formativo de Desarrollo de Aplicaciones Multiplataforma (DAM) para la asignatura de Programación.

Adaptación del clásico juego de estrategia naval por turnos, implementando arquitectura modular mediante paquetes, interfaces gráficas de acceso y menús, modos de juego individuales o por parejas, y persistencia de datos mediante SQLite y ficheros locales.

---

## Características principales
- Modos de juego: Partida individual contra bot automatizado (`Jugar1Jugador.java`, `JugadorBot.java`) y modalidad para dos jugadores (`Jugar2Jugadores.java`, `JugadorReal.java`).
- Interfaz y navegación: Pantallas gráficas para inicio de sesión y menús de control (`VentanaLogin.java`, `VentanaMenu.java`, `MenuPrincipal.java`, `JugarMenu.java`).
- Persistencia de datos:
  - Base de datos relacional SQLite conectada por JDBC para gestión de usuarios y registros (`GestorBaseDatos.java`, `usuarios_batalla.db`).
  - Serialización y guardado del estado de la partida (`CargarPartida.java`, `partida_guardada.dat`).
  - Carga y lectura de normas desde fichero plano (`ReglasJuego.java`, `reglas.txt`).
- Control de excepciones y estados:
  - Identificación de disparos mediante tipos enumerados (`ResultadoDisparo.java`).
  - Control de entradas no válidas y fuera de rango con excepciones propias (`CoordenadaInvalidaException.java`).

---

## Tecnologías y conceptos aplicados
- Lenguaje: Java.
- Base de datos: SQLite a través del controlador JDBC `sqlite-jdbc-3.39.3.0.jar`.
- Paradigma Orientado a Objetos:
  - Abstracción, herencia y polimorfismo (`Jugador.java`, `AccionesJugador.java`).
  - Modelado de dominio y entidades del tablero (`Barco.java`, `Casilla.java`, `Tablero.java`).
  - Métodos transversales y funciones auxiliares (`Utilidades.java`).
- Gestión de dependencias y persistencia en ficheros locales.

---

## Estructura del repositorio

### Código fuente (src/)
- Clases/: Entidades del modelo de juego (`Barco.java`, `Casilla.java`, `Jugador.java`, `JugadorBot.java`, `JugadorReal.java`, `Tablero.java`).
- Enum/: Definición de enumerados para el flujo de la partida (`ResultadoDisparo.java`).
- Excepciones/: Excepciones personalizadas (`CoordenadaInvalidaException.java`).
- Interfaces/: Definición de contratos y comportamientos (`AccionesJugador.java`).
- Metodos/: Lógica de flujo de juego, menús, ventanas gráficas y persistencia (`CargarPartida.java`, `GestorBaseDatos.java`, `Jugar1Jugador.java`, `Jugar2Jugadores.java`, `JugarMenu.java`, `MenuPrincipal.java`, `ReglasJuego.java`, `Utilidades.java`, `VentanaLogin.java`, `VentanaMenu.java`).
- Principal/: Punto de entrada y ejecución de la aplicación (`App.java`).

### Recursos y librerías externas
- ficheros/: Archivos locales de datos (`partida_guardada.dat`, `reglas.txt`, `usuarios_batalla.db`).
- lib/: Conector JDBC de SQLite (`sqlite-jdbc-3.39.3.0.jar`).

---

## Instrucciones de ejecución
1. Clonar el repositorio localmente:
   git clone https://github.com/TU_USUARIO/TU_REPOSITORIO.git
2. Abrir la carpeta del proyecto en el entorno de desarrollo (VS Code, IntelliJ IDEA o Eclipse).
3. Asegurar la inclusión del archivo `lib/sqlite-jdbc-3.39.3.0.jar` en las librerías/classpath del proyecto.
4. Ejecutar la clase principal `App.java` ubicada en el paquete `Principal`.
