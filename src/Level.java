package BubbleTrouble;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.lang.*;

public class Level {
    ArrayList<Bubble> levelMap = new ArrayList<>();
    ArrayList<Bubble> specialMap = new ArrayList<>();
    private SoundControl sfx = new SoundControl();
    private BufferedImage BG;
    private BufferedImage spikes;
    private BufferedImage floor;
    private String path;

    Level(BufferedImage BG, BufferedImage spikes, BufferedImage floor) {
        this.BG = BG;
        this.spikes = spikes;
        this.floor = floor;

    }
    void init (int level) {
        if (levelMap.size() > 0) {
            levelMap.clear();
        }
        if (specialMap.size() > 0) {
            specialMap.clear();
        }
        if (level == 1) {
            levelMap.add(new Bubble(700, 400, 0, -1, "MediumBall.PNG"));

        }
        else if (level == 2) {
            levelMap.add(new Bubble(700, 400, 0, 1, "MediumBall.PNG"));
            levelMap.add(new Bubble(500, 400, 0, -1, "MediumBall.PNG"));
        }
        else if (level == 3) {
            levelMap.add(new Bubble(500, 300, 0, -1, "BigBall.PNG"));
            specialMap.add(new Bubble(900, 100, 0, -1.2, "SpecialBall2.PNG"));
        }
        else if (level == 4) {
            levelMap.add(new Bubble(700, 300, 0, 1, "BigBall.PNG"));
            levelMap.add(new Bubble(500, 300, 0, -1, "BigBall.PNG"));
        }
        else if (level == 5) {
            levelMap.add(new Bubble(500, 300, 0, -1, "GiantBall.PNG"));
        }
        else if (level == 6) {
            levelMap.add(new Bubble(700, 300, 0, 1, "GiantBall.PNG"));
            levelMap.add(new Bubble(500, 300, 0, -1, "GiantBall.PNG"));
            specialMap.add(new Bubble(900, 100, 0, -1.2, "SpecialBall1.PNG"));
        }
        else if (level == 7) {
            levelMap.add(new Bubble(700, 400, 0, 2, "MediumBall.PNG"));
            levelMap.add(new Bubble(800, 400, 0, 2, "MediumBall.PNG"));
            //levelMap.add(new Bubble(400, 400, 0, 2, "MediumBall.PNG"));
            levelMap.add(new Bubble(500, 400, 0, 2, "MediumBall.PNG"));
            levelMap.add(new Bubble(600, 400, 0, 2, "MediumBall.PNG"));
            //levelMap.add(new Bubble(900, 400, 0, 2, "MediumBall.PNG"));
        }

        else if (level == 8) {
            levelMap.add(new Bubble(240, 500, 0, 2.2, "SmallBall.PNG"));
            levelMap.add(new Bubble(320, 500, 0, 2.2, "SmallBall.PNG"));
            levelMap.add(new Bubble(400, 500, 0, 2.2, "SmallBall.PNG"));
            levelMap.add(new Bubble(480, 500, 0, 2.2, "SmallBall.PNG"));
            levelMap.add(new Bubble(560, 500, 0, 2.2, "SmallBall.PNG"));
            levelMap.add(new Bubble(640, 500, 0, -2.2, "SmallBall.PNG"));
            levelMap.add(new Bubble(720, 500, 0, -2.2, "SmallBall.PNG"));
            levelMap.add(new Bubble(800, 500, 0, -2.2, "SmallBall.PNG"));
            levelMap.add(new Bubble(880, 500, 0, -2.2, "SmallBall.PNG"));
            levelMap.add(new Bubble(960, 500, 0, -2.2, "SmallBall.PNG"));

        }
        else if (level == 9) {
            levelMap.add(new Bubble(340, 500, 0, 1.5, "MediumBall.PNG"));
            levelMap.add(new Bubble(420, 500, 0, 1.5, "MediumBall.PNG"));
            levelMap.add(new Bubble(500, 500, 0, 1.5, "MediumBall.PNG"));
            levelMap.add(new Bubble(580, 500, 0, -1.5, "MediumBall.PNG"));
            levelMap.add(new Bubble(660, 500, 0, -1.5, "MediumBall.PNG"));
            levelMap.add(new Bubble(740, 500, 0, -1.5, "MediumBall.PNG"));
            //levelMap.add(new Bubble(720, 500, 0, -1.5, "SmallBall.PNG"));
            //levelMap.add(new Bubble(800, 500, 0, -1.5, "SmallBall.PNG"));

        }
        else if (level == 10) {
            levelMap.add(new Bubble(500, 300, 0, 3, "BigBall.PNG"));
            levelMap.add(new Bubble(700, 300, 0, -3, "BigBall.PNG"));

        }
        else if (level == 11) {
            levelMap.add(new Bubble(500, 300, 0, 1, "MegaBall.PNG"));
        }
    }


    void update() {
        for (int i = 0; i < levelMap.size(); i++) {
            levelMap.get(i).update();
            //levelMap.get(i).print();
            if (levelMap.get(i).checkCeilingCollision()) {
                levelMap.remove(i);
                sfx.outPutAudio("Pop.wav", 12.0f);
            }
        }
        for (int i = 0; i < specialMap.size(); i++) {
            specialMap.get(i).update();
            if (specialMap.get(i).x < -35 || specialMap.get(i).x > GameStart.SCREEN_WIDTH) {
                specialMap.remove(i);
            }
        }
    }

    String projectileCollision(Rectangle bullet) {
        for (int i = 0; i < levelMap.size(); i++) {
            if (levelMap.get(i).hitbox.intersects(bullet)) {
                switch (levelMap.get(i).path) {
                    case "MediumBall.PNG":
                        levelMap.add(new Bubble(levelMap.get(i).x, levelMap.get(i).y,-3.2, Math.abs(levelMap.get(i).direction) + .2, "SmallBall.PNG"));
                        levelMap.add(new Bubble(levelMap.get(i).x, levelMap.get(i).y,-3.2, -(Math.abs(levelMap.get(i).direction) + .2), "SmallBall.PNG"));
                        levelMap.remove(i);
                        sfx.outPutAudio("Pop.wav", 12.0f);
                        return "Norm";

                    case "BigBall.PNG":
                        levelMap.add(new Bubble(levelMap.get(i).x, levelMap.get(i).y, -2.9, Math.abs(levelMap.get(i).direction) + .2, "MediumBall.PNG"));
                        levelMap.add(new Bubble(levelMap.get(i).x, levelMap.get(i).y, -2.9, -(Math.abs(levelMap.get(i).direction) + .2), "MediumBall.PNG"));
                        levelMap.remove(i);
                        sfx.outPutAudio("Pop.wav", 12.0f);
                        return "Norm";

                    case "GiantBall.PNG":
                        levelMap.add(new Bubble(levelMap.get(i).x, levelMap.get(i).y, -2.6, Math.abs(levelMap.get(i).direction) + .2, "BigBall.PNG"));
                        levelMap.add(new Bubble(levelMap.get(i).x, levelMap.get(i).y, -2.6, -(Math.abs(levelMap.get(i).direction) + .2), "BigBall.PNG"));
                        levelMap.remove(i);
                        sfx.outPutAudio("Pop.wav", 12.0f);
                        return "Norm";

                    case "MegaBall.PNG":
                        levelMap.add(new Bubble(levelMap.get(i).x, levelMap.get(i).y, -1.7, Math.abs(levelMap.get(i).direction) + .2, "GiantBall.PNG"));
                        levelMap.add(new Bubble(levelMap.get(i).x, levelMap.get(i).y, -1.7, Math.abs(levelMap.get(i).direction) + .4, "GiantBall.PNG"));
                        levelMap.add(new Bubble(levelMap.get(i).x, levelMap.get(i).y, -1.7, -Math.abs(levelMap.get(i).direction + .4), "GiantBall.PNG"));
                        levelMap.add(new Bubble(levelMap.get(i).x, levelMap.get(i).y, -1.7, -(Math.abs(levelMap.get(i).direction) + .2), "GiantBall.PNG"));
                        levelMap.remove(i);
                        sfx.outPutAudio("Pop.wav", 12.0f);
                        return "Norm";

                    default:
                        levelMap.remove(i);
                        sfx.outPutAudio("Pop.wav", 12.0f);
                        return "Norm";

                }
            }
        }
        for (int i = 0; i < specialMap.size(); i++) {
            if (specialMap.get(i).hitbox.intersects(bullet)) {
                sfx.outPutAudio("Pop.wav", 12.0f);
                path = specialMap.get(i).path;
                specialMap.remove(i);
                return path;
            }

            }
        return "Test?";
    }

    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.BG, 0, 0, null);
        for (int i = 0; i < BG.getWidth(); i++) {
            g2d.drawImage(this.spikes, i*this.spikes.getWidth(), 0 , null);
        }

    }

    void drawFloor(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (GameStart.State != GameStart.STATE.GAME_OVER) {
            for (int i = 0; i < levelMap.size(); i++) {
                levelMap.get(i).draw(g);
            }
            for (int i = 0; i < specialMap.size(); i++) {
                specialMap.get(i).draw(g);
            }
        }
        g2d.drawImage(this.floor, 0, 625, null);
    }
}
