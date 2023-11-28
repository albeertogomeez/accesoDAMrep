package juegoProvisional;

import java.awt.Image;
import java.util.List;

public class Nivel {
    private int jugadorX;
    private int jugadorY;
    private List<PanelJuego.Enemigo> enemigos;
    private Image mapaImagen;  // Nueva propiedad para la imagen del mapa

    public Nivel(int jugadorX, int jugadorY, List<PanelJuego.Enemigo> enemigos, Image mapaImagen) {
        this.jugadorX = jugadorX;
        this.jugadorY = jugadorY;
        this.enemigos = enemigos;
        this.mapaImagen = mapaImagen;
    }

    public int getJugadorX() {
        return jugadorX;
    }

    public int getJugadorY() {
        return jugadorY;
    }

    public List<PanelJuego.Enemigo> getEnemigos() {
        return enemigos;
    }
    
    public Image getMapaImagen() {
        return mapaImagen;
    }
}
