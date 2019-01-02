import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;
    private Graphics2D g;
    private int x;
    private int y;
    private boolean isWall;
    private boolean drawSquares=true;

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
    }

    public BufferedImage getTileGraphics() {
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


        return image;
    }

    public void setIsWall(boolean _isWall) {
        this.isWall = _isWall;
    }
}
