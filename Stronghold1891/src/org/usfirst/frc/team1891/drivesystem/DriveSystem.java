package org.usfirst.frc.team1891.drivesystem;

import edu.wpi.first.wpilibj.CANJaguar;

import java.util.LinkedList;

import org.usfirst.frc.team1891.joysticks.JoyVector;
import org.usfirst.frc.team1891.motorcontroller.Jaguar;

/**
 * @author Egan Schafer, Tyler Manning
 *
 */
public class DriveSystem {
	
	LinkedList<MotorAndSide> motorList= null;
	
	/**
	 * The input of all motors on the current system, ensure that all motors have been instantiated
	 * @param motorList a list of ALL motors on the current system.
	 */
	public DriveSystem(LinkedList<MotorAndSide> motorList){
		
	}
	
}