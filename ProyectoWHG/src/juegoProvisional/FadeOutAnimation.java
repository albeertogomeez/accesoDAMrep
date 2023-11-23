package juegoProvisional;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class FadeOutAnimation extends JFrame {
    private MyPanel panel;
    private Timer timer;
    private float opacity = 1.0f;

    public FadeOutAnimation() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new BorderLayout());

        panel = new MyPanel();
        add(panel, BorderLayout.CENTER);

        JButton fadeButton = new JButton("Fade Out");
        fadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startFadeOutAnimation();
            }
        });
        add(fadeButton, BorderLayout.SOUTH);

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                if (opacity <= 0) {
                    opacity = 0;
                    timer.stop();
                }
                panel.setPanelOpacity(opacity);
                panel.repaint();
            }
        });
    }

    private void startFadeOutAnimation() {
        timer.start();
    }

    private static class MyPanel extends JPanel {
        private float panelOpacity = 1.0f;

        public void setPanelOpacity(float alpha) {
            this.panelOpacity = alpha;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            int width = getWidth();
            int height = getHeight();

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gImage = bufferedImage.createGraphics();

            // Dibuja cualquier contenido que quieras en el panel aquí
            gImage.setColor(Color.BLUE);
            gImage.fillRect(0, 0, width, height);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, panelOpacity));
            g2d.drawImage(bufferedImage, 0, 0, null);

            gImage.dispose();
            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FadeOutAnimation().setVisible(true);
            }
        });
    }
}

