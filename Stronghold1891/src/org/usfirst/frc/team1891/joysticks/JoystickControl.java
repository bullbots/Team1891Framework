package org.usfirst.frc.team1891.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team1891.filewriter.LogWriter;
/**
 * @author Josh
 *
 */
public class JoystickControl {
	double DEADZONE = 0.05;
	double x;
	double y;
	double z;
	double angle;
	boolean press = false;
	boolean release = true;
	boolean on = false;
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
	public JoyVector getData()
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
			angle = getAngle(x, -y);
		break;
		//Logitech Attack
		case 2:
			x = axis(0);
			y = axis(1);
			z = 0;
			angle = getAngle(x,-y);
		break;
		
		}
		return vector = new JoyVector(x, y, z, angle);
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
	
	/**
	 * @param xcom
	 * @param ycom
	 * @return angle
	 */
	public double getAngle(double xcom, double ycom){
		double angle = Math.PI/2;
		if(Math.abs(xcom)<0.01&&ycom>.01){
			return 90;
		}
		if(Math.abs(xcom)<0.01&&ycom<-.01){
			return 270;
		}
		if(Math.abs(ycom)<0.01&&xcom<-.2){
			return 180;
		}
		if(Math.abs(ycom)<0.01&&xcom>.2){
			return 0;
		}
		if(xcom>=0.01){
			if(Math.atan(ycom/xcom)>0){
				angle = Math.atan(ycom/xcom);
			}
			if(Math.atan(ycom/xcom)<0){
				angle = 2*Math.PI+Math.atan(ycom/xcom);
			}
		}
		else if(xcom<-0.01&&Math.atan(ycom/xcom)!=0){
			angle = Math.atan(ycom/xcom)+Math.PI;
		}
		return Math.toDegrees(angle);
	}
	
	/**
	 * @param button
	 * @return if button is pressed
	 */
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
	 * @param index is the button index
	 * @return whether or not the switch is on
	 */
	
	public boolean buttonToggle(int index){
		
		if (!press && button(index) && release){
			press=true;
			on = true;
		}
		else if (press && !button(index) && release){
			press=false;
			release = false;
			on = true;
		}
		else if (!press && button(index) && !release){
			press = true;
			on = false;
		}
		else if (press && !button(index) && !release){
			press = false;
			release = true;
			on = false;
		}
		return on;
	}
	
	/**
	 * sets the joystick rumble pack
	 * @param isOn true is if you want rumble, false is if you don't
	 */
	public void setRumble(boolean isOn){
		if(isOn){
			Stick.setRumble(RumbleType.kLeftRumble, 1);
			Stick.setRumble(RumbleType.kRightRumble, 1);
		}
		if(!isOn){
			Stick.setRumble(RumbleType.kLeftRumble, 0);
			Stick.setRumble(RumbleType.kRightRumble, 0);
		}
	}
}