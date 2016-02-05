package org.usfirst.frc.team1891.arduino;

import edu.wpi.first.wpilibj.I2C;

/**
 * @author 408robot
 *
 */
public class Arduino {
	
	I2C arduino;
	int address;
	byte data[];
	
	/**
	 * @param address
	 */
	public Arduino(int address)
	{
		arduino = new I2C(I2C.Port.kMXP, address);
		this.address = address;
		data = new byte[3];
	}
	
	/**
	 * sets all bytes to a value, for use at start of game
	 * @param val1 defines the state for the arduino to enter
	 * @param val2 defines the dynamic level for the arduino to display
	 * @param val3 defines the team color
	 */
	public void Write(int val1, int val2, int val3){
		data[0] = (byte) val1;
		data[1] = (byte) val2;
		data[2] = (byte) val3;
		arduino.writeBulk(data);
	}
	
	/**
	 * changes the current state of the arduino
	 * @param value defines the state for the arduino to enter
	 */
	public void writeState(int value)
	{
		data[0] = (byte) value;
		arduino.writeBulk(data);
	}
	
	/**
	 * changes dynamic value of the robot's current task
	 * @param value defines the dynamic level for the arduino to display
	 * 
	 */
	public void writeDynamic(int value)
	{
		data[1] = (byte) value;
		arduino.writeBulk(data);
	}
	
	/**
	 * changes team color value, shouldn't be used under normal circumstances
	 * @param value val3 defines the team color
	 * 
	 */
	public void writeTeam(int value)
	{
		data[2] = (byte) value;
		arduino.writeBulk(data);
	}
}
