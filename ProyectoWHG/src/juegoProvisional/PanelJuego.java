package juegoProvisional;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.Timer;

public class PanelJuego extends JPanel implements ActionListener, KeyListener {

    private int jugadorX = 200;
    private int jugadorY = 500;
    private Image jugadorImagen;

    private Image enemigoImagen;
    private List<Enemigo> enemigos;

    private int llaveX = 400;
    private int llaveY = 400;
    private Image llaveImagen;
    private boolean llaveRecogida = false;
    
    private List<Nivel> niveles;
    private int nivelActual;
    private Image mapaNivel1;
    private Image mapaNivel2;
    
    private int puntoTransicionX = 1550; // Ajusta esta coordenada X según tus necesidades
    private int puntoTransicionY = 680 ;  // Ajusta esta coordenada Y según tus necesidades

    private boolean haMuerto = false;
    private boolean enFadeout = false;
    private int tiempoFadeout = 100;
    private float transparenciaJugador = 1.0f; // Transparencia inicial del jugador

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
//        llaveImagen = new ImageIcon("Key.png").getImage();
        mapaNivel1 = new ImageIcon("Level1.png").getImage();
        mapaNivel2 = new ImageIcon("Level2.png").getImage();

        // Puedes iniciar un temporizador para manejar la actualización del juego
        Timer timer = new Timer(10, this);
        timer.start();

        enemigos = new ArrayList<>();
        enemigos.add(new Enemigo(1300, 339, -0));
        enemigos.add(new Enemigo(575, 432, 0));
        enemigos.add(new Enemigo(1300, 525, -0));
        enemigos.add(new Enemigo(575, 618, 0));

        niveles = new ArrayList<>();
        niveles.add(new Nivel(200, 500, enemigos, mapaNivel1)); // Agregar al menos un nivel aquí
        niveles.add(new Nivel(200, 500, enemigos, mapaNivel2));
        nivelActual = 0; // Ajusta aquí el índice inicial
        cargarNivel(nivelActual);
    }
    
    public static class Enemigo {
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

        // Agregar lógica para cambiar dirección cuando llega a cierto punto en X
        public void verificarCambioDireccion() {
            if (enemigoX <= 570 || enemigoX >= 1300 ) {
                direccionMovimiento = -direccionMovimiento;
            }
        }
    }

    
    private void cargarNivel(int nivel) {
        Nivel nivelActual = niveles.get(nivel);

        jugadorX = nivelActual.getJugadorX();
        jugadorY = nivelActual.getJugadorY();
        enemigos = nivelActual.getEnemigos();

        // Cargar la nueva imagen del mapa
        mapaNivel1 = nivelActual.getMapaImagen();
    }
    
    private void avanzarNivel() {
        nivelActual++;
        if (nivelActual < niveles.size()) {
            cargarNivel(nivelActual);
        } else {
            JOptionPane.showInputDialog("Acabas de completar el juego!");
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar el mapa
        g.drawImage(mapaNivel1, 0, 0, this);

        // Dibujar al jugador con la transparencia actual
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparenciaJugador));
        g2d.drawImage(jugadorImagen, jugadorX, jugadorY, this);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        // Dibujar al enemigo
        for (Enemigo enemigo : enemigos) {
        	g.drawImage(enemigoImagen, enemigo.getEnemigoX(), enemigo.getEnemigoY(), this);
        }

        // Dibujar la llave solo si no ha sido recogida
        if (!llaveRecogida) {
            g.drawImage(llaveImagen, llaveX, llaveY, this);
        }

        // Dibuja otros elementos del juego aquí
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica de actualización del juego aquí
        actualizarJuego();
        repaint(); // Vuelve a dibujar el panel
    }

    private void actualizarJuego() {
        // Verificar si el jugador está en animación de fadeout
        if (haMuerto) {
            // Aquí puedes agregar lógica adicional si es necesario durante la animación
            return;
        }

        // Actualizar la posición del jugador solo si no está en animación de fadeout
        if (izquierdaPresionada) {
            jugadorX -= 6;
        }
        if (derechaPresionada) {
            jugadorX += 6;
        }
        if (arribaPresionada) {
            jugadorY -= 6;
        }
        if (abajoPresionada) {
            jugadorY += 6;
        }
        
        // Actualizar la posición de los enemigos
        for (Enemigo enemigo : enemigos) {
            enemigo.setEnemigoX(enemigo.getEnemigoX() + enemigo.getDireccionMovimiento());
        }
        
        // Actualizar la posición de los enemigos y verificar cambio de dirección
        for (Enemigo enemigo : enemigos) {
            enemigo.setEnemigoX(enemigo.getEnemigoX() + enemigo.getDireccionMovimiento());
            enemigo.verificarCambioDireccion();
        }
        
        // Verificar si el jugador está en el punto de transición
        if (jugadorX >= puntoTransicionX && jugadorY >= puntoTransicionY) {
            avanzarNivel();
        }

        // Lógica para la recogida de la llave (puedes ajustar las coordenadas según tu diseño)
        if (jugadorX < llaveX + 55 && jugadorX + 55 > llaveX &&
            jugadorY < llaveY + 55 && jugadorY + 55 > llaveY) {
            llaveRecogida = true;
        }

        // Lógica para la colisión con el enemigo (puedes ajustar las coordenadas según tu diseño)
//        for (Enemigo enemigo : enemigos) {
//            if (jugadorX < enemigo.getEnemigoX() + 50 && jugadorX + 50 > enemigo.getEnemigoX() &&
//                jugadorY < enemigo.getEnemigoY() + 50 && jugadorY + 50 > enemigo.getEnemigoY()) {
//                // Iniciar animación de fadeout solo si no está en curso
//                if (!enFadeout) {
//                    iniciarAnimacionFadeout();
//                }
//            }
//        }
        
        // Cuadrado principal
        	// Bordes izquierda y derecha
	        if (jugadorX < 157) {
	            jugadorX = 157;
	        } else if (jugadorX > 330 && jugadorX < 400 && jugadorY >= 190 && jugadorY < 692) {
	            jugadorX = 330;
	        }
	        // Bordes arriba y abajo
	        if (jugadorY < 258 && jugadorX > 130 && jugadorX < 350) {
	            jugadorY = 258;
	        } else if (jugadorY > 729) {
	            jugadorY = 729;
	        }
	        // Pasillo primero por abajo
	        if (jugadorY <= 696 && jugadorX > 330 && jugadorX < 521) {
	            jugadorY = 696;
	        } else if (jugadorY > 729) {
	            jugadorY = 729;
	        } else if (jugadorX > 617 && jugadorY >= 696 && jugadorY <= 729) {
	        	jugadorX = 617;
	        }
	        // Pasillo grande con enemigos
	        if (jugadorX <= 530 && jugadorY < 450 && jugadorY > 729) {
	        	jugadorX = 530;
	        } else if (jugadorX > 1337 && jugadorY < 550) {
	        	jugadorX = 1337;
	        } else if (jugadorY < 324 && jugadorX >= 540 && jugadorX <= 1200) {
	        	jugadorY = 324;
	        } else if (jugadorY >= 636 && jugadorX > 630 && jugadorX < 1430) {
	        	jugadorY = 636;
	        }
	        
    }

    
    private void iniciarAnimacionFadeout() {
        // Iniciar animación de fadeout
        haMuerto = true;
        enFadeout = true;
        Timer fadeoutTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajustar la transparencia gradualmente durante el tiempo de fadeout
                transparenciaJugador -= 0.01f; // Puedes ajustar el paso de transparencia según tus preferencias

                if (transparenciaJugador <= 0.0f) {
                    // Terminar animación de fadeout y hacer respawn del jugador
                    enFadeout = false;
                    transparenciaJugador = 1.0f;
                    jugadorX = 200;
                    jugadorY = 500;
                    haMuerto = false; // Indicar que la animación ha terminado
                    ((Timer) e.getSource()).stop(); // Detener el temporizador después de la animación
                }
            }
        });
        fadeoutTimer.setDelay(tiempoFadeout / 100); // Ajustar el retraso del temporizador
        fadeoutTimer.start();
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