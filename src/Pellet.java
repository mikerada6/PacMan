import java.awt.*;

public class Pellet {
    private int x;
    private int y;
    private boolean isAlive;
    private int score = 10;

    public Pellet(int x, int y) {
        this.x = x;
        this.y = y;
        isAlive = true;
    }

    public void draw(Graphics2D g) {
        if (isAlive) {
            g.setColor(Color.WHITE);
            g.fillOval(this.x * GamePanel.TILESIZE + GamePanel.TILESIZE / 4, this.y * GamePanel.TILESIZE + GamePanel.TILESIZE / 4, GamePanel.TILESIZE / 2, GamePanel.TILESIZE / 2);
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int eat() {
        isAlive = false;
        return score;
    }

    public int getX() {
        return x;

    }

    public int getY() {
        return y;
    }


}
