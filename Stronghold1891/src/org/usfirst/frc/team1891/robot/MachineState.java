package org.usfirst.frc.team1891.robot;

import org.usfirst.frc.team1891.filewriter.LogWriter;

/**
 * A class that controls the state of the machine.
 * @author Tyler
 *
 */
public class MachineState{
	//0. Find crossable defense
	//1. Route path to defense
	//2. Traverse to defense
	//3. Cross defense
	//4. Fine goal target
	//5. Move to shooting position
	//6. Fire at target.
	//7. Look for rebound and begin moving back.
	private int stateNum=0;

	/**
	 *0. Find crossable defense, robot should be looking for closest possible defense.
	 *1. Route path to defense, while the crossable defense can be found robot should be routing a path to the defense.
	 *2. Traverse to defense, while there is still a viable path to the defense, move to the defense.
	 *3. Cross defense, robot should be crossing the defense, at this point the state of the machine should not reduce.
	 *4. Find goal target, robot should be looking for the nearest and easiest goal to shoot into.
	 *5. Move to shooting position, while the robot can still find the target goal it should be moving into shooting position.
	 *6. Fire at target, robot should fire the ball.
	 *7. Look for rebound and begin moving back, at this point the robot should scan the field for a missed shot, if not it should return.
	 * @return the number of the current state
	 * @throws InvalidStateException when the state of the machine was invalid
	 */
	public int getState() throws InvalidStateException{
		LogWriter fw = new LogWriter();
    	fw.appendErrorToLog("Current State: "+stateNum);
		if(stateNum<0 || stateNum>7){throw new InvalidStateException();}
		return stateNum;
	}
	
	/**
	 * This method should be called to update the current state of the machine.
	 */
	public void update(){
		switch(stateNum){
		case 0://Finding crossable defense
			break;
		case 1:// Routing to defense
			break;
		case 2://Moving to defense
			break;
		case 3://Crossing defense
			break;
		case 4://Finding goal target.
			break;
		case 5://Moving to shooting position
			break;
		case 6://Firing at target
			break;
		case 7://End autonomous 
			break;
		}
	}
}
