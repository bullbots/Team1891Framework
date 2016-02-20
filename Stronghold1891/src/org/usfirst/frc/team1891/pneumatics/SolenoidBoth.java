package org.usfirst.frc.team1891.pneumatics;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Class that represents a single solenoid, both the on and off port
 * @author Tyler
 *
 */
public class SolenoidBoth {
	private Solenoid solenoidOn;
	private Solenoid solenoidOff;
	
	public SolenoidBoth(int portOn, int portOff){
		solenoidOn= new Solenoid(portOn);
		solenoidOff= new Solenoid(portOff);
	}
	
	public void turnOff(){
		solenoidOn.set(false);
		solenoidOff.set(true);
	}
	
	public void turnOn(){
		solenoidOff.set(false);
		solenoidOn.set(true);
	}
	
}
