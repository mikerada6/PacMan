import java.awt.image.BufferedImage;

public class Clyde extends Ghost {


    public Clyde(int x, int y)
    {
        super(x,y);
        for (int i=0;i<sprites.length;i++)
        {
            sprites[i]=GamePanel.spriteSheet.grabImage(i+1,8, 16, 16);
        }
    }
}
