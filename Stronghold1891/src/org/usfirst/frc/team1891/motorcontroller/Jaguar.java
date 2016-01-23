package org.usfirst.frc.team1891.motorcontroller;

import edu.wpi.first.wpilibj.CANJaguar;

/**
 * @author Egan Schafer
 *
 */
public class Jaguar {

	/**
	 * Sets the mode of a jag to percentage mode
	 * @param jag jag to be set
	 */
	public void setMode(CANJaguar jag)
	{
		jag.setPercentMode();
	}
	
	/**
	 * sets the percentage power of motor output
	 * @param power the 1 to -1 value of motor power
	 * @param jag the jag to be set
	 */
	public void setSpeed(double power, CANJaguar jag)
	{
		jag.set(power);
	}
}
