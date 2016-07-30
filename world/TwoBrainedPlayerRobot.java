package robots.world;

import robots.world.Package;
import robots.world.*;
import robots.commands.*;
import robots.play.*;



public class TwoBrainedPlayerRobot extends PlayerRobot {
    
    private Brain brain1;
    private Brain brain2;
    private int called = 1;
    
    
    public TwoBrainedPlayerRobot(int id, int carryingCapacity, int money){
	super(id, carryingCapacity, money);
    }
    
    public void setBrain(Brain brain){
	super.setBrain(brain);
    }
    
    public void setSecondBrain(Brain brain){
	super.setBrain(brain);
    }
    
    public Brain getBrain(){
	if(called == 1){
	    called = called * -1;
	    return brain1;
	}
	else {
	    called = called * -1;
	    return brain2;
	}
    }
    
}
