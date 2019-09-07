package BubbleTrouble;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 *
 * @author Sam Zaffanella
 */
public class PlayerControl implements KeyListener {

    private Player p;
    private final int right;
    private final int left;
    private final int shoot;
    private final int escape;

    PlayerControl(Player p, int left, int right, int shoot, int escape) {
        this.p = p;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
        this.escape = escape;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == left) {
            this.p.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.p.toggleRightPressed();
        }
        if (keyPressed == shoot) {
            this.p.toggleShooting();
        }
        if (keyPressed == escape) {
            this.p.togglePause();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == left) {
            this.p.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.p.unToggleRightPressed();
        }
        if (keyReleased  == shoot) {
            this.p.unToggleShooting();
        }
        if (keyReleased == escape) {
            this.p.unTogglePause();
        }
    }
}