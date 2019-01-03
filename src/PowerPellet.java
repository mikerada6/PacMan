import java.awt.*;

public class PowerPellet extends Pellet {

    private Ghost[] ghosts;
    public PowerPellet(int x, int y, Ghost[] ghosts)
    {
        super(x,y);
        this.ghosts=ghosts;
        this.score=50;

    }

    public int eat() {
        int ans = super.eat();
        for(Ghost g: ghosts)
        {
            g.setMode(Ghost.SCATTER);
        }
        return ans;
    }

    public void draw(Graphics2D g) {
        if (isAlive) {
            g.setColor(Color.WHITE);
            g.fillOval(this.x * GamePanel.TILESIZE + GamePanel.TILESIZE / 4, this.y * GamePanel.TILESIZE + GamePanel.TILESIZE / 4, GamePanel.TILESIZE / 2, GamePanel.TILESIZE / 2);
        }
    }
}
