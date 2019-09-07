package BubbleTrouble;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MenuControl implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();
        if (GameStart.State == GameStart.STATE.MENU) {
            if (mx >= GameStart.SCREEN_WIDTH / 2 - 110 && mx <= GameStart.SCREEN_WIDTH / 2 + 40) {
                if (my >= 180 && my <= 230) {
                    GameStart.State = GameStart.STATE.ONE_PLAYER;
                }
                if (my >= 280 && my <= 330) {
                    GameStart.State = GameStart.STATE.TWO_PLAYER;
                }
                if (my >= 380 && my <= 430) {
                    GameStart.State = GameStart.STATE.QUIT;
                }
            }
        }
        else if (GameStart.State == GameStart.STATE.GAME_OVER || GameStart.State == GameStart.STATE.YOU_WIN){
            if (GameStart.SCREEN_WIDTH >= my && my >= 0) {
                GameStart.State = GameStart.STATE.MENU;
            }
        }



    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
