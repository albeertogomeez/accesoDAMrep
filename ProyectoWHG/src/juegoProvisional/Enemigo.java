package juegoProvisional;

public class Enemigo {
    private int enemigoX;
    private int enemigoY;
    private int direccionMovimiento;

    public Enemigo(int enemigoX, int enemigoY, int direccionMovimiento) {
        this.enemigoX = enemigoX;
        this.enemigoY = enemigoY;
        this.direccionMovimiento = direccionMovimiento;
    }

    public int getEnemigoX() {
        return enemigoX;
    }

    public int getEnemigoY() {
        return enemigoY;
    }

    public int getDireccionMovimiento() {
        return direccionMovimiento;
    }

    public void setEnemigoX(int enemigoX) {
        this.enemigoX = enemigoX;
    }

    public void setDireccionMovimiento(int direccionMovimiento) {
        this.direccionMovimiento = direccionMovimiento;
    }
}

