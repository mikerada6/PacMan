import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    //FIELDS
    public static final int TILESIZE = 25;
    public static final int WIDTH = TILESIZE * 28;
    public static final int HEIGHT = TILESIZE * 36;
    public boolean running;
    private Tile[][] board;
    private BufferedImage image;
    private Graphics2D g;

    private Thread thread;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        board= new Tile[WIDTH/TILESIZE][HEIGHT/TILESIZE];
        for(int x=0;x<board.length;x++)
        {
            for(int y=0;y<board[x].length;y++)
            {
                board[x][y]=new Tile(x,y);
            }
        }
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void run() {
        running = true;

        image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        while (running) {
            gameUpdate();
            gameRender();
            gameDraw();
        }
    }

    public void gameUpdate() {

    }

    public void gameRender() {
        g.setColor(Color.GREEN);
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.setColor(Color.BLACK);


        for(int x=0;x<board.length;x++)
        {
            for(int y=0;y<board[x].length;y++)
            {
                g.drawImage(board[x][y].getTileGraphics(),25*x,25*y,null);
            }
        }
    }

    public void gameDraw() {
    Graphics g2 = this.getGraphics();
    g2.drawImage(image,0,0,null);
    g2.dispose();
    }
}
