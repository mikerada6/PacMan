import java.awt.image.BufferedImage;

public class Inky extends Ghost {

    public Inky(int x, int y)
    {
        super(x,y);
        super.name="INKY";
        for (int i=0;i<sprites.length;i++)
        {
            sprites[i]=GamePanel.spriteSheet.grabImage(i+1,7, 16, 16);
        }
    }
}
