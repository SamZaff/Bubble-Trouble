package BubbleTrouble;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
/**
 *
 * @author Sam Zaffanella
 */
public class Player {
    double x = 50;
    double origX;
    int y;
    int heartCount;
    private int heartY;
    private double R = 1.7;
    private BufferedImage Heart;
    private boolean special = false;
    private Rectangle hitbox;
    boolean weaponActive = false;
    private SoundControl sfx = new SoundControl();
    Hook weapon;
    private String weaponType = "hook.PNG";
    private double weaponVelY = 2.4;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private boolean pausePressed;
    boolean pauseScreen;
    private int counter = 8;
    private int sprite = 4;
    private ArrayList<Image> spriteSheet = new ArrayList<>();

    Player(int y, int heartY, BufferedImage img, BufferedImage Heart) {
        this.heartY = heartY;
        this.y = y;
        this.Heart = Heart;
        hitbox = new Rectangle((int)x, y, 41, 63);
        for (int i = 0; i < 6; i++) {
            spriteSheet.add(img.getSubimage(i*41, 0, 41, 63));
        }
    }

    void init(double x, int heartCount) {
        origX = x;
        this.x = x;
        this.heartCount = heartCount;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {

        this.LeftPressed = true;
    }

    void toggleShooting(){
        this.ShootPressed = true;

    }


    void togglePause() {this.pausePressed = true; }


    void unToggleRightPressed() {
        sprite = 4;
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        sprite = 4;
        this.LeftPressed = false;
    }

    void unToggleShooting(){
        sprite = 4;
        this.ShootPressed = false;
    }


    void unTogglePause() {this.pausePressed = false; }


    void update() {
        if (!pauseScreen) {
            if (this.LeftPressed && !this.RightPressed) {
                this.moveLeft();
            }
            if (this.RightPressed && !this.LeftPressed) {
                this.moveRight();
            }
            if (this.ShootPressed) {
                this.shootWeapon();
            }
            if (weaponActive) {
                weapon.update();
            }
        }
        if (this.pausePressed) {
            this.pauseGame();
            this.pausePressed = false;
        }
    }

    private void pauseGame() {
        pauseScreen = !pauseScreen;
    }

    private void moveLeft() {
        counter++;
        if (!RightPressed) {
            if (counter % 30 < 15) {
                sprite = 2;
            } else {
                sprite = 3;
            }
        }
        else {
            sprite = 4;
        }
        if (x > 0) {
            x -= R;
            hitbox.x = (int)x;
        }

    }

    private void moveRight() {
        counter++;
        if (!LeftPressed) {
            if (counter % 30 < 15) {
                sprite = 0;
            } else {
                sprite = 1;
            }
        }
        else {
            sprite = 4;
        }
        if (x < 1190) {
            x += R;
            hitbox.x = (int)x;
        }

    }

    private void shootWeapon() {
        if (GameStart.State == GameStart.STATE.GAME_OVER) {
            GameStart.State = GameStart.STATE.MENU;
        }
        if (!LeftPressed && !RightPressed && !weaponActive) {
            sprite = 5;
        }
        if (!weaponActive) {
            sfx.outPutAudio("Throw.wav", 12.0f);
            //weapon = new Hook(x + 10, 7, "shuriken.PNG", special);
            weapon = new Hook(x + 10, weaponVelY, weaponType, special);
            weaponActive = true;
        }
    }


    boolean bubbleCollisionCheck(Rectangle bubble) {
        if (this.hitbox.intersects(bubble)) {
            sfx.outPutAudio("Hit.wav", 12.0f);
            return true;
        }
        return false;
    }

    void resetWeapon() {
        weaponActive = false;
        weapon.hitBox.y = 700;
    }

    void resetPlayer(boolean lifeLost) {
        if (lifeLost && heartCount > 1) {
            heartCount--;
            x = origX;
        }
        else if (lifeLost) {
            heartCount--;
            GameStart.State = GameStart.STATE.GAME_OVER;
        }
        else {
            x = origX;
        }

    }

    void PowerUp(String path) {
        if (path.contains("1")) {
            weaponType = "shuriken.PNG";
            weaponVelY = 7;
            special = false;
        }
        else if (path.contains("2")) {
            weaponType = "Hook.PNG";
            weaponVelY = 2.4;
            special = true;
        }
        else if (path.contains("3")) {
            weaponType = "Hook.PNG";
            weaponVelY = 2.4;
            special = false;
        }
    }

    void draw(Graphics g) {
        hitbox.x = (int)x;
        hitbox.y = y;
        Graphics2D g2d = (Graphics2D) g;
        if (weaponActive) {
            weapon.draw(g);
            if (weapon.y < 15 && !special) {
                weaponActive = false;
            }
        }
        if (pauseScreen) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("AR Destine", Font.PLAIN, 34));
            g2d.drawString("PAUSED", 1100, 100);
        }
        g2d.drawImage(this.Heart, 30, heartY, null);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Chiller", Font.PLAIN, 34));
        g.drawString("x" + Integer.toString(heartCount), 80, heartY + 30);
        //g2d.drawImage(this.filter, -1260 + (int)x, 0, null);
        g2d.drawImage(spriteSheet.get(sprite), (int)x, y, null);
    }
}