package robots.world;

/**
 
   A NoRobotHereException is thrown by Tile.getRobot() to indicate
   that the invoking tile is not occupied by a robot.
   
*/
public class NoRobotHereException extends RuntimeException {

  /**
     Constructs a NoRobotHereException
  */
  public NoRobotHereException() {
    super("No robot on this tile.");
  }

}
