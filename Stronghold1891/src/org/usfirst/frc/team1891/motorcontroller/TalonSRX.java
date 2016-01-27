/**
 * 
 */
package org.usfirst.frc.team1891.motorcontroller;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * @author Egan Schafer
 *
 */
public class TalonSRX {
	
	int codesPerRev;

	/**
	 * 
	 */
	public TalonSRX()
	{
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
	 * @param talon
	 */
	public void initPercentPID(CANTalon talon)
	{
		talon.setControlMode(1);
		talon.enable();
	}
	
	/**
	 * @param talon
	 * @param power
	 */
	public void setPercentagePID(CANTalon talon, double power)
	{
		talon.set(power);
	}
	
	/**
	 * @param talon
	 */
	public void initPercent(CANTalon talon)
	{
		talon.setControlMode(1);
		talon.enable();
	}
	
	/**
	 * @param talon
	 * @param power
	 */
	public void setPercentage(CANTalon talon, double power)
	{
		talon.set(power);
	}
}
