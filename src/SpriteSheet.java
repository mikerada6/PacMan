import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage image;

    public SpriteSheet(BufferedImage ss) {
        this.image = ss;
    }

    public BufferedImage grabImage(int col, int row, int width, int height) {
        int pixelSize = 16;
        BufferedImage img = image.getSubimage((col * pixelSize) - pixelSize+1, (row * pixelSize) - pixelSize, width, height);
        return img;
    }
}

