package BubbleTrouble;
import java.awt.*;

class Menu {
    private Rectangle playButton = new Rectangle(GameStart.SCREEN_WIDTH/2 - 110, 150, 150, 50);
    private Rectangle helpButton = new Rectangle(GameStart.SCREEN_WIDTH/2 - 110, 250, 150, 50);
    private Rectangle quitButton = new Rectangle(GameStart.SCREEN_WIDTH/2 - 110, 350, 150, 50);

    void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (GameStart.State == GameStart.STATE.GAME_OVER) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("AR Destine", Font.PLAIN, 34));
            g2.drawString("GAME OVER", 900, 100);
            //g2.drawString("Press anywhere on screen", 900, 300);
        }
        else if (GameStart.State == GameStart.STATE.YOU_WIN) {
            g2.setColor(Color.BLUE);
            g2.setFont(new Font("AR Destine", Font.PLAIN, 34));
            g2.drawString("YOU WIN!", 900, 100);
        }
        else {
            g.setFont(new Font("AR Destine", Font.BOLD, 50));
            g.setColor(new Color(255, 100, 0));
            g.drawString("BUBBLE TROUBLE", GameStart.SCREEN_WIDTH / 2 - 250, 100);

            g.setFont(new Font("AR Destine", Font.BOLD, 30));

            g.setColor(Color.RED);
            g2.draw(playButton);
            g.drawString("1 Player", playButton.x + 5, playButton.y + 34);

            g.setColor(new Color(0, 255, 0));
            g2.draw(helpButton);
            g.drawString("2 Player", helpButton.x + 2, helpButton.y + 34);

            g.setColor(Color.black);
            g2.draw(quitButton);
            g.drawString("Quit", quitButton.x + 40, quitButton.y + 34);
        }

    }

}
