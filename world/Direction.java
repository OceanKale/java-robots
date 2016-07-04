package robots.world;

import java.util.Random;

/**
   Enum Direction represents the four possible directions that a 
   robot can travel in one turn.
*/
public enum Direction {
  
  NORTH, SOUTH, EAST, WEST;

  private static final int NUM_DIRECTIONS = 4;
  
  /**
     Method pickOneAtRandom returns a randomly selected direction.
  */
  public static Direction pickOneAtRandom() {
    Random generator = new Random();
    int random = generator.nextInt(NUM_DIRECTIONS);
    if      (random == 0) return NORTH;
    else if (random == 1) return SOUTH;
    else if (random == 2) return EAST;
    else                  return WEST;
  }
  
  /**
     Method size returns the number of constants (directions) in this type.
  */
  public static int size() {
    return NUM_DIRECTIONS;
  }
  
  /**
     Method toString returns a single character string representation
     of this direction.
  */
  public String toString() {
    if      (this.equals(NORTH)) return "N";
    else if (this.equals(SOUTH)) return "S";
    else if (this.equals(EAST))  return "E";
    else if (this.equals(WEST))  return "W";
    else                         return "Impossible direction!";
  }
}