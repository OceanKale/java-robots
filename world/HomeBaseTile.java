package robots.world;

import java.awt.*;
import java.awt.Color;

/**
   Class HomeBaseTile represents a home base tile on the 
   playing board.
*/
public class HomeBaseTile extends Tile {

  /**
     Constructs a home base tile with the specified column
     and row
  */
  public HomeBaseTile(int col, int row) {
    super(col, row);
  }

  /**
     Constructs a home base tile with the specified Location
  */
  public HomeBaseTile(Location location) {
    super(location);
  }

  /**
     Method getColor returns the Color used by paint 
     to display this tile
  */
  public Color getColor() {
    return Color.YELLOW;
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
    return "@";
  }
}