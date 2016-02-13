package org.usfirst.frc.team1891.analog;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author 408robot
 *
 */
public class Analog {
	
	AnalogInput ai;
	int test;
	int Oversamplebits;
	int AverageBits;
	double minimum;
	double maximum;
	double conversion;
	double index1;
	double index2;
	int range;
	File file;
	String[] config;
	String name;

	/**
	 * This class requires a config file to run.
	 * The first line of the config file must start with the name of the sensor to be used, valid names are 
	 * "pot" - potentiometer
	 * "ultra" - ultra sonic sensor
	 * "ir" - infared sensor
	 * 
	 * each type needs its own specific values in the file, each value will be on its own line in the order listed below
	 * potentiometer - oversampleBits, averageBits, minimum value of sensor, maximum value of sensor, and range of sensor
	 * ultrasonic - oversampleBits, averageBits,
	 * ir - oversampleBits, averageBits, index1, index2
	 * 		indexes are two numbers derived from the exponential function that best fits the sensor's data. the first is the coefficiant of x in the equation, while the second is the power the x is to.
	 * @param ai analog sensor to be used
	 * @param configPath the path to the config file
	 * @throws IOException will be thrown if address is incorrect or file doesn't exist
	 */
	public Analog(AnalogInput ai, String configPath) throws IOException
	{
		this.ai = ai;
		file = new File(configPath);
		Scanner scan = new Scanner(file);
		name = scan.nextLine();
		System.out.println(name);
		if (name.equals("pot"))
		{
			config = new String[5];
			System.out.println("pretty dank!");
			int x = 0;
			while (scan.hasNext())
			{
				config[x] = scan.next();
				System.out.println(config[x]);
				x++;
			}
			Oversamplebits = Integer.parseInt(config[0]);
			AverageBits = Integer.parseInt(config[1]);
			minimum = Integer.parseInt(config[2]);
			maximum = Integer.parseInt(config[3]);
			range = Integer.parseInt(config[4]);
			conversion = (maximum-minimum)/range;
			ai.setOversampleBits(Oversamplebits);
			ai.setAverageBits(AverageBits);
		}
		else if (name.equals("ultra"))
		{
			ai.setOversampleBits(4);
			ai.setAverageBits(2);
		}
		else if (name.equals("ir"))
		{
			config = new String[5];
			int x = 0;
			while (scan.hasNext())
			{
				config[x] = scan.next();
				System.out.println(config[x]);
				x++;
			}
			Oversamplebits = Integer.parseInt(config[0]);
			AverageBits = Integer.parseInt(config[1]);
			index1 = Double.parseDouble(config[2]);
			index2 = Double.parseDouble(config[3]);
			ai.setAverageBits(AverageBits);
			ai.setOversampleBits(Oversamplebits);
		}
		
		
//		Oversamplebits = 4;
//		AverageBits = 2;
//		minimum = 4;
//		maximum = 4079;
//		range = 300;
//		conversion = (maximum-minimum)/range;

	}
	
	/**
	 * reads the degrees turned for a potentiometer
	 * @return the current angle
	 * 
	 */
	public double readDegrees()
	{
		double value = ai.getAverageValue()/(Math.pow(2, (ai.getOversampleBits())));
		double degrees = (value-minimum)/conversion;
		return degrees;
	}
	
	/**
	 * reads the distance from an ir sensor
	 * @return the approximate distance in inches
	 */
	public double readDistance()
	{
		double value = ai.getAverageValue()/ Math.pow(2, ai.getOversampleBits());
//		double distance = (Math.log(value / 1421.9) / -0.24);
		double distance = (Math.log(value / index1) / index2);
		return distance;
	}
	
	/**
	 * @return
	 */
	public double readDistanceTest()
	{
		double value = ai.getAverageValue()/ Math.pow(2, ai.getOversampleBits());
		return value;
	}
}
