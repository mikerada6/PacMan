import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;
    private Graphics2D g;
    private int x;
    private int y;

    public Tile(int x, int y)
    {
        this();
        this.x=x;
        this.y=y;
    }
    public Tile() {
        x=0;
        y=0;
        image = new BufferedImage(GamePanel.TILESIZE,GamePanel.TILESIZE, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
    }

    public BufferedImage getTileGraphics() {
        g.setColor(Color.GREEN);
        g.fillRect(0,0,GamePanel.TILESIZE,GamePanel.TILESIZE);
        g.setColor(Color.BLACK);
        g.drawString(""+y,GamePanel.TILESIZE/2,GamePanel.TILESIZE/2);
        return image;
    }
}
