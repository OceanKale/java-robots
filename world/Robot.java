package robots.world;


import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.awt.geom.Rectangle2D;
import java.util.logging.*;
import robots.play.*;
import robots.play.WanderBrain.*;

/** 
   Class Robot represents a robot in the game
*/
public class Robot {

    private int id;
    private int money;
    private int carryingCapacity;
    private int score = 0;
    private boolean alive; 
    private Tile t;
    private Location l;
    private boolean isOnBoard = false;
    private PackageCollection packages;
    Logger logger = Logger.getLogger("robots");
    //private ArrayList<Tile> visited;
    
    /**
       Constructs a Robot, as per parameters
    */
    public Robot(int id, int carryingCapacity, int money) {
	this.id = id;
	this.carryingCapacity = carryingCapacity;
	this.money = money;
        this.alive = true;
        this.packages = new PackageCollection();
	logger.info(String.format("Robot %d was born!", getId()));
    }

    /**
       Returns the string describing the given robot, with id, capacity, money and score
    */
    public String toString() {
	String x = "Robot " +id+": capacity="+getAvailableCarryingCapacity()+", money="+money+", score="+score;
	return x;
    }

    /**
       Returns the identifier of given robot
    */
    public int getId() {
	return this.id;
    }

    /**
       Returns the currently acquired amount of funds of given robot
    */
    public int getMoney() {
	return this.money;
    }

    /**
       Returns the maximum carrying capacity of given robot
    */
    public int getMaximumCarryingCapacity() {
	return this.carryingCapacity;
    }

    /**
       Returns the available carrying capacity of given robot, determined by subtracting the weight
       of the currently held package from the maximum carrying capacity
    */
    public int getAvailableCarryingCapacity() {
      return (this.carryingCapacity - this.packages.getTotalWeight());
    }

    /**
       Returns the robot's current score
    */
    public int getScore() {
	return this.score;
    }

   /**
      This robot dies
   */ 
   public void die() {
	this.alive = false;
	this.isOnBoard = false;
	logger.info(String.format("Robot %d died.", getId()));
    }

    /**
       Returns whether or not the robot is dead
    */
    public boolean isDead() {
	return !this.alive;
	
    }

    /**
       Adjusts the amount of money that the given robot has after charging
       the given bid
    */
    public void pay(int bid) {
	if(Math.abs(bid) > this.money) {
	    this.die();
	}
	else {
	    this.money = (this.money - Math.abs(bid));
	}
    }

    /**
       Returns the color used by paint to display this robot
    */
    public Color getColor(){
	return Color.CYAN;
    }

    /**
       Returns true if the robot is on a board tile, and false if otherwise
    */
    public boolean onBoard(){
	return isOnBoard;
    }

    /**
      Returns the tile on which this robot is positioned. If this robot
      is not on the board, then null is returned
    */
    public Tile getTile() {
	if(this.onBoard()) {
	    return t;
	}
	else {
	    return null;
	}
    }
    
     /**
        Moves the robot to the given tile. If the robot was located at another tile, 
        it is removed from that tile.  Returns true if the robot move was successful, 
        and false otherwise. Assumes the robot is live. 
     */
    public boolean move(Tile tile) {
	if (! this.onBoard()) {
	    tile.setRobot(this);
	    this.t = tile;
	    isOnBoard = true;
	    return true;

	} else if ( this.onBoard() ) {
            this.t.removeRobot(this);
	    tile.setRobot(this);

	    if (this.isDead()) {
		return false;

	    } else {
		this.t = tile;
		l = t.getLocation();
		return true;
	    }
	    
	} else {
	    return false;
	}
    }
    
    /**
       Paints the robot on the tile
    */
    public void paint(Graphics2D g2, Rectangle2D.Double box) {
	if(!this.isDead()) {
            double wSize = box.width;
            double hSize = box.height;
	    double w =  box.width / 2.0;
	    double h =  box.height / 2.0;
	    double x =  box.x;
	    double y =  box.y;
            x += (wSize / 4.0);
            y += (hSize / 4.0);
	    
            Rectangle2D.Double innerBox = new Rectangle2D.Double(x, y, w, h);
	    g2.setColor(this.getColor());
	    g2.fill(innerBox);
            g2.setColor(Color.BLACK);
            g2.draw(innerBox);
        }
    }
    
    /**
       Returns the collectin of packages being carried by this robot.
    */
    public PackageCollection getPackageCollection() {
	return this.packages;
    }

    /**
       If the robot has the capacity to carry the given package, then it is added to the collection 
       of packages that the robot is carrying. Otherwise, the package is ignored. Returns true if 
       the package was successfully picked up, and false otherwise. Assumes the robot is live 
       and on the board. 
    */
    public boolean pick(Package pkg) {
	if (pkg != null && this.getAvailableCarryingCapacity() >= pkg.getWeight()) {
	    this.packages.add(pkg);
	    t.getPackageCollection().remove(pkg.getId());
	    logger.info(String.format("Robot %d picked up package %d.", getId(), pkg.getId()));
	    //this.carryingCapacity = getAvailableCarryingCapacity() - pkg.getWeight();
	    return true;
	} else {
	    return false;
	}
    }
    
    /**
       If the robot is carrying a package with the given id, it is dropped at the current location, 
       and the robot's score is updated accordingly. If the robot is not carrying the indicated package, 
       nothing happens. Returns true if the package was delivered, and false otherwise. Assumes the 
       robot is live and on the board.<br>
       
       Note: no error occurs if the robot is not carrying a package with the given id.<br>
       
       Note: when a package is dropped at its destination, it disappears from the game, and the 
       robot's score is updated.<br>
       
       Note: this is the only mechanism by which the robot's score can change. 
    */
    public boolean drop(int pid) {
    Package pack = this.packages.get(pid);
    if (pack == null) {  // package not in robot's collection
      return false;
    
    } else {
      if ( pack.getDestination().equals( this.t.getLocation() ) )  {  // robot at the package's destination
        this.packages.remove(pid);   
        this.score += pack.getWeight();
        logger.info(String.format("Robot %d dropped a package. Package dropped: %d ", getId(), pid));
        logger.info(String.format("Robot %d scored. Current score is %d", getId(), this.score));
        return true;
      
      } else {  // robot NOT at package destination
        this.packages.remove(pid);   
        t.getPackageCollection().add(pack);   
        logger.info(String.format("Robot %d dropped a package. Package dropped: %d ", getId(), pid));
        return false;
      }
    }
  }
     
      
   
}

