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
	int value1;
	int value2;
	int value3;
	
	/**
	 * @param address
	 */
	public Arduino(int address)
	{
		arduino = new I2C(I2C.Port.kOnboard, address);
		this.address = address;
		data = new byte[3];
	}
	
	/**
	 * sets all bytes to a value, for use at start of game
	 * @param val1 defines the state for the arduino to enter
	 * @param val2 defines the dynamic level for the arduino to display
	 * @param val3 defines the team color
	 */
	public void write(int val1, int val2, int val3){
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
		value1 = (byte) value;
		write(value1,value2,value3);
	}
	
	/**
	 * changes dynamic value of the robot's current task
	 * @param value defines the dynamic level for the arduino to display
	 * 
	 */
	public void writeDynamic(int value)
	{
		value2 = (byte) value;
		write(value1,value2,value3);
	}
	
	/**
	 * changes team color value
	 * @param value val3 defines the team color
	 * 
	 */
	public void writeTeam(int value)
	{
		value3 = (byte) value;
		write(value1,value2,value3);
	}
}
