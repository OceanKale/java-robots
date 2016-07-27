package robots.world;

import java.awt.*;
import java.awt.Color;


/**



   Class NormalTile represents a normal tile on the 
   playing board.
*/
public class NormalTile extends Tile {
    
  /**
     Constructs a normal tile at the specified column
     and row.
  */
  public NormalTile(int col, int row) {
    super(col, row);
  }
  
  /**
     Constructs a normal tile at the specified location.
  */
  public NormalTile(Location location) {
    super(location);
  }
  
  /**
     Method getColor returns the Color used by paint 
     to display this tile.
  */
  public Color getColor() {
    return Color.WHITE;
  }

  /**
     Method isAccessible returns whether or not
     a robot can move to this tile.
  */
  public boolean isAccessible() {
    return true;
  }
  
  /**
     Method isSafe returns whether or not a robot
     can be safely placed on this tile.
  */
  public boolean isSafe() {
    return true;
  }
  
  /**
     Method toString returns a single-character string
     of this Tile's character representation.
  */
  public String toString() {
    return ".";
  }
}
