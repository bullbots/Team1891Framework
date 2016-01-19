package org.usfirst.frc.team1891.joysticks;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickControl {
	
	Joystick Stick;
//	static double xValue;
//	static double yValue;
//	static double zValue;
	
	
	
	public void init(Joystick Joy)
	{
		Stick = Joy;
	}
	
	public double readX()
	{
		double xValue = Stick.getX();
		return xValue;
	}
	
	public double readY()
	{
		double yValue = Stick.getY();
		return yValue;
	}
}