package org.usfirst.frc.team1891.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

/**
 * @author 408robot
 *
 */
public class JoystickControl {
	
	double x;
	double y;
	double z;
	double angle;
	
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
		case 1:
			x = readX();
			y = readY();
			z = 0;
			angle = 0;
		break;
		case 2:
			x = readX();
			y = readY();
			z = 0;
			angle = 0;
		break;
		
		}
		System.out.println(x + " " + y);
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
	 * @return value for y axis
	 */
	public double readY()
	{
		//double yValue = Stick.getY();
		if (Math.abs(Stick.getRawAxis(1))>.05)return Stick.getRawAxis(1);
		return 0;
	}
	
	/**
	 * @return value for x axis
	 */
	public double readX(){
		//return Stick.getRawAxis(3)-Stick.getRawAxis(2);
		if (Math.abs(Stick.getRawAxis(0))>.05)return Stick.getRawAxis(0);
		return 0;
	}
	
	/**
	 * This method identifies what type of joystick is plugged in. X is 1, 
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