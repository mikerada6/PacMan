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
    public static final int FPS = 250;
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
    private Pellet[] pellets;
    private PowerPellet[] powerPellets;
    public static int score;

    public GamePanel() {
        super();
        score=0;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        board = new Tile[WIDTH / TILESIZE][HEIGHT / TILESIZE];
        pellets = new Pellet[240];
        powerPellets = new PowerPellet[4];
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

        int[] levelOnePellets={
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,113,114,115,116,117,118,119,120,121,122,123,124,-1,-1,127,128,129,130,131,132,133,134,135,136,137,138,-1,
                -1,141,-1,-1,-1,-1,146,-1,-1,-1,-1,-1,152,-1,-1,155,-1,-1,-1,-1,-1,161,-1,-1,-1,-1,166,-1,
                -1,-1,-1,-1,-1,-1,174,-1,-1,-1,-1,-1,180,-1,-1,183,-1,-1,-1,-1,-1,189,-1,-1,-1,-1,-1,-1,
                -1,197,-1,-1,-1,-1,202,-1,-1,-1,-1,-1,208,-1,-1,211,-1,-1,-1,-1,-1,217,-1,-1,-1,-1,222,-1,
                -1,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,-1,
                -1,253,-1,-1,-1,-1,258,-1,-1,261,-1,-1,-1,-1,-1,-1,-1,-1,270,-1,-1,273,-1,-1,-1,-1,278,-1,
                -1,281,-1,-1,-1,-1,286,-1,-1,289,-1,-1,-1,-1,-1,-1,-1,-1,298,-1,-1,301,-1,-1,-1,-1,306,-1,
                -1,309,310,311,312,313,314,-1,-1,317,318,319,320,-1,-1,323,324,325,326,-1,-1,329,330,331,332,333,334,-1,
                -1,-1,-1,-1,-1,-1,342,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,357,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,370,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,385,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,398,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,413,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,426,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,441,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,454,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,469,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,482,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,497,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,510,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,525,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,538,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,553,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,566,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,581,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,594,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,609,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,622,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,637,-1,-1,-1,-1,-1,-1,
                -1,645,646,647,648,649,650,651,652,653,654,655,656,-1,-1,659,660,661,662,663,664,665,666,667,668,669,670,-1,
                -1,673,-1,-1,-1,-1,678,-1,-1,-1,-1,-1,684,-1,-1,687,-1,-1,-1,-1,-1,693,-1,-1,-1,-1,698,-1,
                -1,701,-1,-1,-1,-1,706,-1,-1,-1,-1,-1,712,-1,-1,715,-1,-1,-1,-1,-1,721,-1,-1,-1,-1,726,-1,
                -1,-1,730,731,-1,-1,734,735,736,737,738,739,740,741,742,743,744,745,746,747,748,749,-1,-1,752,753,-1,-1,
                -1,-1,-1,759,-1,-1,762,-1,-1,765,-1,-1,-1,-1,-1,-1,-1,-1,774,-1,-1,777,-1,-1,780,-1,-1,-1,
                -1,-1,-1,787,-1,-1,790,-1,-1,793,-1,-1,-1,-1,-1,-1,-1,-1,802,-1,-1,805,-1,-1,808,-1,-1,-1,
                -1,813,814,815,816,817,818,-1,-1,821,822,823,-1,-1,-1,-1,828,829,830,-1,-1,833,834,835,836,837,838,-1,
                -1,841,-1,-1,-1,-1,-1,-1,-1,-1,-1,851,-1,-1,-1,-1,856,-1,-1,-1,-1,-1,-1,-1,-1,-1,866,-1,
                -1,869,-1,-1,-1,-1,-1,-1,-1,-1,-1,879,-1,-1,-1,-1,884,-1,-1,-1,-1,-1,-1,-1,-1,-1,894,-1,
                -1,897,898,899,900,901,902,903,904,905,906,907,908,909,910,911,912,913,914,915,916,917,918,919,920,921,922,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        };

        int[] levelOnePowerPellets={169,194,729,754};

        for (int i = 0; i < levelOne.length; i++) {
            int num = levelOne[i];
            if (num != -1) {
                board[num % TILEWIDTHCOUNT][num / TILEWIDTHCOUNT].setIsWall(true);
            }
        }
        int pelletCount=0;
        for (int i = 0; i < levelOnePellets.length; i++) {
            int num = levelOnePellets[i];
            if (num != -1) {
                pellets[pelletCount]=new Pellet(num % TILEWIDTHCOUNT,num / TILEWIDTHCOUNT);
                pelletCount++;
                System.out.println(pelletCount);
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

        pelletCount=0;
        for (int i = 0; i < levelOnePowerPellets.length; i++) {
            int num = levelOnePowerPellets[i];
            if (num != -1) {
                powerPellets[pelletCount]=new PowerPellet(num % TILEWIDTHCOUNT,num / TILEWIDTHCOUNT,ghosts);
                pelletCount++;
            }
        }
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
        int maxFrameCount = FPS;

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
            ghosts[i].update();
        }
        clock++;
        for(Pellet pellet : pellets)
        {
            if(pellet.isAlive() && p.getX()==pellet.getX() && p.getY()==pellet.getY())
            {
                score+=pellet.eat();
            }
        }
        for(PowerPellet pellet : powerPellets)
        {
            if(pellet.isAlive() && p.getX()==pellet.getX() && p.getY()==pellet.getY())
            {
                score+=pellet.eat();
            }
        }
        int alivePellets=0;
        for(Pellet pellet : pellets)
        {
            if(pellet.isAlive())
                alivePellets++;
        }
        if(alivePellets==0)
        {
            running=false;
        }

    }

    public void gameRender() {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                board[x][y].draw(g);
            }
        }
        for(Pellet pellet : pellets)
        {
            pellet.draw(g);
        }

        for(PowerPellet pellet : powerPellets)
        {
            pellet.draw(g);
        }
        for (int i = 0; i < ghosts.length; i++) {
            ghosts[i].draw(g);
        }

        p.draw(g);
        g.setColor(Color.WHITE);
        g.drawString("FPS: " + averageFPS, 10, 10);
        g.drawString("Score: " + score, 10, 20);
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
