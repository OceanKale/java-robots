package robots.play;

import robots.world.*;
import robots.world.Package;

import java.util.ArrayList;
import java.util.Iterator;

/**
    This class manages the potential targets for a PlottingBrain, 
   and maintains the currently locked target. 
*/

public class TargetChooser {

    private Game game;
    private Tile target;
    private PlayerRobot player;
    private PackageCollection playerPack;
    private Board board;
    private Iterator<Package> packIt;
    private ArrayList<Tile> minPath = new ArrayList<Tile>();
    private ArrayList<Tile> targets = new ArrayList<Tile>();

    /**
       Creates a chooser for use with the given game and identifies 
       the initial target.
    */ 
    public TargetChooser(Game game){
	this.game = game;
        player = game.getPlayer();
        playerPack = player.getPackageCollection();
        board = game.getBoard();
	//set list of targets to list of home base tiles on the board
	board = game.getBoard();
	for(int r = 1; r <= board.getNumRows(); r++) {
	    for(int c = 1; c <= board.getNumColumns(); c++) {
		if(board.get(c,r) instanceof HomeBaseTile) {
		    targets.add(board.get(c,r));
		}
	    }
	}
	target = identify();
    }

    /**
       Returns the current target.
    */
    public Tile getTarget(){
	return target;
    }

    /**
       Returns the list of HomeBase tiles
    */
    public ArrayList<Tile> getHomeBases() {
        return targets;
    }
 
    /**
       Returns a string representation of the HomeBase list
    */
    public String targetsToString() {
        String s = "[";
	Iterator<Tile> it = targets.iterator();
        while(it.hasNext()) {
            Tile t = it.next();
            s += t.getLocation().toString();
        }
        s += "]";
        return s;
    }
    
    /**
       Helper method to find the closest target from a 
       list of possible targets. 
    */
    public Tile findClosest(ArrayList<Tile> targets){
	Iterator<Tile> it = targets.iterator();
	int min = 10000000;
	Tile minimumTile = targets.get(0);
	while(it.hasNext()){
	    Tile t = it.next();
            try {
                ArrayList<Tile> path = new Course(player.getTile(), t, board).plot();
                if(path.size() < min){
                    minimumTile = t;
                    min = path.size();
                }
            } catch (NoSafePathException e) {
                // keep iterating
            }
	}
	return minimumTile;
    }


    /*
    public Tile findBest(ArrayList<Tile> targets){
	Iterator<Tile> it = targets.iterator();
	double bestRatio = 1000000.0;
	Tile bestTile = targets.get(0);
	while(it.hasNext()){
	    Tile t = it.next();
	    try {
                ArrayList<Tile> path = new Course(player.getTile(), t, board).plot();
		double curRatio = (t.size() + player.getAvailableCarryingCapacity() / 
				   (0.4 * path.size() + 0.25 * t.getSurroundingRobots() + 0.25 * t.getTotalWeight())); 
		if(curRatio < bestRatio){
		    bestTile = t;
		    bestRatio = curRatio;
		}
	    }
	    catch (NoSafePathException e){
	    }		
	}
	return bestTile;
    
    }
    */
    /**
       Returns the next unvisited HomeBaseTile.  If all HomeBases have been visited, returns the first visited HomeBase.
    */
    public Tile getNextBase(Tile curTarget) {
        Tile newTarget = curTarget;
        ArrayList<Tile> visitedBases = player.getVisitedBases();
        ArrayList<Tile> unvisited = new ArrayList<Tile>();
        Iterator<Tile> targIt = targets.iterator();
        while(targIt.hasNext()){
            Tile t = targIt.next();
            if (!visitedBases.contains(t)) {
                unvisited.add(t);
            }
        }
        if ( !unvisited.isEmpty() && unvisited.size() >= 2 ){
            Iterator<Tile> it = unvisited.iterator();
            while( it.hasNext() ) {
                Tile t2 = it.next();
                if (!curTarget.equals(t2)) {
                    newTarget = t2;;
                }
            }
        } else {
            newTarget = visitedBases.get(0);
        }
        return newTarget;
    }
    
    /**
       Identifies and returns the new target. If the player 
       robot is carrying one or more packages, then the new target
       is the closest destination of a package. 
       Otherwise, the new target is the closest as-yet-unvisited home base. 
       If all home bases have been visited, then we start over and 
       lock in on the nearest base.
    */
    public Tile identify() {

	if(targets.size() == 0) {
	    return new HomeBaseTile(-1, -1); // arbitrary for test cases ONLY
	}
	else if(!playerPack.isEmpty()){
	    packIt = playerPack.iterator();
	    int min = 10000000;
            ArrayList<Tile> path = new ArrayList<Tile>();
	    while(packIt.hasNext()){
		Package p = packIt.next();
                try {
                    path = new Course(player.getTile(), board.get(p.getDestination()), board).plot();
                    //System.out.printf("\nPackage %s destination %s distance %d\n", p.toString(), p.getDestination().toString(), path.size());
                    if(path.size() < min){
                        minPath = path;
                        min = path.size();
                        //System.out.printf("New min path length: %d\n", min);
                    }
                } catch ( NoSafePathException e ) {
                    path = new Course(player.getTile(), getHomeBases().get(0), board).plot();
                    minPath = path;
                    min = path.size();
                }
	    }
	    target = minPath.get(minPath.size() - 1);
	    return target;
	}
	else {
	    ArrayList<Tile> visitedBases = player.getVisitedBases();
	    if(visitedBases.containsAll(targets)){
		player.clearVisitedBases();
		target = findClosest(targets);
		return target;
	    }
	    else {
		ArrayList<Tile> unvisited = new ArrayList<Tile>();
		Iterator<Tile> targIt = targets.iterator();
		while(targIt.hasNext()){
		    Tile t = targIt.next();
		    if (!visitedBases.contains(t)) {
			unvisited.add(t);
		    }
		}
		target = findClosest(unvisited);
		return target;
	    }
	}
    }

}
