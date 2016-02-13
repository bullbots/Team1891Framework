package org.usfirst.frc.team1891.machinestate2016;

import org.usfirst.frc.team1891.filewriter.*;
/**
 * This error should be thrown when there is an invalid state of the machine.
 * @author Tyler
 *
 */
@SuppressWarnings("serial")
public class InvalidStateException extends Exception {
	
	/**
     * Thrown when there is an invalid state.
     */
    public InvalidStateException ()
    {
    	super ("The state was invalid");
    	LogWriter fw = new LogWriter();
    	fw.appendErrorToLog("The state of the machine was invalid");
	   
    }
}
