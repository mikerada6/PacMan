import java.awt.*;
import java.awt.image.BufferedImage;

public class Ghost {
    public static final int CHASE = 1;
    public static final int SCATTER = 2;
    public static final int FRIGHTENED = 3;
    protected int homeX;
    protected int homeY;
    protected String name;
    BufferedImage[] sprites = new BufferedImage[7];
    protected int x;
    protected int y;
    protected int direction;
    protected boolean isBorn;
    private int mode;
    protected int tilesPerSecond = 11;

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        isBorn = false;
        this.homeX = 0;
        this.homeY = 0;
        name = "ghost";

    }

    public void draw(Graphics2D g) {
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

    public BufferedImage getSprite(int direction) {
        if (this.mode != FRIGHTENED) {
            if (direction == Player.LEFT) {
                return sprites[2];
            } else if (direction == Player.RIGHT) {
                return sprites[1];
            } else if (direction == Player.UP) {
                return sprites[4];
            } else if (direction == Player.DOWN) {
                return sprites[6];
            }
        } else if (mode == FRIGHTENED) {
            return GamePanel.spriteSheet.grabImage(5, 9, 16, 16);
        }
        return null;
    }

    public void setMode(int i) {
        mode = i;
    }


    public void update() {
        if (isBorn) {
            if (GamePanel.clock % (GamePanel.FPS / tilesPerSecond) == 0) {
                if (!GamePanel.board[x][y].getNeighbor(direction).isWall()) {
                    x = GamePanel.board[x][y].getNeighbor(direction).getX();
                    y = GamePanel.board[x][y].getNeighbor(direction).getY();
                }
                else {
                    int[] directions = {Player.UP, Player.DOWN, Player.RIGHT, Player.LEFT};
                    direction = directions[(int) (Math.random() * 1000000) % directions.length];
                }
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

    public void move(int x, int y)
    {
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }
    public void setX(int x)
    {
        this.x=x;
    }
    public void setY(int y)
    {
        this.y=y;
    }


    public int getY() {
        return y;
    }

    public static Tile[][] copy(Tile[][] input)
    {
        Tile[][] copy = new Tile[input.length][];
        for(int i = 0; i < input.length; i++)
        {
            copy[i] = new Tile[input[i].length];
            for (int j = 0; j < input[i].length; j++)
            {
                copy[i][j] = input[i][j];
            }
        }
        return copy;
    }

}
