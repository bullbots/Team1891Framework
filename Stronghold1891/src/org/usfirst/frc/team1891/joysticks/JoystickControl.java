package org.usfirst.frc.team1891.joysticks;

import edu.wpi.first.wpilibj.Joystick;

/**
 * @author 408robot
 *
 */
public class JoystickControl {
	
	Joystick Stick;
//	static double xValue;
//	static double yValue;
//	static double zValue;
	
	
	
	/**
	 * @param Joy
	 */
	public void init(Joystick Joy)
	{
		Stick = Joy;
	}
	
	/**
	 * @return
	 */
	public double readX()
	{
		double xValue = Stick.getX();
		return xValue;
	}
	
	/**
	 * @return
	 */
	public double readY()
	{
		double yValue = Stick.getY();
		return yValue;
	}
}