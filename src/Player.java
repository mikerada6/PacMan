public class Player {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    private int x;
    private int y;
    private Tile[][] board;
    private int direction;
    private int tryDirection;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Player(Tile[][] board) {
        this.board = board;
        //740
        x = 740 % GamePanel.TILEWIDTHCOUNT;
        y = 740 / GamePanel.TILEWIDTHCOUNT;
        board[x][y].playerMoveIn();
        direction = LEFT;
        tryDirection = -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDirection(int _direction) {
        if (_direction == UP) {
            this.direction = UP;
        } else if (_direction == DOWN) {
            this.direction = DOWN;
        } else if (_direction == LEFT) {
            this.direction = LEFT;
        } else if (_direction == RIGHT) {
            this.direction = RIGHT;
        }
    }

    public void setTryDirection(int _direction) {
        if (_direction == UP) {
            this.tryDirection = UP;
        } else if (_direction == DOWN) {
            this.tryDirection = DOWN;
        } else if (_direction == LEFT) {
            this.tryDirection = LEFT;
        } else if (_direction == RIGHT) {
            this.tryDirection = RIGHT;
        }
    }

    public void update() {
        if (tryDirection != -1 && !board[x][y].getNeighbor(tryDirection).isWall()) {
            board[x][y].getNeighbor(tryDirection).playerMoveIn();
            board[x][y].playerMoveOut();
            x=board[x][y].getNeighbor(tryDirection).getX();
            y=board[x][y].getNeighbor(tryDirection).getY();
            direction=tryDirection;
            tryDirection=-1;
        } else if (!board[x][y].getNeighbor(direction).isWall()) {
            board[x][y].getNeighbor(direction).playerMoveIn();
            board[x][y].playerMoveOut();
            x=board[x][y].getNeighbor(direction).getX();
            y=board[x][y].getNeighbor(direction).getY();
        }
    }
}
