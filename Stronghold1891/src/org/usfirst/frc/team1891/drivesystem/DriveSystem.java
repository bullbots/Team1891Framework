package org.usfirst.frc.team1891.drivesystem;

import edu.wpi.first.wpilibj.CANJaguar;
import org.usfirst.frc.team1891.motorcontroller.Jaguar;

public class DriveSystem {
	
	Jaguar jagControl;
	CANJaguar jag1;
	public void init(CANJaguar jag)
	{
		jag1 = jag;
		jagControl = new Jaguar();
		jagControl.setMode(jag1);
	}
	
	public void move(double y)
	{
		jagControl=new Jaguar();
		jagControl.setSpeed(y, jag1);
	}
}