package org.usfirst.frc.team1891.motorcontroller;

import edu.wpi.first.wpilibj.CANJaguar;

/**
 * @author Egan Schafer
 *
 */
public class Jaguar {
	 int codesPerRev;

	/**
	 *initializes a few varibles 
	 */
	public Jaguar()
	{
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
	 * @param jag specifies the jag to be set
	 */
	public void initPercentPID(CANJaguar jag)
	{
		jag.setPercentMode(CANJaguar.kQuadEncoder, codesPerRev);
		jag.enableControl();
	}
	
	/**
	 * Sets percentage of power used for a PID percentage motor.
	 * @param power power the 1 to -1 value of motor power
	 * @param jag specifies the jag to be set
	 */
	public void setPercentagePID(double power, CANJaguar jag)
	{
		jag.set(power);
	}
	
	/**
	 * Sets specified jag to percent mode without PID and enables it.
	 * Sets the mode of a jag to percentage mode
	 * @param jag specifies the jag to be set
	 */
	public void initPercent(CANJaguar jag)
	{
		jag.setPercentMode();
		jag.enableControl();
	}
	
	/**
	 * sets the percentage power of motor output
	 * @param power the 1 to -1 value of motor power
	 * @param jag specifies the jag to be set
	 */
	public void setPercentage(double power, CANJaguar jag)
	{
		jag.set(power);
	}
	
	/** 
	 * Sets specified jag to position mode with PID and enables control on it.
	 * @param jag specifies the jag to be set
	 * 
	 */
	public void initPositionPID(CANJaguar jag)
	{
		jag.setSpeedMode(CANJaguar.kQuadEncoder, codesPerRev, 0.1, 0.01, 0);
		jag.enableControl();
	}
	
	/** 
	 * Sets a postion for the jag to move motor to.
	 * @param jag specifies the jag to be set
	 * @param postion specifies a position for a jag to be set to
	 * 
	 */
	public void setPositionPID(double postion, CANJaguar jag)
	{
		jag.set(postion);;
	}
	
	/**
	 * Sets specified jag to voltage mode with PID and enables it.
	 * @param jag specifies the jag to be set
	 * 
	 */
	public void initVoltagePID(CANJaguar jag)
	{
		jag.setVoltageMode(CANJaguar.kQuadEncoder, codesPerRev);
		jag.enableControl();
	}
	
	/**
	 * Sets the voltage output for the specified jag.
	 * @param jag specifies the jag to be set
	 * @param volt specifies a voltage to set motor to be set
	 * 
	 */
	public void setVoltagePID(double volt, CANJaguar jag)
	{
		jag.set(volt);
	}
	
	/**
	 * Sets specified jag to voltage mode without PID and enables it.
	 * @param jag specifies the jag to be set
	 */
	public void initVoltage(CANJaguar jag)
	{
		jag.setVoltageMode();
		jag.enableControl();
	}
	
	/**
	 * Sets the voltage output for the specified jag.
	 * @param jag specifies the jag to be set
	 * @param volt specifies a voltage to set motor to be set
	 */
	public void setVoltage(double volt, CANJaguar jag)
	{
		jag.set(volt);
	}
}
