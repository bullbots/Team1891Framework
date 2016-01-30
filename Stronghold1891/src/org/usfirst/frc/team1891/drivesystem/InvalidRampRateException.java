package org.usfirst.frc.team1891.drivesystem;

import org.usfirst.frc.team1891.filewriter.LogWriter;

/**
 * 
 *Thrown when the input ramp rate is invalid.
 * @author Tyler Manning
 */
@SuppressWarnings("serial")
public class InvalidRampRateException extends Exception {
	/**
     * Thrown when there is an invalid state.
     */
    public InvalidRampRateException ()
    {
    	super ("The ramp rate was invalid.");
    	LogWriter fw = new LogWriter();
    	fw.appendErrorToLog("The ramp rate was invalid.");
	   
    }
}
