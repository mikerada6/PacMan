import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;
    private Graphics2D g;
    private int x;
    private int y;
    private boolean isWall;
    private boolean drawSquares=false;
    private boolean isPlayer;
    private Tile up;
    private Tile down;
    private Tile left;
    private Tile right;

    public Tile(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }

    public Tile() {
        x = 0;
        y = 0;
        image = new BufferedImage(GamePanel.TILESIZE, GamePanel.TILESIZE, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        isPlayer=false;
        left=null;
        right=null;
        up=null;
        down=null;
        this.isWall=false;
    }

    public void setNeighbor(int direction, Tile neighbor)
    {
        if (direction==Player.UP)
            up=neighbor;
        else if(direction==Player.DOWN)
            down=neighbor;
        else if(direction==Player.LEFT)
            left=neighbor;
        else if(direction==Player.RIGHT)
            right=neighbor;
    }
    public Tile getNeighbor(int direction)
    {
        if (direction==Player.UP)
            return up;
        else if(direction==Player.DOWN)
            return down;
        else if(direction==Player.LEFT)
            return left;
        else if(direction==Player.RIGHT)
            return right;
        else return null;
    }
    public BufferedImage draw() {
        if (isWall) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, GamePanel.TILESIZE, GamePanel.TILESIZE);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, GamePanel.TILESIZE, GamePanel.TILESIZE);
            if(drawSquares) {
                g.setColor(Color.BLACK);
                g.drawRect(0, 0, GamePanel.TILESIZE, GamePanel.TILESIZE);
            }
        }
        if(isPlayer)
        {
            g.setColor(Color.RED);
            g.fillOval(0,0,GamePanel.TILESIZE, GamePanel.TILESIZE);
        }

        g.setColor(Color.BLACK);
        g.drawString(""+y,0,0);
        return image;
    }

    public void setIsWall(boolean _isWall) {
        this.isWall = _isWall;
    }

    public boolean isWall()
    {
        return isWall;
    }

    public void playerMoveIn()
    {
        isPlayer=true;
    }
    public void playerMoveOut()
    {
        isPlayer=false;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
