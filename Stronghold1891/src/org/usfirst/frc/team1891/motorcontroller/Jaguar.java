package org.usfirst.frc.team1891.motorcontroller;

import edu.wpi.first.wpilibj.CANJaguar;

/**
 * @author Egan Schafer
 *
 */
public class Jaguar {

	
	/**
	 * Initializes a jag in percentage mode with PID enabled 
	 * @param jag specifies the jag to be set
	 */
	public void initPercentPID(CANJaguar jag)
	{
		jag.setPercentMode(CANJaguar.kQuadEncoder, 270);
		jag.enable();
	}
	
	/**
	 * @param power power the 1 to -1 value of motor power
	 * @param jag specifies the jag to be set
	 */
	public void setPercentagePID(double power, CANJaguar jag)
	{
		jag.set(power);
	}
	
	/**
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
	 * @param jag specifies the jag to be set
	 * 
	 */
	public void initPositionPID(CANJaguar jag)
	{
		jag.setSpeedMode(CANJaguar.kQuadEncoder, 270, 0.1, 0.01, 0);
		jag.enableControl();
	}
	
	/**
	 * @param jag specifies the jag to be set
	 * @param postion specifies a position for a jag to be set to
	 * 
	 */
	public void setPositionPID(double postion, CANJaguar jag)
	{
		jag.set(postion);;
	}
	
	/**
	 * @param jag specifies the jag to be set
	 * 
	 */
	public void initVoltagePID(CANJaguar jag)
	{
		jag.setVoltageMode(CANJaguar.kQuadEncoder, 270);
		jag.enable();
	}
	
	/**
	 * @param jag specifies the jag to be set
	 * @param volt specifies a voltage to set motor to be set
	 * 
	 */
	public void setVoltagePID(double volt, CANJaguar jag)
	{
		jag.set(volt);
	}
	
	/**
	 * @param jag specifies the jag to be set
	 */
	public void initVoltage(CANJaguar jag)
	{
		jag.setVoltageMode();
		jag.enable();
	}
	
	/**
	 * @param jag specifies the jag to be set
	 * @param volt specifies a voltage to set motor to be set
	 */
	public void setVoltage(double volt, CANJaguar jag)
	{
		jag.set(volt);
	}
}
