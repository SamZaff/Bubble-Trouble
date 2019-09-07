package BubbleTrouble;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.KeyEvent;
        import java.awt.image.BufferedImage;
        import java.io.IOException;
        import java.io.File;
        import static javax.imageio.ImageIO.read;

/**
 *
 * @author Sam Zaffanella
 */
public class GameStart extends JPanel  {

    static final int SCREEN_WIDTH = 1280;
    static final int SCREEN_HEIGHT = 690;
    private BufferedImage world;
    private Graphics2D buffer;
    private Level lvl;
    private JFrame jf;
    private Player p1;
    private Player p2;
    private SoundControl sfx = new SoundControl();
    private int levelChecker = 11;
    private Menu menu;
    private String path;
    private BufferedImage backGround = null, floor = null, spikes = null;
    public enum STATE {
        MENU,
        ONE_PLAYER,
        TWO_PLAYER,
        GAME_OVER,
        YOU_WIN,
        QUIT
    }

    public static STATE State = STATE.MENU;

    public static void main(String[] args) {
        GameStart gamex = new GameStart();
        SoundControl music = new SoundControl();
        gamex.init();
        try {
            while (true) {
                if (State == STATE.ONE_PLAYER) {
                    if (gamex.p1.heartCount == 0) {
                        gamex.p1.init(50, 7);
                    }
                        gamex.p1.update();
                        if (!gamex.p1.pauseScreen) {
                            gamex.lvl.update();
                        }
                        for (int i = 0; i < gamex.lvl.levelMap.size(); i++) {
                            if (gamex.p1.bubbleCollisionCheck(gamex.lvl.levelMap.get(i).hitbox)) {
                                gamex.lvl.init(gamex.levelChecker);
                                gamex.p1.resetPlayer(true);
                                gamex.p1.weaponActive = false;
                            }
                            if (gamex.p1.weaponActive) {
                                gamex.path = gamex.lvl.projectileCollision(gamex.p1.weapon.hitBox);
                                if (gamex.path.equals("Norm")) {
                                    gamex.p1.resetWeapon();
                                }
                                if (gamex.path.contains("Special")) {
                                    gamex.p1.PowerUp(gamex.path);
                                    gamex.p1.resetWeapon();
                                }
                            }
                        }
                        if (gamex.lvl.levelMap.size() == 0 && gamex.levelChecker < 11) {
                            gamex.levelChecker++;
                            gamex.lvl.init(gamex.levelChecker);
                            gamex.p1.resetPlayer(false);
                        }
                        else if (gamex.lvl.levelMap.size() == 0) {
                            State = STATE.YOU_WIN;
                            gamex.p1.init(50, 0);
                            gamex.levelChecker = 0;
                        }

                }
                else if (State == STATE.TWO_PLAYER) {
                    if (gamex.p1.heartCount == 0 || gamex.p2.heartCount == 0) {
                        gamex.p1.init(50, 4);
                        gamex.p2.init(1100, 4);
                    }
                        gamex.p1.update();
                        gamex.p2.update();
                        if (!gamex.p1.pauseScreen) {
                            gamex.lvl.update();
                        }
                        for (int i = 0; i < gamex.lvl.levelMap.size(); i++) {
                            if (gamex.p1.bubbleCollisionCheck(gamex.lvl.levelMap.get(i).hitbox)) {
                                gamex.lvl.init(gamex.levelChecker);
                                gamex.p1.resetPlayer(true);
                                gamex.p2.resetPlayer(false);
                                gamex.p1.weaponActive = false;
                                gamex.p2.weaponActive = false;
                            }
                            if (gamex.p1.weaponActive) {
                                gamex.path = gamex.lvl.projectileCollision(gamex.p1.weapon.hitBox);
                                if (gamex.path.equals("Norm")) {
                                    gamex.p1.resetWeapon();
                                }
                                if (gamex.path.contains("Special")) {
                                    gamex.p1.PowerUp(gamex.path);
                                    gamex.p1.resetWeapon();
                                }
                            }

                            if (gamex.lvl.levelMap.size()!= 0) {
                                if (gamex.p2.bubbleCollisionCheck(gamex.lvl.levelMap.get(i).hitbox)) {
                                    gamex.lvl.init(gamex.levelChecker);
                                    gamex.p1.resetPlayer(false);
                                    gamex.p2.resetPlayer(true);
                                    gamex.p1.weaponActive = false;
                                    gamex.p2.weaponActive = false;
                                }
                            }
                        }
                            if (gamex.p2.weaponActive) {
                                gamex.path = gamex.lvl.projectileCollision(gamex.p2.weapon.hitBox);
                                if (gamex.path.equals("Norm")) {
                                    gamex.p2.resetWeapon();
                                }
                                if (gamex.path.contains("Special")) {
                                    gamex.p2.PowerUp(gamex.path);
                                    gamex.p2.resetWeapon();
                                }
                            }
                        if (gamex.lvl.levelMap.size() == 0 && gamex.levelChecker < 11) {
                            gamex.levelChecker++;
                            gamex.lvl.init(gamex.levelChecker);
                            gamex.p1.resetPlayer(false);
                            gamex.p2.resetPlayer(false);
                        }
                        else if (gamex.lvl.levelMap.size() == 0) {
                            State = STATE.YOU_WIN;
                            gamex.p1.init(50, 0);
                            gamex.p2.init(1100, 0);
                            gamex.levelChecker = 0;
                        }
                }
                else if (State == STATE.GAME_OVER && gamex.levelChecker > 1) {
                    gamex.lvl.init(gamex.levelChecker = 1);
                    gamex.p1.init(50, 0);
                    gamex.p2.init(1100, 0);
                    gamex.p1.PowerUp("3");
                    gamex.p2.PowerUp("3");
                }
                else if (State == STATE.QUIT) {
                    System.exit(0);
                }
                gamex.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }

    }

    private void init() {
        this.jf = new JFrame("Bubble Trouble");
        this.world = new BufferedImage(GameStart.SCREEN_WIDTH, GameStart.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage p1img = null, p2img = null, Life1 = null, Life2 = null;
        try {
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
            File f = new File("Resources/redNinjaSpriteSheet.PNG");
            System.out.println(f.getAbsolutePath());
            p1img = read(f);
            backGround = read(new File("Resources/backGround.PNG"));
            spikes = read(new File("Resources/Spikes.png"));
            floor = read(new File("Resources/Floor.png"));
            Life1 = read(new File("Resources/ninjafacered.PNG"));
            Life2 = read(new File("Resources/ninjafacegreen.PNG"));
            p2img = read(new File("Resources/greenNinjaSpriteSheet.PNG"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        p1 = new Player(562, 50, p1img, Life1);
        p2 = new Player(562, 100, p2img, Life2);
        lvl = new Level(backGround, spikes, floor);
        lvl.init(levelChecker);
        menu = new Menu();
        PlayerControl pc1 = new PlayerControl(p1, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE);
        PlayerControl pc2 = new PlayerControl(p2, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE);
        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);

        this.jf.addKeyListener(pc1);
        this.jf.addKeyListener(pc2);
        this.jf.addMouseListener(new MenuControl());
        this.jf.setSize(GameStart.SCREEN_WIDTH, GameStart.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("AR Delaney", Font.PLAIN, 34));
        buffer = world.createGraphics();
        super.paintComponent(g2);
        this.lvl.draw(buffer);
        this.p1.draw(buffer);
        if (State == STATE.ONE_PLAYER) {
            this.lvl.drawFloor(buffer);
        }
        else if (State == STATE.TWO_PLAYER) {
            this.p2.draw(buffer);
            this.lvl.drawFloor(buffer);
        }
        else if (State == STATE.MENU || State == STATE.GAME_OVER || State == STATE.YOU_WIN) {
            menu.draw(buffer);
        }
        g2.drawImage(world,0,0,null);


    }

}