import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    //FIELDS
    public static final int TILESIZE = 28;
    public static final int TILEWIDTHCOUNT = 28;
    public static final int TILEHEIGHTCOUNT = 36;
    public static final int WIDTH = TILESIZE * TILEWIDTHCOUNT;
    public static final int HEIGHT = TILESIZE * TILEHEIGHTCOUNT;
    public static final int FPS = 45;
    public static Tile[][] board;
    public static Player p;
    public static long clock = 0;
    public static Ghost[] ghosts = new Ghost[4];
    public static SpriteSheet spriteSheet = null;
    public boolean running;
    private BufferedImage image;
    private Graphics2D g;
    private double averageFPS;
    private Thread thread;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        board = new Tile[WIDTH / TILESIZE][HEIGHT / TILESIZE];
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                board[x][y] = new Tile(x, y);
            }
        }
        int[] levelOne = {
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111,
                112, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 125, 126, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 139,
                140, -1, 142, 143, 144, 145, -1, 147, 148, 149, 150, 151, -1, 153, 154, -1, 156, 157, 158, 159, 160, -1, 162, 163, 164, 165, -1, 167,
                168, -1, 170, 171, 172, 173, -1, 175, 176, 177, 178, 179, -1, 181, 182, -1, 184, 185, 186, 187, 188, -1, 190, 191, 192, 193, -1, 195,
                196, -1, 198, 199, 200, 201, -1, 203, 204, 205, 206, 207, -1, 209, 210, -1, 212, 213, 214, 215, 216, -1, 218, 219, 220, 221, -1, 223,
                224, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 251,
                252, -1, 254, 255, 256, 257, -1, 259, 260, -1, 262, 263, 264, 265, 266, 267, 268, 269, -1, 271, 272, -1, 274, 275, 276, 277, -1, 279,
                280, -1, 282, 283, 284, 285, -1, 287, 288, -1, 290, 291, 292, 293, 294, 295, 296, 297, -1, 299, 300, -1, 302, 303, 304, 305, -1, 307,
                308, -1, -1, -1, -1, -1, -1, 315, 316, -1, -1, -1, -1, 321, 322, -1, -1, -1, -1, 327, 328, -1, -1, -1, -1, -1, -1, 335,
                336, 337, 338, 339, 340, 341, -1, 343, 344, 345, 346, 347, -1, 349, 350, -1, 352, 353, 354, 355, 356, -1, 358, 359, 360, 361, 362, 363,
                -1, -1, -1, -1, -1, 369, -1, 371, 372, 373, 374, 375, -1, 377, 378, -1, 380, 381, 382, 383, 384, -1, 386, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, 397, -1, 399, 400, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 411, 412, -1, 414, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, 425, -1, 427, 428, -1, 430, 431, 432, 433, 434, 435, 436, 437, -1, 439, 440, -1, 442, -1, -1, -1, -1, -1,
                448, 449, 450, 451, 452, 453, -1, 455, 456, -1, 458, -1, -1, -1, -1, -1, -1, 465, -1, 467, 468, -1, 470, 471, 472, 473, 474, 475,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 486, -1, -1, -1, -1, -1, -1, 493, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                504, 505, 506, 507, 508, 509, -1, 511, 512, -1, 514, -1, -1, -1, -1, -1, -1, 521, -1, 523, 524, -1, 526, 527, 528, 529, 530, 531,
                -1, -1, -1, -1, -1, 537, -1, 539, 540, -1, 542, 543, 544, 545, 546, 547, 548, 549, -1, 551, 552, -1, 554, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, 565, -1, 567, 568, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 579, 580, -1, 582, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, 593, -1, 595, 596, -1, 598, 599, 600, 601, 602, 603, 604, 605, -1, 607, 608, -1, 610, -1, -1, -1, -1, -1,
                616, 617, 618, 619, 620, 621, -1, 623, 624, -1, 626, 627, 628, 629, 630, 631, 632, 633, -1, 635, 636, -1, 638, 639, 640, 641, 642, 643,
                644, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 657, 658, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 671,
                672, -1, 674, 675, 676, 677, -1, 679, 680, 681, 682, 683, -1, 685, 686, -1, 688, 689, 690, 691, 692, -1, 694, 695, 696, 697, -1, 699,
                700, -1, 702, 703, 704, 705, -1, 707, 708, 709, 710, 711, -1, 713, 714, -1, 716, 717, 718, 719, 720, -1, 722, 723, 724, 725, -1, 727,
                728, -1, -1, -1, 732, 733, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 750, 751, -1, -1, -1, 755,
                756, 757, 758, -1, 760, 761, -1, 763, 764, -1, 766, 767, 768, 769, 770, 771, 772, 773, -1, 775, 776, -1, 778, 779, -1, 781, 782, 783,
                784, 785, 786, -1, 788, 789, -1, 791, 792, -1, 794, 795, 796, 797, 798, 799, 800, 801, -1, 803, 804, -1, 806, 807, -1, 809, 810, 811,
                812, -1, -1, -1, -1, -1, -1, 819, 820, -1, -1, -1, 824, 825, 826, 827, -1, -1, -1, 831, 832, -1, -1, -1, -1, -1, -1, 839,
                840, -1, 842, 843, 844, 845, 846, 847, 848, 849, 850, -1, 852, 853, 854, 855, -1, 857, 858, 859, 860, 861, 862, 863, 864, 865, -1, 867,
                868, -1, 870, 871, 872, 873, 874, 875, 876, 877, 878, -1, 880, 881, 882, 883, -1, 885, 886, 887, 888, 889, 890, 891, 892, 893, -1, 895,
                896, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 923,
                924, 925, 926, 927, 928, 929, 930, 931, 932, 933, 934, 935, 936, 937, 938, 939, 940, 941, 942, 943, 944, 945, 946, 947, 948, 949, 950, 951,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
        };

        for (int i = 0; i < levelOne.length; i++) {
            int num = levelOne[i];
            if (num != -1) {
                board[num % TILEWIDTHCOUNT][num / TILEWIDTHCOUNT].setIsWall(true);
            }
        }
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                try {
                    board[x][y].setNeighbor(Player.UP, board[x][y - 1]);
                } catch (Exception e) {
                }
                try {
                    board[x][y].setNeighbor(Player.DOWN, board[x][y + 1]);
                } catch (Exception e) {
                }
                try {
                    board[x][y].setNeighbor(Player.LEFT, board[x - 1][y]);
                } catch (Exception e) {
                }
                try {
                    board[x][y].setNeighbor(Player.RIGHT, board[x + 1][y]);
                } catch (Exception e) {
                }
            }
        }
        board[0][17].setNeighbor(Player.LEFT, board[board.length - 1][17]);
        board[board.length - 1][17].setNeighbor(Player.RIGHT, board[0][17]);


        p = new Player();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("Pac-Man_-_ARC_-_Sprite_Sheet.png"));
            spriteSheet = new SpriteSheet(img);
        } catch (IOException e) {
        }
        ghosts[0] = new Blinky(12, 17);
        ghosts[1] = new Pinky(13, 17);
        ghosts[2] = new Inky(14, 17);
        ghosts[3] = new Clyde(15, 17);
        ghosts[0].born();
        //ghosts[1].born();
        //ghosts[2].born();
        //ghosts[3].born();
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    public void run() {
        running = true;

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        long startTime;
        long URDTimeMillis;
        long waitTime;
        long totalTime = 0;

        int frameCount = 0;
        int maxFrameCount = 30;

        long targetTime = 1000 / FPS;

        while (running) {
            startTime = System.nanoTime();
            gameUpdate();
            gameRender();
            gameDraw();

            URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - URDTimeMillis;

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {

            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == maxFrameCount) {
                averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000.0);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }

    public void gameUpdate() {
        //check to see if you are dead
        for(int i=0;i<ghosts.length;i++)
        {
            if(p.getX()==ghosts[i].getX() && p.getY()==ghosts[i].getY())
            {
                p.die();
            }
        }
        p.update();
        for(int i=0;i<ghosts.length;i++)
        {
           // ghosts[i].update();
        }
        clock++;

    }

    public void gameRender() {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                board[x][y].draw(g);
            }
        }
        for (int i = 0; i < ghosts.length; i++) {
            ghosts[i].draw(g);
        }
        p.draw(g);
        g.setColor(Color.WHITE);
        g.drawString("FPS: " + averageFPS, 10, 10);
    }

    public void gameDraw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {
    }

    public void keyPressed(KeyEvent key) {
        int keyCode = key.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            p.setTryDirection(Player.LEFT);
        } else if (keyCode == KeyEvent.VK_UP) {
            p.setTryDirection(Player.UP);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            p.setTryDirection(Player.DOWN);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            p.setTryDirection(Player.RIGHT);
        }
    }

    public void keyReleased(KeyEvent key) {
        p.setTryDirection(-1);
    }

}
