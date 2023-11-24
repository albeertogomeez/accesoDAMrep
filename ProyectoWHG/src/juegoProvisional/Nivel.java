package juegoProvisional;

import java.util.List;

public class Nivel {
    private int jugadorX;
    private int jugadorY;
    private List<Enemigo> enemigos;

    public Nivel(int jugadorX, int jugadorY, List<Enemigo> enemigos) {
        this.jugadorX = jugadorX;
        this.jugadorY = jugadorY;
        this.enemigos = enemigos;
    }

    public int getJugadorX() {
        return jugadorX;
    }

    public int getJugadorY() {
        return jugadorY;
    }

    public List<Enemigo> getEnemigos() {
        return enemigos;
    }
}
