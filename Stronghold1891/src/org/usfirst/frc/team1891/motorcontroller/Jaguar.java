package org.usfirst.frc.team1891.motorcontroller;

import edu.wpi.first.wpilibj.CANJaguar;

/**
 * @author Egan Schafer
 *
 */
public class Jaguar {
	 int codesPerRev;
	 CANJaguar jag;

	/**
	 *initializes a few varibles 
	 * @param jag Jaguar being controlled
	 */
	public Jaguar(CANJaguar jag)
	{
		this.jag = jag;
		 codesPerRev = 270;
	}
	
	//code for changing PID settings
	
	/**
	 * resets the number of codes on an encoder, default is 270.
	 * @param codes The number of codes on the encoders used.
	 */
	public void setCodesPerRev(int codes)
	{
		codesPerRev = codes;
	}
	
	//code for setting mode, enabling, and controlling jaguars with PID or not.
	
	/**
	 * Initializes a jag in percentage mode with PID enabled 
	 */
	public void initPercentPID()
	{
		jag.setPercentMode(CANJaguar.kQuadEncoder, codesPerRev);
		jag.enableControl();
	}
	
	/**
	 * Sets percentage of power used for a PID percentage motor.
	 * @param power power the 1 to -1 value of motor power
	 */
	public void setPercentagePID(double power)
	{
		jag.set(power);
	}
	
	/**
	 * Sets specified jag to percent mode without PID and enables it.
	 * Sets the mode of a jag to percentage mode
	 */
	public void initPercent()
	{
		jag.setPercentMode();
		jag.enableControl();
	}
	
	/**
	 * sets the percentage power of motor output
	 * @param power the 1 to -1 value of motor power
	 */
	public void setPercentage(double power)
	{
		jag.set(power);
	}
	
	/** 
	 * Sets specified jag to position mode with PID and enables control on it.

	 * 
	 */
	public void initPositionPID()
	{
		jag.setSpeedMode(CANJaguar.kQuadEncoder, codesPerRev, 0.1, 0.01, 0);
		jag.enableControl();
	}
	
	/** 
	 * Sets a postion for the jag to move motor to.
	 * @param postion specifies a position for a jag to be set to
	 * 
	 */
	public void setPositionPID(double postion)
	{
		jag.set(postion);;
	}
	
	/**
	 * Sets specified jag to voltage mode with PID and enables it.
	 * 
	 */
	public void initVoltagePID()
	{
		jag.setVoltageMode(CANJaguar.kQuadEncoder, codesPerRev);
		jag.enableControl();
	}
	
	/**
	 * Sets the voltage output for the specified jag.
	 * @param volt specifies a voltage to set motor to be set
	 * 
	 */
	public void setVoltagePID(double volt)
	{
		jag.set(volt);
	}
	
	/**
	 * Sets specified jag to voltage mode without PID and enables it.
	 */
	public void initVoltage()
	{
		jag.setVoltageMode();
		jag.enableControl();
	}
	
	/**
	 * Sets the voltage output for the specified jag.
	 * @param volt specifies a voltage to set motor to be set
	 */
	public void setVoltage(double volt)
	{
		jag.set(volt);
	}
	
	/**
	 * Sets jag to speed mode with PID and enables it
	 */
	public void initSpeed()
	{
		jag.setSpeedMode(CANJaguar.kQuadEncoder, codesPerRev, 0.1, 0.01, 0);
		jag.enableControl();
	}
	
	/**
	 * Sets jag to a specified speed
	 * @param speed speed to be set to.
	 */
	public void setSpeed(double speed)
	{
		jag.set(speed);
	}
}
