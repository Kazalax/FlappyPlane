package Game;

import java.awt.*;

public class Letadlo {
    int x;
    int y;
    int sirka;
    int vyska;
    Image img;

    Letadlo(Image img, int x, int y, int sirka, int vyska) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.sirka = sirka;
        this.vyska = vyska;
    }
}