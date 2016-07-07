package robots.world;

import java.awt.*;
import java.awt.Color;

/**
   Class WaterTile represents a water tile on the 
   playing board.
*/
public class WaterTile extends Tile {

  /**
     Constructs a water tile at the specified column 
     and row.
  */
  public WaterTile(int col, int row) {
    super(col, row);
  }

  /**
     Constructs a water tile at the specified Location.
  */
  public WaterTile(Location location) {
    super(location);
  }

  /**
     Method getColor returns the Color used by paint 
     to display this tile.
  */
  public Color getColor() {
    return Color.BLUE;
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
    return false;
  }

  /**
     Method toString returns a single-character string
     of this Tile's character representation.
  */
  public String toString() {
    return "~";
  }
}