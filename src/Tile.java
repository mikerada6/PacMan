import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private boolean isWall;
    private boolean drawSquares = false;
    private boolean isPlayer;
    private Tile up;
    private Tile down;
    private Tile left;
    private Tile right;

    private int fScore;
    private int gScore;
    private Tile cameFrom;

    public Tile(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }

    public Tile() {
        x = 0;
        y = 0;
        isPlayer = false;
        left = null;
        right = null;
        up = null;
        down = null;
        this.isWall = false;
    }

    public static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public void setNeighbor(int direction, Tile neighbor) {
        if (direction == Player.UP)
            up = neighbor;
        else if (direction == Player.DOWN)
            down = neighbor;
        else if (direction == Player.LEFT)
            left = neighbor;
        else if (direction == Player.RIGHT)
            right = neighbor;
    }

    public Tile getNeighbor(int direction) {
        if (direction == Player.UP)
            return up;
        else if (direction == Player.DOWN)
            return down;
        else if (direction == Player.LEFT)
            return left;
        else if (direction == Player.RIGHT)
            return right;
        else return null;
    }

    public void draw(Graphics2D g) {
        if (isWall) {
            g.setColor(Color.BLUE);
            g.fillRect(x * GamePanel.TILESIZE, y * GamePanel.TILESIZE, GamePanel.TILESIZE, GamePanel.TILESIZE);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(x * GamePanel.TILESIZE, y * GamePanel.TILESIZE, GamePanel.TILESIZE, GamePanel.TILESIZE);
            if (drawSquares) {
                g.setColor(Color.WHITE);
                g.drawRect(x * GamePanel.TILESIZE, y * GamePanel.TILESIZE, GamePanel.TILESIZE, GamePanel.TILESIZE);
            }
        }

    }

    public void setIsWall(boolean _isWall) {
        this.isWall = _isWall;
    }

    public boolean isWall() {
        return isWall;
    }

    public int getX() {
        return x;
    }

    public int getCenterXPixel() {
        return (int) (x * GamePanel.TILESIZE + GamePanel.TILESIZE / 2.0);
    }

    public int getCenterYPixel() {
        return (int) (y * GamePanel.TILESIZE + GamePanel.TILESIZE / 2.0);
    }

    public int getY() {
        return y;
    }

    public void setScores(int goalX, int goalY) {
        this.gScore = Integer.MAX_VALUE;
        this.fScore = Math.abs(this.x - goalX) + Math.abs(this.y - goalY);
    }

    public int getGScore() {
        return gScore;
    }

    public void setGScore(int score) {
        this.gScore = score;
    }

    public int getFScore() {
        return fScore;
    }

    public void setFScore(int score) {
        this.fScore = score;
    }

    public ArrayList<Tile> getNeighbors()
    {
        ArrayList<Tile> ans = new ArrayList<Tile>();

        if(up!=null)
            ans.add(up);
        if(down!=null)
            ans.add(down);
        if(left!=null)
            ans.add(left);
        if(right!=null)
            ans.add(right);

        return ans;
    }

    public void setCameFrom(Tile tile)
    {
        this.cameFrom=tile;
    }

    public Tile getCameFrom()
    {
        return this.cameFrom;
    }

}
