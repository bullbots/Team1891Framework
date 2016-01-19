package org.usfirst.frc.team1891.robot;

import org.usfirst.frc.team1891.filewriter.*;
/**
 * This error should be thrown when there is an invalid state of the machine.
 * @author Tyler
 *
 */
@SuppressWarnings("serial")
public class InvalidStateError extends Exception {
	
	/**
     * Thrown when there is an invalid state.
     */
    public InvalidStateError ()
    {
    	super ("The state was invalid");
    	LogWriter fw = new LogWriter();
    	fw.appendErrorToLog("The state of the machine was invalid");
	   
    }
}
