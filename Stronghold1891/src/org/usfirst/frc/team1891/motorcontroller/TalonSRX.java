
package org.usfirst.frc.team1891.motorcontroller;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * @author Egan Schafer
 *
 */
public class TalonSRX {
	
	int codesPerRev;
	CANTalon talon;

	/**
	 * @param talon 
	 * 
	 */
	public TalonSRX(CANTalon talon)
	{
		this.talon = talon;
		codesPerRev = 270;
	}
	
	/**
	 * @param codes
	 */
	public void setCodesPerRev(int codes)
	{
		codesPerRev = codes;
	}
	
	/**
	 * 
	 */
	public void initPercentPID()
	{
		talon.setControlMode(0);
		talon.configEncoderCodesPerRev(codesPerRev);
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talon.enableControl();
	}
	
	/**
	 * 
	 * @param power
	 */
	public void setPercentagePID(double power)
	{
		talon.set(power);
	}
	
	/**
	 * 
	 */
	public void initPercent()
	{
		talon.setControlMode(0);
		talon.enableControl();
	}
	
	/**
	 *
	 * @param power
	 */
	public void setPercentage(double power)
	{
		talon.set(power);
	}
	
	/** 
	 * Sets specified jag to position mode with PID and enables control on it.
	 * @param jag specifies the jag to be set
	 * 
	 */
	public void initPositionPID()
	{
		talon.setControlMode(1);
		talon.configEncoderCodesPerRev(codesPerRev);
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talon.setPID(0.1, 0.01, 0);
		talon.enableControl();
	}
	
	/** 
	 * Sets a postion for the talon to move motor to.
	 * @param postion specifies a position for a talon to be set to
	 * 
	 */
	public void setPositionPID(double postion)
	{
		talon.set(postion);;
	}
	
	/**
	 * Sets specified talon to voltage mode with PID and enables it.
	 * @param jag specifies the talon to be set
	 * 
	 */
	public void initVoltagePID()
	{
		talon.setControlMode(4);
		talon.configEncoderCodesPerRev(codesPerRev);
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talon.enableControl();
	}
	
	/**
	 * Sets the voltage output for the specified talon.
	 * @param volt specifies a voltage to set motor to be set
	 */
	public void setVoltagePID(double volt)
	{
		talon.set(volt);
	}
	
	/**
	 * Sets specified talon to voltage mode without PID and enables it.
	 */
	public void initVoltage()
	{
		talon.setControlMode(4);
		talon.enableControl();
	}
	
	/**
	 * Sets the voltage output for the specified talon.
	 * @param volt specifies a voltage to set motor to be set
	 */
	public void setVoltage(double volt)
	{
		talon.set(volt);
	}
}
