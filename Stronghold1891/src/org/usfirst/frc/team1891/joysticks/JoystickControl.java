package org.usfirst.frc.team1891.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.Timer;

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
	boolean a = false;
	int aCount = 0;
	Timer time = new Timer();
	
	
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
			x = axis(0)*0;
			y = axis(1)*0;
			z = 0;
			angle = 0;
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
	public boolean button(int button)
	{
		return Stick.getRawButton(button);
	}
	
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
	 * IGNORE EVERYTHING BENEATH THIS, IT'S JUST FOR TESTING
	 */
	
	public void rumble(){
		if (!a && button(1)){
			setRumble();
			a=true;
		}
		if (a && !button(1)){
			stopRumble();
			a=false;
		}
	}
	
	/**
	 * sets the joystick rumble pack
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