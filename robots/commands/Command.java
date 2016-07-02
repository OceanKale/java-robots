package robots.commands;

/**
   The abstract class of all commands.<br><br>
   
   The player controls the robot by issuing commands.<br><br>

   * The Move command moves the robot to an adjacent square, in one of the four directions 
   north, east, south or west.<br><br>
   * The Pick command is used to pick packages. Packages are initially available from home bases. 
   Packages may not be picked up if they are too heavy, or if there are no packages available.<br><br>
   * The Drop command is used to drop packages. Packages are always dropped. <br><br>

   The Pick and Drop commands are followed by a sequence of zero or more package ids. It is not an 
   error to attempt to pick up packages that are not available. Nor is it an error to drop packages 
   that are not being carried.<br>

   The robot dies if<br>

   1. it moves (or is pushed) onto a lethal tile, i.e., one containing water, or<br>
   2. it sends a malformed command to the server, or<br>
   3. it spends all its money. <br><br>

   In an effort to minimize the chances of the robot dying due to (2) above, 
   this class ensures that the bid is always non-zero. 
*/

public abstract class Command {

    private int bid;

    /**
       Creates a command with bid 1. 
    */
    public Command() {
	bid = 1;
    }

    /**
       Creates a command with the given bid value. 
    */
    public Command(int bid) {
	//if (bid != 0) {
	this.bid = bid;
	//}
    }

    /**
       Returns the bid associated with the command, unless the bid is zero, in which case 
       the value -1 is returned. 
    */
    public int getBid() {
	if (bid != 0) {
	    return bid;
	}
	else {
	    return -1;
	}
    }

}
