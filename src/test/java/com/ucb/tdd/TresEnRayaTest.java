package com.ucb.tdd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

public class TresEnRayaTest {

    private TresEnRaya tresEnRaya;

    @Before
    public void iniciarTablero() {
        tresEnRaya = new TresEnRaya();
    }

    /** Juega una secuencia de posiciones {x, y} y devuelve el resultado de la ultima. */
    private String jugarSecuencia(int[]... jugadas) throws Exception {
        String resultado = null;
        for (int[] jugada : jugadas) {
            resultado = tresEnRaya.jugar(jugada[0], jugada[1]);
        }
        return resultado;
    }

    // Requerimiento 1: una pieza se coloca dentro de un tablero 3x3

    @Test
    public void cuandoPiezaFueraDelEjeXLanzaExcepcion() {
        Exception error = assertThrows(Exception.class, () -> tresEnRaya.jugar(5, 2));

        assertEquals("La coordenada 5 esta fuera de lugar", error.getMessage());
    }

    @Test
    public void cuandoPiezaFueraDelEjeYLanzaExcepcion() {
        Exception error = assertThrows(Exception.class, () -> tresEnRaya.jugar(2, 0));

        assertEquals("La coordenada 0 esta fuera de lugar", error.getMessage());
    }

    @Test
    public void cuandoPosicionOcupadaLanzaExcepcion() throws Exception {
        tresEnRaya.jugar(1, 3);

        Exception error = assertThrows(Exception.class, () -> tresEnRaya.jugar(1, 3));

        assertEquals("La posicion ya esta ocupada", error.getMessage());
    }

    // Requerimiento 2: se puede saber que jugador juega despues

    @Test
    public void primerTurnoEsDeX() {
        assertEquals('X', tresEnRaya.siguienteTurno());
    }

    @Test
    public void despuesDeXJuegaMas() throws Exception {
        tresEnRaya.jugar(2, 1);

        assertEquals('+', tresEnRaya.siguienteTurno());
    }

    @Test
    public void despuesDeMasJuegaX() throws Exception {
        jugarSecuencia(new int[]{2, 1}, new int[]{2, 2});

        assertEquals('X', tresEnRaya.siguienteTurno());
    }

    // Requerimiento 3: gana quien completa una linea o diagonal

    @Test
    public void sinLineaCompletaNoHayGanador() throws Exception {
        String resultado = jugarSecuencia(
                new int[]{1, 1}, new int[]{2, 2},
                new int[]{1, 2}, new int[]{1, 3},
                new int[]{3, 1});

        assertEquals("No hay ganador", resultado);
    }

    @Test
    public void ganaAlCompletarLineaHorizontal() throws Exception {
        String resultado = jugarSecuencia(
                new int[]{2, 1}, new int[]{1, 1},
                new int[]{3, 1}, new int[]{1, 2},
                new int[]{2, 2}, new int[]{1, 3});

        assertEquals("+ es el ganador", resultado);
    }

    @Test
    public void ganaAlCompletarLineaVertical() throws Exception {
        String resultado = jugarSecuencia(
                new int[]{1, 1}, new int[]{1, 2},
                new int[]{2, 1}, new int[]{2, 2},
                new int[]{3, 1});

        assertEquals("X es el ganador", resultado);
    }

    @Test
    public void ganaAlCompletarDiagonal() throws Exception {
        String resultado = jugarSecuencia(
                new int[]{1, 1}, new int[]{1, 2},
                new int[]{2, 2}, new int[]{1, 3},
                new int[]{3, 3});

        assertEquals("X es el ganador", resultado);
    }

    @Test
    public void ganaAlCompletarDiagonalOpuesta() throws Exception {
        String resultado = jugarSecuencia(
                new int[]{1, 2}, new int[]{1, 3},
                new int[]{1, 1}, new int[]{2, 2},
                new int[]{2, 3}, new int[]{3, 1});

        assertEquals("+ es el ganador", resultado);
    }
}
