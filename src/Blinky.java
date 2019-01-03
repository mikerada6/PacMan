import java.awt.*;
import java.util.ArrayList;

public class Blinky extends Ghost {
    private boolean drawPath = true;
    private ArrayList<Tile> path;

    public Blinky(int x, int y) {
        super(x, y);
        path = new ArrayList<Tile>();
        super.name = "Blinky";
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = GamePanel.spriteSheet.grabImage(i + 1, 5, 16, 16);
            this.homeX = 35;
            this.homeY = 0;
        }
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        if (drawPath) {
            if (path.size() >= 1) {
                for (int i = 1; i < path.size(); i++) {
                    Tile one = path.get(i);
                    Tile two = path.get(i - 1);
                    g.setColor(Color.RED);
                    g.setStroke(new BasicStroke(5));
                    g.drawLine(one.getCenterXPixel(), one.getCenterYPixel(), two.getCenterXPixel(), two.getCenterYPixel());
                }
            }

        }
    }

    public void getPath() {
        Tile[][] copy = Ghost.copy(GamePanel.board);

        aStar astar = new aStar(GamePanel.board[this.x][this.y], GamePanel.board[GamePanel.p.getX()][GamePanel.p.getY()], copy);
        path = astar.A_Star();
    }

    public void update() {
        super.update();
        this.getPath();
    }
}
