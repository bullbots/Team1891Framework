package org.usfirst.frc.team1891.drivesystem;

import edu.wpi.first.wpilibj.CANJaguar;

import org.usfirst.frc.team1891.joysticks.JoyVector;
import org.usfirst.frc.team1891.motorcontroller.Jaguar;

/**
 * @author Egan Schafer
 *
 */
public class DriveSystem {
	
	Jaguar jagControl;
	CANJaguar jag1;
	CANJaguar jag2;
	CANJaguar jag3;
	CANJaguar jag4;
	int ramp = 100;
	
	/**
	 * @param jag
	 */
	public void init(CANJaguar jag)
	{
		jag1 = jag;
	}
	/**
	 * Used to pass all the drive jaguars to the driveSystem.
	 * @param jaguar1 the first jag passed to driveSystem, motor must be on left side
	 * @param jaguar2 the second jag passed to driveSystem, motor must be on left side
	 * @param jaguar3 the third jag passed to driveSystem, motor must be on right side
	 * @param jaguar4 the fourth jag passed to driveSystem, motor must be on right side
	 */
	public void init(CANJaguar jaguar1, CANJaguar jaguar2, CANJaguar jaguar3, CANJaguar jaguar4)
	{
		jag1 = jaguar1;
		jag2 = jaguar2;
		jag3 = jaguar3;
		jag4 = jaguar4;
		jagControl = new Jaguar();
		jagControl.setMode(jag1);
		jagControl.setMode(jag2);
		jagControl.setMode(jag3);
		jagControl.setMode(jag4);
		jag1.setVoltageRampRate(ramp);
		jag2.setVoltageRampRate(ramp);
		jag3.setVoltageRampRate(ramp);
		jag4.setVoltageRampRate(ramp);
		
		
	}
	
	/**
	 * This method sets the jag speed values.
	 * @param vector
	 */
	public void moveTank(JoyVector vector)
	{
		double x = vector.getX_comp();
		double y = vector.getY_comp();
		double leftSide = (x-y) * scaleTank(x,y);
		double rightSide = (x+y) * scaleTank(x,y);
		jagControl.setSpeed(leftSide, jag1);
		jagControl.setSpeed(leftSide, jag2);
		jagControl.setSpeed(rightSide, jag3);
		jagControl.setSpeed(rightSide, jag4);
		
	}
	
	/**
	 * This method makes sure the set speed on any jag does not exceed the max value.
	 * @param x
	 * @param y
	 * @return the the damp for the tank drive
	 */
	public double scaleTank(double x, double y)
	{
		double max = 1;
		double right = Math.abs(y + x);
		double left = Math.abs(y+ x);
		if (left>1&&left>right){
			max = 1/left;
		}
		if (right>1&&right>left){
			max = 1/right;
		}
		return max * .4;
		
	}
	
}