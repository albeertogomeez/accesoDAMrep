package juegoProvisional;

import javax.swing.*;

public class Juego extends JFrame {

    public Juego() {
        setTitle("Juego Básico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Ajusta el tamaño según tus preferencias
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Crea un nuevo panel de juego y agrégalo al marco
        PanelJuego panelJuego = new PanelJuego();
        add(panelJuego);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Juego();
        });
    }
}
