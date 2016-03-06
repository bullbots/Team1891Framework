package org.usfirst.frc.team1891.joysticks;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
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
	String profile = "No Supported Controller!!!";
	Joystick Stick1;
	Joystick Stick2;
	/**
	 * JoyVector contains all of the joystick data we might need
	 */
	public JoyVector vector;

	/**
	 * updates all of the data from the joystick and passes it to JoyVector
	 * @return the data from the joystick package
	 */
	public JoyVector getData()
	{
		switch(getProfile()){
		case 0:
			break;
			//xbox
		case 1:
			x = joy1Axis(0);
			y = joy1Axis(1);
			z = joy1Axis(4);
			angle = getAngle(x, -y);
			break;
			//single Logitech Attack
		case 2:
			x = joy1Axis(0);
			y = joy1Axis(1);
			z = 0;
			angle = getAngle(x,-y);
			break;
			//dual Logitech Attack
		case 3:
			x = (joy1Axis(1)-joy2Axis(1))/2;
			y = (joy1Axis(1)+joy2Axis(1))/2;
			z = joy1Axis(0);
			angle = getAngle(x,-y);
			break;
			//Nyko Airflo
		case 4:
			x = joy1Axis(0);
			y = joy1Axis(1);
			z = joy1Axis(3);
			angle = getAngle(x,-y);
			break;
			//Logitech Extreme 3D
		case 5:
			x = joy1Axis(0);
			y = joy1Axis(1);
			z = joy1Axis(2);
			angle = getAngle(x,-y);
			break;
		}
		SmartDashboard.putString("Controller:", profile);
		SmartDashboard.putNumber("Joystick X", x);
		SmartDashboard.putNumber("Joystick Y", y);
		SmartDashboard.putNumber("Joystick Z", z);
		SmartDashboard.putNumber("Joystick Angle", angle);
		return vector = new JoyVector(x, y, z, angle);
	}

	/**
	 * initiates the joystick
	 * @param Joy
	 * @param Joy2 Second joystick for two hands
	 * Always instantiate two joysticks, even if you're not using two. If you don't, it'll throw exceptions like a Mardi Gras float throwing beads
	 */
	public void init(Joystick Joy, Joystick Joy2)
	{
		Stick1 = Joy;
		Stick2 = Joy2;
		clearButtons();
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
	/**
	 * @param axis is the axis you want to read
	 * @return value for an axis
	 */
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
	 * This method identifies what type of joystick is plugged in.
	 * XBox is 1
	 * Single Logitech Attack is 2
	 * Dual Logitech Attack is 3
	 * Nyko Airflo is 4
	 * Logitech Extreme 3D is 5
	 * CURRENT BUG: Logitech Dual Action is read as a Logitech Extreme 3D
	 * @return value for joystick type index
	 */
	public int getProfile()
	{
		//sets the profile to be Xbox (360 or One)
		if(Stick1.getAxisCount() == 6 && (Stick1.getButtonCount() == 10||Stick1.getButtonCount() == 11))
		{
			profile = "XBox";
			return 1;
		}
		//sets the profile to be dual Logitech Attack
		else if(Stick1.getAxisCount() == 3 && Stick1.getButtonCount() == 11 && Stick2.getAxisCount() == 3 && Stick2.getButtonCount() == 11)
		{
			profile = "Dual Logitech Attack";
			return 3;
		}
		//sets the profile to be single Logitech Attack
		else if(Stick1.getAxisCount() == 3 && Stick1.getButtonCount() == 11)
		{
			profile = "Single Logitech Attack";
			return 2;
		}
		//sets the profile to be Nyko Airflo
		else if(Stick1.getAxisCount() == 4 && Stick1.getButtonCount() == 13)
		{
			profile = "Nyko Airflo";
			return 4;
		}
		//sets the profile to be Logitech Extreme 3D
		else if(Stick1.getAxisCount() == 4 && Stick1.getButtonCount() == 12)
		{
			profile = "Logitech Extreme 3D";
			return 5;
		}
		
		return 0;
	}

	/**
	 * Resets the button toggle booleans to default value.
	 */
	public void clearButtons(){
		for(int a = 0;a<20;a++){
			press[a] = false;
			release[a] = true;
			on[a] = false;
		}
	}

	/**
	 * @param i is the button number
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
	 * Only gives true once every button press and release
	 * @param i button to be read from
	 * @return if the button is pushed
	 */
	public boolean buttonControlled(int i)
	{
		if (button(i) && !press[i])
		{
			press[i] = true;
			return true;
		}
		else if (!button(i) && press[i])
		{
			press[i] = false;
			return false;
		}
		else
		{
			return false;
		}
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