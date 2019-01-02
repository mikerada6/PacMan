import java.awt.image.BufferedImage;

public class Blinky extends Ghost {

    public Blinky(int x, int y)
    {
        super(x,y);
        for (int i=0;i<sprites.length;i++)
        {
            sprites[i]=GamePanel.spriteSheet.grabImage(i+1,5, 16, 16);
        }
    }
}
