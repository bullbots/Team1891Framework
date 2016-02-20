package org.usfirst.frc.team1891.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	boolean[] press = new boolean[20];
	boolean[] release = new boolean[20];
	boolean[] on = new boolean[20];
	int aCount = 0;
	Timer time = new Timer();

	Joystick Stick1;
	Joystick Stick2;
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
			//xbox
		case 1:
			x = joy1Axis(0);
			y = joy1Axis(1);
			z = 0;
			angle = getAngle(x, -y);
			break;
			//single Logitech Attack
		case 2:
			x = joy1Axis(0);
			y = joy1Axis(1);
			z = 0;
			angle = getAngle(x,-y);
			break;

		case 3:

			x = (joy1Axis(1)-joy2Axis(1))/2;
			y = (joy1Axis(1)+joy2Axis(1))/2;
			z = 0;
			angle = getAngle(x,-y);

		}
		return vector = new JoyVector(x, y, z, angle);
	}

	/**
	 * initiates the joystick
	 * @param Joy
	 * @param Joy2 Second joystick for two hands
	 */
	public void init(Joystick Joy, Joystick Joy2)
	{
		Stick1 = Joy;
		Stick2 = Joy2;
	}
	public void init(Joystick Joy)
	{
		Stick1 = Joy;
		for(int a = 0; a<20; a++){
			press[a] = false;
			release[a] = true;
			on[a] = false;
		}
	}

	/**
	 * @param axis is the axis you want to read
	 * @return value for an axis
	 */
	public double joy1Axis(int axis)
	{
		if (Math.abs(Stick1.getRawAxis(axis))>DEADZONE)return Stick1.getRawAxis(axis);
		return 0;
	}
	public double joy2Axis(int axis)
	{
		if (Math.abs(Stick2.getRawAxis(axis))>DEADZONE)return Stick2.getRawAxis(axis);
		return 0;
	}

	/**
	 * @param xcom
	 * @param ycom
	 * @return angle
	 */
	public double getAngle(double xcom, double ycom){
		double angle = Math.PI/2;
		if(Math.abs(xcom)<0.01&&ycom>DEADZONE){
			return 90;
		}
		if(Math.abs(xcom)<0.01&&ycom<-DEADZONE){
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
		return Stick1.getRawButton(button);
	}

	/**
	 * This method identifies what type of joystick is plugged in. Xbox 360 is 1. Logitech Attack is 2.
	 * @return value for joystick type index
	 */
	public int getProfile()
	{
		//sets the profile to be Xbox (360 or One)


		if(Stick1.getAxisCount() == 6 && (Stick1.getButtonCount() == 10||Stick1.getButtonCount() == 11))
		{
			return 1;
		}
		//sets the profile to be single Logitech Attack
		else if(Stick1.getAxisCount() == 3 && Stick1.getButtonCount() == 11)
		{
			return 2;
		}
		//sets the profile to be dual Logitech Attack
		else if(Stick1.getAxisCount() == 3 && Stick1.getButtonCount() == 11 && Stick2.getAxisCount() == 3 && Stick2.getButtonCount() == 11)
		{
			return 3;
		}


		return 0;
	}


	/**
	 * @param index is the button index
	 * @return whether or not the switch is on
	 */

	public boolean buttonToggle(int i){
		
		if (!press[i] && button(i) && release[i]){
			press[i]=true;
			on[i] = true;
		}
		else if (press[i] && !button(i) && release[i]){
			press[i]=false;
			release[i] = false;
			on[i] = true;
		}
		else if (!press[i] && button(i) && !release[i]){
			press[i] = true;
			on[i] = false;
		}
		else if (press[i] && !button(i) && !release[i]){
			press[i] = false;
			release[i] = true;
			on[i] = false;
		}
		return on[i];
	}

	/**
	 * sets the joystick rumble pack
	 * @param isOn true is if you want rumble, false is if you don't
	 */
	public void setRumble(boolean isOn){
		if(isOn){
			Stick1.setRumble(RumbleType.kLeftRumble, 1);
			Stick1.setRumble(RumbleType.kRightRumble, 1);
		}
		if(!isOn){
			Stick1.setRumble(RumbleType.kLeftRumble, 0);
			Stick1.setRumble(RumbleType.kRightRumble, 0);
		}
	}
}