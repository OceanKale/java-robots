package robots.play;

/**
     Thrown by Course.plot() to indicate that there does not exist 
   a safe path between the course endpoints.
*/
public class NoSafePathException extends RuntimeException {
  
  /**
     Creates an NoSafePathException
  */
  public NoSafePathException() {
    super("No safe path exists to destination from current tile.");
  }
}