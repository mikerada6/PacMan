import java.util.ArrayList;

public class aStar {

    private Tile start;
    private Tile goal;
    private Tile[][] grid;

    public aStar(Tile start, Tile goal, Tile[][] grid) {
        this.start = start;
        this.goal = goal;
        this.grid = grid;
    }

    public ArrayList<Tile> reconstruct_path(ArrayList<Tile> cameFrom, Tile current) {

        ArrayList<Tile> path = new ArrayList<Tile>();

        path.add(current);

        while (current.getCameFrom() != null) {
            path.add(current.getCameFrom());
            current = current.getCameFrom();
        }

        return path;
    }


    public ArrayList<Tile> A_Star() {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y].setScores(goal.getX(), goal.getY());
            }
        }
        ArrayList<Tile> closedSet = new ArrayList<Tile>();
        ArrayList<Tile> openSet = new ArrayList<Tile>();
        openSet.add(start);
        ArrayList<Tile> cameFrom = new ArrayList<Tile>();
        Tile current = null;
        start.setFScore(Math.abs(goal.getX() - start.getX()) + Math.abs(start.getY() - goal.getY()));
        while (openSet.size() != 0) {
            //current := the node in openSet having the lowest fScore[] value
            int indexOfCurrent = 0;
            current = openSet.get(indexOfCurrent);
            /*
            go through evey item in the open set to see what is the lowest
            TODO optimize this.
            */
            for (int i = 0; i < openSet.size(); i++) {
                Tile temp = openSet.get(i);
                if (temp.getFScore() < current.getFScore()) {
                    current = temp;
                }
            }
            if (current.getX() == goal.getX() && current.getY() == goal.getY()) {
                return reconstruct_path(cameFrom, current);
            }
            this.remove(openSet, current);
            this.add(closedSet, current);
            for (Tile neighbor : current.getNeighbors()) {
                if (neighbor.isWall()) {
                    continue;
                }

                if (isIn(closedSet, neighbor)) {
                    continue;
                }
                int tentative_gScore = current.getGScore() + 1;
                if (!isIn(closedSet, neighbor)) {
                    add(openSet, neighbor);
                } else if (tentative_gScore >= neighbor.getGScore()) {
                    continue;
                }

                neighbor.setCameFrom(current);
                neighbor.setGScore(tentative_gScore);
                neighbor.setFScore(neighbor.getGScore() + Math.abs(neighbor.getX() - goal.getX()) + Math.abs(neighbor.getY() - goal.getY()));

            }

        }
        return reconstruct_path(cameFrom, current);
    }

    public ArrayList<Tile> remove(ArrayList<Tile> list, Tile item) {
        for (int i = 0; i < list.size(); i++) {
            Tile test = list.get(i);
            if (test.getX() == item.getX() && test.getY() == item.getY()) {
                list.remove(i);
                return list;
            }
        }
        return list;
    }

    public ArrayList<Tile> add(ArrayList<Tile> list, Tile item) {
        list.add(item);
        return list;
    }

    public boolean isIn(ArrayList<Tile> list, Tile item) {
        for (int i = 0; i < list.size(); i++) {
            Tile test = list.get(i);
            if (test.getX() == item.getX() && test.getY() == item.getY()) {
                return true;
            }
        }
        return false;
    }
}
