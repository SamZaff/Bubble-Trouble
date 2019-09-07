package BubbleTrouble;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static javax.imageio.ImageIO.read;

public class Bubble {
    double x;
    double y;
    double direction;
    String path;
    private double velY;

    private BufferedImage img;
    private double grav = .03;
    Rectangle hitbox;


    Bubble(double x, double y, double velY, double direction, String path) {
        this.x = x;
        this.y = y;
        this.velY = velY;
        this.direction = direction;
        this.path = path;
        if (path.contains("Special")) {
            grav = -grav;
        }
        try {
            this.img = read(new File("Resources/" + path));
            hitbox = new Rectangle((int)x, (int)y, img.getWidth(), img.getHeight());
        }
        catch(IOException e) {
            System.out.println("Test!");
        }
    }

    void update() {
        x += direction;
        hitbox.x = (int)x;
         if (path.contains("Special") && y < 0) {
            velY = 2.5;
             while (y > 0) {
                 y += velY;
                 hitbox.y = (int)y;
             }
        }
        else if (y > 630 - (img.getHeight())) {
            if (path.equals("SmallBall.PNG")) {
                velY = -2.3;
            } else if (path.equals("MediumBall.PNG")) {
                velY = -2.7;
            } else if (path.equals("BigBall.PNG")) {
                velY = -3.4;
            } else if (path.equals("GiantBall.PNG")) {
                velY = -4.1;
            }
            else if (path.equals("MegaBall.PNG")) {
                velY = -3.8;
            }

            while (y > 625 - (img.getHeight())) {
                y += velY;
                hitbox.y = (int)y;
            }
        }
        velY += grav;
        y += velY;
        hitbox.y = (int) y;

        if (!path.contains("Special") && x > 1265 -( img.getWidth()) || x < 0) {
            direction *= -1;
        }
    }

    boolean checkCeilingCollision() {
        if (y < 15 && !path.contains("Special")) {
            return true;
        }
        return false;
    }

    public void print() {
        System.out.println("hitbox x=" + hitbox.x + ", hitbox y=" + hitbox.y);
        System.out.println("x=" + x + ", y=" + y);
        System.out.println();
        //System.out.println("Ground x = " + ground.x + ", y = " + ground.y + " , width = " + ground.width + ", height = " + ground.height);
    }

    void draw(Graphics g) {
        //AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        //rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, (int)x, (int)y, null);
    }
}
