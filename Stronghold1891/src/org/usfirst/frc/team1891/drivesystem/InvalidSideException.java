package org.usfirst.frc.team1891.drivesystem;

import org.usfirst.frc.team1891.filewriter.*;
/**
 * This error should be thrown when there is an invalid state of the machine.
 * @author Tyler
 *
 */
@SuppressWarnings("serial")
public class InvalidSideException extends Exception {
	
	/**
     * Thrown when there is an invalid state.
     */
    public InvalidSideException ()
    {
    	super ("The side was invalid");
    	LogWriter fw = new LogWriter();
    	fw.appendErrorToLog("The side of the motor was invalid.");
	   
    }
}
