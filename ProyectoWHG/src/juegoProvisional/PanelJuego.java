package juegoProvisional;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class PanelJuego extends JPanel implements ActionListener, KeyListener {

    private int jugadorX = 50;
    private int jugadorY = 50;
    private Image jugadorImagen;

    private int enemigoX = 200;
    private int enemigoY = 200;
    private Image enemigoImagen;

    private int llaveX = 400;
    private int llaveY = 400;
    private Image llaveImagen;
    private boolean llaveRecogida = false;

    private boolean enFadeout = false;
    private int tiempoFadeout = 500; // Duración del fadeout en milisegundos (0.5 segundos)

    private boolean izquierdaPresionada = false;
    private boolean derechaPresionada = false;
    private boolean arribaPresionada = false;
    private boolean abajoPresionada = false;

    public PanelJuego() {
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);

        // Cargar las imágenes
        jugadorImagen = new ImageIcon("Player.png").getImage();
        enemigoImagen = new ImageIcon("Enemy.png").getImage();
        llaveImagen = new ImageIcon("Key.png").getImage();

        // Puedes iniciar un temporizador para manejar la actualización del juego
        Timer timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!enFadeout) {
            // Dibujar al jugador solo si no está en fadeout
            g.drawImage(jugadorImagen, jugadorX, jugadorY, this);
        }

        // Dibujar al enemigo
        g.drawImage(enemigoImagen, enemigoX, enemigoY, this);

        // Dibujar la llave solo si no ha sido recogida
        if (!llaveRecogida) {
            g.drawImage(llaveImagen, llaveX, llaveY, this);
        }

        // Dibuja otros elementos del juego aquí
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica de actualización del juego aquí
        if (!enFadeout) {
            actualizarJuego();
        }
        repaint(); // Vuelve a dibujar el panel
    }

    private void actualizarJuego() {
        // Actualizar la posición del jugador según las teclas presionadas
        if (izquierdaPresionada) {
            jugadorX -= 2;
        }
        if (derechaPresionada) {
            jugadorX += 2;
        }
        if (arribaPresionada) {
            jugadorY -= 2;
        }
        if (abajoPresionada) {
            jugadorY += 2;
        }

        // Lógica para la recogida de la llave (puedes ajustar las coordenadas según tu diseño)
        if (jugadorX < llaveX + 30 && jugadorX + 30 > llaveX &&
            jugadorY < llaveY + 30 && jugadorY + 30 > llaveY) {
            llaveRecogida = true;
        }

        // Lógica para la colisión con el enemigo (puedes ajustar las coordenadas según tu diseño)
        if (jugadorX < enemigoX + 30 && jugadorX + 30 > enemigoX &&
            jugadorY < enemigoY + 30 && jugadorY + 30 > enemigoY) {
            // Iniciar fadeout
            enFadeout = true;
            Timer fadeoutTimer = new Timer(tiempoFadeout, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Terminar fadeout y hacer respawn del jugador
                    enFadeout = false;
                    jugadorX = 50;
                    jugadorY = 50;
                    ((Timer) e.getSource()).stop(); // Detener el temporizador después del fadeout
                }
            });
            fadeoutTimer.setRepeats(false); // No se repite, solo se ejecuta una vez
            fadeoutTimer.start();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Manejar eventos de teclado (por ejemplo, mover al jugador) aquí
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_A) {
            izquierdaPresionada = true;
        } else if (keyCode == KeyEvent.VK_D) {
            derechaPresionada = true;
        } else if (keyCode == KeyEvent.VK_W) {
            arribaPresionada = true;
        } else if (keyCode == KeyEvent.VK_S) {
            abajoPresionada = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Puedes manejar eventos de teclado al soltar las teclas aquí
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_A) {
            izquierdaPresionada = false;
        } else if (keyCode == KeyEvent.VK_D) {
            derechaPresionada = false;
        } else if (keyCode == KeyEvent.VK_W) {
            arribaPresionada = false;
        } else if (keyCode == KeyEvent.VK_S) {
            abajoPresionada = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Puedes manejar eventos de teclado al escribir aquí
    }
}
