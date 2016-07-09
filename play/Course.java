package robots.play;

import robots.world.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

/**
      This class represents a shortest path course in a board between two tiles.
*/
public class Course {

    private Tile here;
    private Tile there;
    private Board board;
    private ArrayList<Tile> course;

    /**
       Create a course from here to there on the board. 
    */
    public Course(Tile here, Tile there, Board board) {
        this.here = here;
        this.there = there;
        this.board = board;
    }

    /**
       Returns the ending point of the course. 
    */
    public Tile getEnd() {
        return course.get( course.size() - 1 );
    }

    /**
       Returns the starting point of the course. 
    */
    public Tile getStart() {
        return course.get(0);
    }

    /**
       Computes the shortest path connecting the two endpoints and returns it as a list 
       of board tiles. The lead element in the path is here and the last element is there. 
       Utilizes a breadth-first search algorithm that grows a blob, uniformly radiating 
       out from the starting point, until the ending point is subsumed.<br><br>

       Throws:<br>
       NoSafePathException - if there is no safe path connecting endpoints
    */
    public ArrayList<Tile> plot() throws NoSafePathException {
        LinkedList<Tile> frontier = new LinkedList<Tile>();
        ArrayList<Tile> explored = new ArrayList<Tile>();
        frontier.add(here);
        explored.add(here);
        //System.out.printf("\nCalculating shortest path from tile %s to tile %s\n", here.getLocation().toString(), there.getLocation().toString() );
        course = new ArrayList<Tile>( bfs(frontier, explored) );
        return course;
    }

    /**
       Breadth-first search algorithm to return the path given the current frontier and explored Tiles  
    */
    public LinkedList<Tile> bfs(LinkedList<Tile> frontier, ArrayList<Tile> explored) throws NoSafePathException {
        //System.out.printf("BFS: frontier = %s\n", listToString(frontier));
        LinkedList<Tile> path = new LinkedList<Tile> ();
        if (frontier.size() == 0) throw new NoSafePathException();
        Tile curTile = frontier.remove();
        //System.out.printf("Considering tile %s\n", curTile.getLocation().toString());
        explored.add(curTile);

        if ( curTile.equals(there) ) {
            path.add(curTile);
            //System.out.printf("Reached destination; returning %s\n", listToString(path));
        }
        else {
            //System.out.printf("Not at destination, recurring with neighbors...\n");
            for (Direction dir : Direction.values()) {
                if ( board.inBounds(curTile, dir) ) {
                    Tile adjTile = board.adjacentTile(curTile, dir);
                    if (adjTile.isAccessible() && adjTile.isSafe()
                        && !explored.contains(adjTile) && !frontier.contains(adjTile) ) {
                        frontier.add(adjTile);
                    }
                }
            }
            path = bfs(frontier, explored);
            //System.out.printf("Best path from frontier = %s\n", listToString(path));
            int curRow = curTile.getLocation().getRow();
            int curCol = curTile.getLocation().getColumn();
	    Tile otherTile = path.get(0);
            int otherRow = otherTile.getLocation().getRow();
            int otherCol = otherTile.getLocation().getColumn();
            
            if ( (curCol == otherCol && Math.abs(curRow - otherRow) == 1) || 
                 (curRow == otherRow && Math.abs(curCol - otherCol) == 1) ) {
                path.add(0, curTile);
                //System.out.printf("Current tile %s IS neighbor; returning %s\n", curTile.getLocation().toString(), listToString(path));
            }
            //System.out.printf("Current tile %s NOT neighbor; returning %s\n", curTile.getLocation().toString(), listToString(path));
        }
        return path;
    }
    
    /**
       Returns a string listing the locations of all Tiles in the given LinkedList of Tiles
    */
    public String listToString(LinkedList<Tile> list) {
        String str = "[";
        Iterator<Tile> it = list.iterator();
        while ( it.hasNext() ) {
            Tile tile = it.next();
            str += tile.getLocation().toString();
        }
        str += ']';
        return str;
   }
}