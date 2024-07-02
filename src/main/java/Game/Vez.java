package Game;

import java.awt.*;

public class Vez {
    int x;
    int y;
    int sirka;
    int vyska;
    Image img;
    boolean passed;

    Vez(Image img, int x, int y, int sirka, int vyska) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.sirka = sirka;
        this.vyska = vyska;
        this.passed=false;
    }
}