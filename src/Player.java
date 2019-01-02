import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {
    public static final int UP = 3;
    public static final int DOWN = 4;
    public static final int LEFT = 2;
    public static final int RIGHT = 1;
    private int x;
    private int y;
    private int direction;
    private int tryDirection;
    private int tilesPerSecond = 11;
    private int lives;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Player() {
        x = 12;
        y = 26;
        direction = LEFT;
        tryDirection = -1;
        lives=3;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int _direction) {
        if (_direction == UP) {
            this.direction = UP;
        } else if (_direction == DOWN) {
            this.direction = DOWN;
        } else if (_direction == LEFT) {
            this.direction = LEFT;
        } else if (_direction == RIGHT) {
            this.direction = RIGHT;
        }
    }

    public void setTryDirection(int _direction) {
        if (_direction == UP) {
            this.tryDirection = UP;
        } else if (_direction == DOWN) {
            this.tryDirection = DOWN;
        } else if (_direction == LEFT) {
            this.tryDirection = LEFT;
        } else if (_direction == RIGHT) {
            this.tryDirection = RIGHT;
        }
    }

    public void die()
    {
        x = 12;
        y = 26;
        direction = LEFT;
        tryDirection = -1;
        lives--;

    }

    public void update() {
        if (GamePanel.clock % (GamePanel.FPS/tilesPerSecond) == 0 ) {
            if (tryDirection != -1 && !GamePanel.board[x][y].getNeighbor(tryDirection).isWall()) {
                x = GamePanel.board[x][y].getNeighbor(tryDirection).getX();
                y = GamePanel.board[x][y].getNeighbor(tryDirection).getY();
                direction = tryDirection;
                tryDirection = -1;
            } else if (!GamePanel.board[x][y].getNeighbor(direction).isWall()) {
                x = GamePanel.board[x][y].getNeighbor(direction).getX();
                y = GamePanel.board[x][y].getNeighbor(direction).getY();
            }
        }
    }

    public void draw(Graphics2D g) {

        BufferedImage temp = null;
        if (GamePanel.clock % 4 < 2) {
            temp = GamePanel.spriteSheet.grabImage(1, this.getDirection(), 16, 16);
        } else {
            temp = GamePanel.spriteSheet.grabImage(2, this.getDirection(), 16, 16);
        }
        temp = Tile.resize(temp, GamePanel.TILESIZE, GamePanel.TILESIZE);
        g.drawImage(temp, null, x * GamePanel.TILESIZE, y * GamePanel.TILESIZE);
    }
}
