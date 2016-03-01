package org.usfirst.frc.team1891.drivesystem;

import org.usfirst.frc.team1891.motorcontroller.*;

/**
 * This is used as a support class for the drive system constructor. To properly use the
 *Drive system controller create a list of all motors used in the drive system, using this class.
 *Then use that list for the constructor.
 * @author Tyler
 *
 */
public class MotorAndSide {
	
	Jaguar jag= null;
	TalonSRX talonSRX = null;
	String side;
	boolean isMaster;
	/**
	 * Constructor for MotorAndSide, instatiates the motor with the information about what side of the robot
	 * it is on. Use this constructor if the drive system consists of jag motors.
	 * @param jag the jag object 
	 * @param side of the robot the jag is on. value of side should either be "RIGHT" or "LEFT" else an error will be thrown.
	 * @param isMaster true if the motor is a master motor, false otherwise.
	 * @throws InvalidSideException thrown when the side of the motor is invalid.
	 */
	public MotorAndSide(Jaguar jag, String side, boolean isMaster) throws InvalidSideException{
		if(!side.equals("RIGHT") && !side.equals("LEFT")) {throw new InvalidSideException();}
		this.jag=jag;
		this.side=side;
		this.isMaster=isMaster;
	}
	
	/**
	 * Constructor for MotorAndSide, instatiates the motor with the information about what side of the robot
	 * it is on. Use this constructor if the drive system consists of TalonSRX motors.
	 * @param talon the TalonSRX motor object
	 * @param side side of the robot the TalonSRX is on. value of side should either be "RIGHT" or "LEFT" else an error will be thrown.
	 * @param isMaster true if the motor is a master motor, false otherwise.
	 * @throws InvalidSideException thrown when the side of the motor is invalid.
	 */
	public MotorAndSide(TalonSRX talon, String side, boolean isMaster) throws InvalidSideException{
		if(!side.equals("RIGHT") && !side.equals("LEFT")) {throw new InvalidSideException();}
		this.talonSRX=talon;
		this.side=side;
		this.isMaster=isMaster;
	}

	/**
	 * @return returns the current jag object, will return null if jag motors are not being used.
	 */
	public Jaguar getJag() {
		return jag;
	}

	/**
	 * @return returns the current talonSRX object, will return null if srx motors are not being used.
	 */	
	public TalonSRX getTalonSRX() {
		return talonSRX;
	}

	/**
	 * @return the side that the motor is on.
	 */
	public String getSide() {
		return side;
	}

	/**
	 * @return true if the motor is a master motor and other motors should follow it. False if it should follow a master motor on its same side.
	 */
	public boolean isMaster() {
		return isMaster;
	}
}
