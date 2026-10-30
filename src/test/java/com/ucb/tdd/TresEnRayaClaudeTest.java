package com.ucb.tdd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias de la kata Tres en Raya generadas por IA (Claude).
 *
 * Convencion de coordenadas: jugar(x, y) con x = fila e y = columna, ambas de 1 a 3.
 * Una linea horizontal es una fila completa (mismo x) y una vertical es una columna completa (mismo y).
 */
public class TresEnRayaClaudeTest {

    private static final String GANA_X = "X es el ganador";
    private static final String GANA_MAS = "+ es el ganador";
    private static final String SIN_GANADOR = "No hay ganador";

    private TresEnRaya juego;

    @Before
    public void nuevoJuego() {
        juego = new TresEnRaya();
    }

    /** Ejecuta las jugadas en orden, alternando turnos, y devuelve el resultado de la ultima. */
    private String jugar(int[]... jugadas) throws Exception {
        String resultado = null;
        for (int[] jugada : jugadas) {
            resultado = juego.jugar(jugada[0], jugada[1]);
        }
        return resultado;
    }

    private static int[] p(int x, int y) {
        return new int[]{x, y};
    }

    // ------------------------------------------------------------------
    // Requerimiento 1: una pieza puede estar colocada en un tablero 3x3
    // ------------------------------------------------------------------

    @Test
    public void lanzaExcepcionCuandoXEsMayorQueTres() {
        assertThrows(Exception.class, () -> juego.jugar(4, 2));
    }

    @Test
    public void lanzaExcepcionCuandoXEsMenorQueUno() {
        assertThrows(Exception.class, () -> juego.jugar(0, 2));
    }

    @Test
    public void lanzaExcepcionCuandoYEsMayorQueTres() {
        assertThrows(Exception.class, () -> juego.jugar(2, 4));
    }

    @Test
    public void lanzaExcepcionCuandoYEsMenorQueUno() {
        assertThrows(Exception.class, () -> juego.jugar(2, 0));
    }

    @Test
    public void lanzaExcepcionConCoordenadasNegativas() {
        assertThrows(Exception.class, () -> juego.jugar(-1, -1));
    }

    @Test
    public void aceptaLasCuatroEsquinasDelTablero() throws Exception {
        assertEquals(SIN_GANADOR, juego.jugar(1, 1));
        assertEquals(SIN_GANADOR, juego.jugar(1, 3));
        assertEquals(SIN_GANADOR, juego.jugar(3, 1));
        assertEquals(SIN_GANADOR, juego.jugar(3, 3));
    }

    @Test
    public void lanzaExcepcionCuandoLaPosicionYaEstaOcupadaPorElRival() throws Exception {
        juego.jugar(2, 2); // X

        assertThrows(Exception.class, () -> juego.jugar(2, 2)); // + intenta la misma casilla
    }

    @Test
    public void lanzaExcepcionCuandoLaPosicionYaEstaOcupadaPorElMismoJugador() throws Exception {
        jugar(p(1, 1), p(3, 3)); // X, +

        assertThrows(Exception.class, () -> juego.jugar(1, 1)); // X intenta su propia casilla
    }

    // ------------------------------------------------------------------
    // Requerimiento 2: determinar que jugador juega despues
    // ------------------------------------------------------------------

    @Test
    public void elPrimerTurnoEsDeX() {
        assertEquals('X', juego.siguienteTurno());
    }

    @Test
    public void despuesDeQueJuegaXElTurnoEsDeMas() throws Exception {
        juego.jugar(1, 1);

        assertEquals('+', juego.siguienteTurno());
    }

    @Test
    public void despuesDeQueJuegaMasElTurnoEsDeX() throws Exception {
        jugar(p(1, 1), p(2, 2));

        assertEquals('X', juego.siguienteTurno());
    }

    @Test
    public void losTurnosSeAlternanDuranteTodaLaPartida() throws Exception {
        int[][] jugadas = {p(1, 1), p(1, 2), p(1, 3), p(2, 2), p(2, 1), p(2, 3), p(3, 2), p(3, 1)};
        char esperado = '+';

        for (int[] jugada : jugadas) {
            juego.jugar(jugada[0], jugada[1]);
            assertEquals(esperado, juego.siguienteTurno());
            esperado = (esperado == 'X') ? '+' : 'X';
        }
    }

    @Test
    public void consultarElTurnoNoCambiaElTurno() {
        juego.siguienteTurno();
        juego.siguienteTurno();

        assertEquals('X', juego.siguienteTurno());
    }

    @Test
    public void unaCoordenadaInvalidaNoConsumeElTurno() throws Exception {
        juego.jugar(1, 1); // X

        assertThrows(Exception.class, () -> juego.jugar(5, 5)); // + se equivoca

        assertEquals('+', juego.siguienteTurno());
    }

    @Test
    public void unaJugadaEnCasillaOcupadaNoConsumeElTurno() throws Exception {
        juego.jugar(1, 1); // X

        assertThrows(Exception.class, () -> juego.jugar(1, 1)); // + se equivoca

        assertEquals('+', juego.siguienteTurno());
    }

    // ------------------------------------------------------------------
    // Requerimiento 3: gana quien conecta una linea o una diagonal
    // ------------------------------------------------------------------

    @Test
    public void noHayGanadorConElTableroCasiVacio() throws Exception {
        assertEquals(SIN_GANADOR, juego.jugar(2, 2));
    }

    @Test
    public void noHayGanadorConUnaLineaMixta() throws Exception {
        // Fila 1: X + X
        assertEquals(SIN_GANADOR, jugar(p(1, 1), p(1, 2), p(1, 3)));
    }

    @Test
    public void noHayGanadorConDosPiezasEnLinea() throws Exception {
        // X tiene (1,1) y (1,2), le falta (1,3)
        assertEquals(SIN_GANADOR, jugar(p(1, 1), p(3, 3), p(1, 2)));
    }

    @Test
    public void noHayGanadorConElTableroLlenoSinLineas() throws Exception {
        // X | + | X
        // X | + | +
        // + | X | X
        String resultado = jugar(p(1, 1), p(1, 2), p(1, 3), p(2, 2), p(2, 1),
                p(2, 3), p(3, 2), p(3, 1), p(3, 3));

        assertEquals(SIN_GANADOR, resultado);
    }

    @Test
    public void xGanaConLaPrimeraFila() throws Exception {
        assertEquals(GANA_X, jugar(p(1, 1), p(2, 1), p(1, 2), p(2, 2), p(1, 3)));
    }

    @Test
    public void xGanaConLaFilaDelMedio() throws Exception {
        assertEquals(GANA_X, jugar(p(2, 1), p(1, 1), p(2, 2), p(1, 2), p(2, 3)));
    }

    @Test
    public void xGanaConLaUltimaFila() throws Exception {
        assertEquals(GANA_X, jugar(p(3, 1), p(1, 1), p(3, 2), p(1, 2), p(3, 3)));
    }

    @Test
    public void xGanaConLaPrimeraColumna() throws Exception {
        assertEquals(GANA_X, jugar(p(1, 1), p(1, 2), p(2, 1), p(2, 2), p(3, 1)));
    }

    @Test
    public void xGanaConLaColumnaDelMedio() throws Exception {
        assertEquals(GANA_X, jugar(p(1, 2), p(1, 1), p(2, 2), p(2, 1), p(3, 2)));
    }

    @Test
    public void xGanaConLaUltimaColumna() throws Exception {
        assertEquals(GANA_X, jugar(p(1, 3), p(1, 1), p(2, 3), p(2, 1), p(3, 3)));
    }

    @Test
    public void xGanaConLaDiagonalPrincipal() throws Exception {
        assertEquals(GANA_X, jugar(p(1, 1), p(1, 2), p(2, 2), p(1, 3), p(3, 3)));
    }

    @Test
    public void xGanaConLaDiagonalSecundaria() throws Exception {
        assertEquals(GANA_X, jugar(p(1, 3), p(1, 1), p(2, 2), p(1, 2), p(3, 1)));
    }

    @Test
    public void masGanaConUnaFila() throws Exception {
        assertEquals(GANA_MAS, jugar(p(2, 1), p(1, 1), p(3, 2), p(1, 2), p(3, 3), p(1, 3)));
    }

    @Test
    public void masGanaConUnaColumna() throws Exception {
        assertEquals(GANA_MAS, jugar(p(1, 1), p(1, 3), p(2, 2), p(2, 3), p(3, 2), p(3, 3)));
    }

    @Test
    public void masGanaConUnaDiagonal() throws Exception {
        assertEquals(GANA_MAS, jugar(p(1, 2), p(1, 1), p(1, 3), p(2, 2), p(3, 2), p(3, 3)));
    }

    @Test
    public void xGanaConLaUltimaCasillaLibre() throws Exception {
        // X | X | +
        // + | X | X
        // + | + | X  <- (3,3) completa la diagonal principal
        String resultado = jugar(p(1, 1), p(1, 3), p(1, 2), p(2, 1), p(2, 2),
                p(3, 1), p(2, 3), p(3, 2), p(3, 3));

        assertEquals(GANA_X, resultado);
    }

    @Test
    public void noSePuedeJugarDespuesDeQueHayUnGanador() throws Exception {
        jugar(p(1, 1), p(2, 1), p(1, 2), p(2, 2), p(1, 3)); // X gana con la fila 1

        assertThrows(Exception.class, () -> juego.jugar(3, 3));
    }
}
