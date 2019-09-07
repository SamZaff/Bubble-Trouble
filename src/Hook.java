package BubbleTrouble;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static javax.imageio.ImageIO.read;

public class Hook {
    private double x;
    double y = 583;
    private double velY;
    private String path;
    Rectangle hitBox;
    private BufferedImage img;
    private BufferedImage img2;
    private boolean special;
    Hook(double x, double velY, String path, boolean special) {
        this.x = x;
        this.special = special;
        this.velY = velY;
        this.path = path;
        try {
            img = read(new File("Resources/" + path));
            img2 = read(new File("Resources/HookTrap.PNG"));
        }
        catch (IOException io) {
            System.err.println("Cannot read input file!");
        }
        hitBox = new Rectangle((int)x, (int)y, img.getWidth(), img.getHeight());
    }

    void update() {
        if (!special || (special && y > 10)) {
            /*y -= 2.4;
            hitBox.y -= 2.4;*/
            y -= velY;
            hitBox.y = (int)y;
        }
        else if (special) {
            hitBox.width = img2.getWidth();
        }
    }

    void draw(Graphics g) {
        //AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        //rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        if (y > 15) {
            g2d.drawImage(this.img, (int) x, (int) y, null);
        }
        else if (y < 15 && special) {
            g2d.drawImage(this.img2, (int) x - 10, (int) y, null);
        }
    }
}
