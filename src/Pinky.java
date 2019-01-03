import java.awt.image.BufferedImage;

public class Pinky  extends Ghost {

    public Pinky(int x, int y)
    {
        super(x,y);
        super.name="Pinky";
        for (int i=0;i<sprites.length;i++)
        {
            sprites[i]=GamePanel.spriteSheet.grabImage(i+1,6, 16, 16);
        }
    }
}