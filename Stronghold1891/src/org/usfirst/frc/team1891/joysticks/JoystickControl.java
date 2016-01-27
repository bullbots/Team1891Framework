package org.usfirst.frc.team1891.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

/**
 * @author 408robot
 *
 */
public class JoystickControl {
	double DEADZONE = 0.05;
	double x;
	double y;
	double z;
	double angle;
	boolean a;
	
	Joystick Stick;
	/**
	 * JoyVector contains all of the joystick data we might need
	 */
	public JoyVector vector;
	
/**
 * updates all of the data from the joystick and passes it to JoyVector
 */
	public void getData()
	{
		switch(getProfile()){
		case 0:
			System.out.println("Not a supported controller!");
		break;
		//xbox 360
		case 1:
			x = axis(0);
			y = axis(1);
			z = 0;
			angle = 0;
			a = 
		break;
		//Logitech Attack
		case 2:
			x = axis(0);
			y = axis(1);
			z = 0;
			angle = 0;
		break;
		
		}
		vector = new JoyVector(x, y, z, angle);
	}
	
	/**
	 * initiates the joystick
	 * @param Joy
	 */
	public void init(Joystick Joy)
	{
		Stick = Joy;
		
	}
	
	/**
	 * @param axis is the axis you want to read
	 * @return value for an axis
	 */
	public double axis(int axis)
	{
		if (Math.abs(Stick.getRawAxis(axis))>DEADZONE)return Stick.getRawAxis(axis);
		return 0;
	}
	public boolean 
	
	/**
	 * This method identifies what type of joystick is plugged in. Xbox 360 is 1. Logitech Attack is 2.
	 * @return value for joystick type index
	 */
	public int getProfile()
	{
		//sets the profile to be Xbox 360
		if(Stick.getAxisCount() == 6 && Stick.getButtonCount() == 10)
		{
			return 1;
		}
		//sets the profile to be Logitech Attack
		else if(Stick.getAxisCount() == 3 && Stick.getButtonCount() == 11)
		{
			return 2;
		}
		return 0;
	}
	
	
	/**
	 * sets the joystick rumble pack to on
	 */
	public void setRumble(){
		Stick.setRumble(RumbleType.kLeftRumble, 1);
		Stick.setRumble(RumbleType.kRightRumble, 1);
	}
	
	/**
	 * stops the joystick rumble pack
	 */
	public void stopRumble(){
		Stick.setRumble(RumbleType.kLeftRumble, 0);
		Stick.setRumble(RumbleType.kRightRumble, 0);
	}
	
}