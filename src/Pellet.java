import java.awt.*;

public class Pellet {
    protected int x;
    protected int y;
    protected boolean isAlive;
    protected int score;

    public Pellet(int x, int y) {
        this.x = x;
        this.y = y;
        isAlive = true;
        score=10;
    }

    public void draw(Graphics2D g) {
        if (isAlive) {
            g.setColor(Color.WHITE);
            g.fillOval(this.x * GamePanel.TILESIZE + GamePanel.TILESIZE / 2, this.y * GamePanel.TILESIZE + GamePanel.TILESIZE / 2, GamePanel.TILESIZE / 4, GamePanel.TILESIZE / 4);
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
