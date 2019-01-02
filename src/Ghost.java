import java.awt.*;
import java.awt.image.BufferedImage;

public class Ghost {
    private final int CHASE = 1;
    private final int SCATTER = 2;
    private final int FRIGHTENED = 3;
    BufferedImage[] sprites = new BufferedImage[7];
    private int x;
    private int y;
    private int direction;
    private boolean isBorn;
    private int mode;

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        isBorn = false;
    }

    public void draw(Graphics2D g) {
    if(mode!=FRIGHTENED) {
        if (!isBorn) {
            BufferedImage temp = sprites[(int) GamePanel.clock % sprites.length];
            temp = Tile.resize(temp, GamePanel.TILESIZE, GamePanel.TILESIZE);
            g.drawImage(temp, null, x * GamePanel.TILESIZE, y * GamePanel.TILESIZE);
        } else {
            BufferedImage temp = getSprite(direction);
            temp = Tile.resize(temp, GamePanel.TILESIZE, GamePanel.TILESIZE);
            g.drawImage(temp, null, x * GamePanel.TILESIZE, y * GamePanel.TILESIZE);
        }
    }
    else
    {

    }
    }

    public BufferedImage getSprite(int direction) {
        if (direction == Player.LEFT) {
            return sprites[2];
        } else if (direction == Player.RIGHT) {
            return sprites[1];
        } else if (direction == Player.UP) {
            return sprites[4];
        } else if (direction == Player.DOWN) {
            return sprites[6];
        }
        return null;
    }

    public void setMode(int i) {
        mode = i;
    }


    public void update() {
        if (isBorn) {
            if (!GamePanel.board[x][y].getNeighbor(direction).isWall()) {
                x = GamePanel.board[x][y].getNeighbor(direction).getX();
                y = GamePanel.board[x][y].getNeighbor(direction).getY();
            }
            if (GamePanel.board[x][y].getNeighbor(direction).isWall()) {

                int[] directions = {Player.UP, Player.DOWN, Player.RIGHT, Player.LEFT};
                direction = directions[(int) (Math.random() * 1000000) % directions.length];
            }
        }
    }

    public void born() {
        setMode(CHASE);
        x = 13;
        y = 14;
        direction = Player.LEFT;
        isBorn = true;
    }

}
