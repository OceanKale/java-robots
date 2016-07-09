package robots.play;

/**
 Thrown by PlottingBrain.nextDirection when the nextLoc and playerLoc tiles
   are the same tile.
*/
public class SameTileException extends RuntimeException {

    /**
       Creates SameTileException
    */
    public SameTileException() {
        super("Same tiles in call to PlottingBrain.nextDirection.");
    }

}