package robots.world;

import java.awt.*;
import java.awt.Color;

/**
   Class WallTile represents a wall tile on the 
   playing board.
*/
public class WallTile extends Tile {

  /**
     Constructs a wall tile at the specified column
     and row.
  */
  public WallTile(int col, int row) {
    super(col, row);
  }

  /**
     Constructs a wall tile at the specified Location.
  */
  public WallTile(Location location) {
    super(location);
  }

  /**
     Method getColor returns the Color used by paint 
     to display this tile.
  */
  public Color getColor() {
    return Color.GRAY;
  }

  /**
     Method isAccessible returns whether or not
     a robot can move to this tile.
  */
  public boolean isAccessible() {
    return false;
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
    return "#";
  }
}