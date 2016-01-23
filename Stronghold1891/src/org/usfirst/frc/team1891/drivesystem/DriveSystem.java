package org.usfirst.frc.team1891.drivesystem;

import edu.wpi.first.wpilibj.CANJaguar;
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
		
	}
	
	/**
	 * @param x 
	 * @param y
	 */
	public void move(double x, double y)
	{
		double leftSide = (x-y);
		double rightSide = (x+y);
		jagControl.setSpeed(leftSide, jag1);
		jagControl.setSpeed(leftSide, jag2);
		jagControl.setSpeed(rightSide, jag3);
		jagControl.setSpeed(rightSide, jag4);
	}
	
	/**
	 * For spinning a single wheel
	 * @param power
	 */
	public void spinWheel(double power)
	{
		jagControl.setSpeed(power, jag1);
	}
	
}