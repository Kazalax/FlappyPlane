package Game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        int windowSirka = 360; // Half of sirka
        int windowVyska = 640; // Half of vyska
        JFrame frame = new JFrame("Flappy Plane");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowSirka, windowVyska);

        FlappyPlane flappyPlane = new FlappyPlane();
        frame.add(flappyPlane);
        frame.pack();
        flappyPlane.requestFocus();
        frame.setVisible(true);
    }
}