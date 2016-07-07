package robots.world;

/**

   Thrown by Tile.read() to indicate that the given character
   is unrecognized.
*/
public class UnknownTileException extends RuntimeException {
  
  /**
     Creates an UnknownTileException with the given message
  */
  public UnknownTileException(String message) {
    super(message);
  }
}