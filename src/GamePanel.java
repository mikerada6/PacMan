import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;

public class GamePanel extends JPanel{
//FIELDS
    public static final int TILESIZE=10;
    public static final int  WIDTH = TILESIZE*28;
    public static final int  HEGIHT = TILESIZE*36;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEGIHT));
        setFocusable(true);
        requestFocus();
    }
}
