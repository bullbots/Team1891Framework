package org.usfirst.frc.team1891.motorcontroller;

import edu.wpi.first.wpilibj.CANJaguar;

public class Jaguar {

	public void setMode(CANJaguar jag)
	{
		jag.setPercentMode();
	}
	
	public void setSpeed(double power, CANJaguar jag)
	{
		jag.set(power);
	}
}
