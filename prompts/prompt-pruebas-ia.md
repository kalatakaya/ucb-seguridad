Actúa como un desarrollador Java con experiencia en TDD (Test Driven Development).

Necesito que escribas las pruebas unitarias para la kata del **Tres en Raya**, a partir de los
requerimientos que te doy abajo. 

**Requerimiento 1:** Una pieza puede estar colocada en un espacio de un tablero 3x3.

Pruebas:
- Cuando una pieza está fuera del eje X, entonces se debe lanzar una excepción.
- Cuando una pieza está fuera del eje Y, entonces se debe lanzar una excepción.
- Cuando una pieza esté en un lugar ya ocupado, se debe lanzar una excepción.

**Requerimiento 2:** Debe existir una forma de determinar qué jugador juega después.

Pruebas:
- El primer turno siempre debe ser jugado por `'X'`.
- Si el último turno fue jugado por `'X'`, entonces el siguiente turno es para `'+'`.
- Si el último turno fue jugado por `'+'`, entonces el siguiente turno es para `'X'`.

**Requerimiento 3:** Un jugador gana si es el primero en conectar una línea de un lado a otro,
o de una esquina a otra.

Pruebas:
- Si no se cumple la condición de victoria, entonces no hay ganador.
- Un jugador gana cuando toda una línea horizontal es ocupada por sus piezas.
- Un jugador gana cuando toda una línea vertical es ocupada por sus piezas.
- Un jugador gana cuando toda una línea diagonal es ocupada por sus piezas.

## Lo que espero

1. Al menos una prueba por cada punto de los requerimientos. Agrega las pruebas adicionales
   que consideres necesarias (casos límite, casos que los requerimientos no mencionan pero que
   un buen juego de Tres en Raya debería manejar).
2. Nombres de métodos en español que describan el comportamiento probado.
