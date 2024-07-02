package Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

public class FlappyPlane extends JPanel implements ActionListener, KeyListener {
    int sirka = 720;
    int vyska = 1280;

    // Window size
    int windowSirka = 360; // Half of sirka
    int windowVyska = 640; // Half of vyska

    Image letadloImg;
    Image vezePozadiImg;
    Image vezDoleImg;
    Image vezNahoreImg;

    int letadloX = sirka / 8;
    int letadloY = vyska / 2;
    int letadloSirka = 120;
    int letadloVyska = 60;

    int vezX = sirka;
    int vezY = 0;
    int vezSirka = 150;
    int vezVyska = 780;
    int mezeraMeziVezemi = 600;

    Letadlo letadlo;
    int rychlostY = 0;
    int rychlostX = -10;
    int gravitace = 1;

    ArrayList<Vez> veze;

    Timer gameLoop;
    Timer polozVezeTimer;
    double skore = 0;
    boolean gameOver = false;

    FlappyPlane() {
        setPreferredSize(new Dimension(windowSirka, windowVyska));
        setFocusable(true);
        addKeyListener(this);

        letadloImg = new ImageIcon(getClass().getResource("/images/letadlo.png")).getImage();
        vezePozadiImg = new ImageIcon(getClass().getResource("/images/towers_bg.png")).getImage();
        vezDoleImg = new ImageIcon(getClass().getResource("/images/vez_dole.png")).getImage();
        vezNahoreImg = new ImageIcon(getClass().getResource("/images/vez_nahore.png")).getImage();

        letadlo = new Letadlo(letadloImg, letadloX, letadloY, letadloSirka, letadloVyska);
        veze = new ArrayList<>();

        polozVezeTimer = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                polozVeze();
            }
        });
        polozVezeTimer.start();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    public void polozVeze() {
        int randomHeight = (int) (Math.random() * (vyska / 2));
        Vez vezNahore = new Vez(vezNahoreImg, sirka, randomHeight - vezVyska, vezSirka, vezVyska);
        Vez vezDole = new Vez(vezDoleImg, sirka, randomHeight + mezeraMeziVezemi, vezSirka, vezVyska);
        veze.add(vezNahore);
        veze.add(vezDole);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double scaleX = (double) windowSirka / sirka;
        double scaleY = (double) windowVyska / vyska;
        g2d.scale(scaleX, scaleY);

        g.drawImage(vezePozadiImg, 0, 0, sirka, vyska, null);
        g.drawImage(letadlo.img, letadlo.x, letadlo.y, letadlo.sirka, letadlo.vyska, null);

        for (Vez vez : veze) {
            g.drawImage(vez.img, vez.x, vez.y, vez.sirka, vez.vyska, null);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Konec Hry: " + (int) skore, 10, 35);
        } else {
            g.drawString(String.valueOf((int) skore), 10, 35);
        }
    }

    public void pohyb() {
        letadlo.y += rychlostY;
        letadlo.y = Math.max(letadlo.y, 0);
        rychlostY += gravitace;

        for (Vez vez : veze) {
            vez.x += rychlostX;
            if (!vez.passed && letadlo.x > vez.x + vez.sirka) {
                skore += 0.5; //0.5 because there are 2 pipes! so 0.5*2 = 1, 1 for each set of pipes
                vez.passed = true;
            }

            if (kolize(letadlo, vez)) {
                gameOver = true;
            }
        }

        if (letadlo.y > vyska) {
            gameOver = true;
        }
    }

    public boolean kolize(Letadlo a, Vez b) {
        return a.x < b.x + b.sirka &&   //a's top left corner doesn't reach b's top right corner
                a.x + a.sirka > b.x &&   //a's top right corner passes b's top left corner
                a.y < b.y + b.vyska &&  //a's top left corner doesn't reach b's bottom left corner
                a.y + a.vyska > b.y;    //a's bottom left corner passes b's top left corner
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pohyb();
        repaint();
        if (gameOver) {
            polozVezeTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // System.out.println("JUMP!");
            rychlostY = -20;

            if (gameOver) {
                //restart game by resetting conditions
                letadlo.y = letadloY;
                rychlostY = 0;
                veze.clear();
                gameOver = false;
                skore = 0;
                gameLoop.start();
                polozVezeTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}