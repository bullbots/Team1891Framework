package org.usfirst.frc.team1891.machinestate2016;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.text.AttributeSet.CharacterAttribute;

import edu.wpi.first.wpilibj.smartdashboard.*;;

/**
 * @author 408robot
 *
 */
public class FieldConfig {
	
	File fl;
	SmartDashboard dash;
	Scanner scan;
	BufferedReader read;
	FileWriter write;
	Boolean found;
	String line;
	int previousY;
	int previousX;
	int fieldX = 325;
	int fieldY = 319;
	char[][] field;
	/**
	 * The obstacle location at which we want to cross.
	 * number 1 is low bar, and it continues from there
	 */
	public static char targetObstacle;
	
	/**
	 * 
	 */
	public FieldConfig()
	{
		fl = new File("/home/lvuser/fieldcsv/field.csv");
		try {
			read = new BufferedReader(new FileReader(fl));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		field = new char[fieldX][fieldY];
		try {
			for(int i=0;i<fieldY;i++ ) {
				line=read.readLine();
				line=line.trim();
				line=line.replaceAll("	", "");
				line=line.replace(",", "");
				char[] charset = line.toCharArray();
				for(int j=0; j<fieldX ;j++){
					field[j][i]=charset[j];
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		found = false;
//		SmartDashboard.getBoolean("your mom");
		try {
			SmartDashboard.putNumber("robotX",SmartDashboard.getNumber("robotX"));
		} catch (edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined e) {
			SmartDashboard.putNumber("robotX",0);
		}
		try {
			SmartDashboard.putNumber("robotY",SmartDashboard.getNumber("robotY"));
		} catch (edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined e) {
			SmartDashboard.putNumber("robotY",0);
		}
		try {
			SmartDashboard.putString("crossingTarget",SmartDashboard.getString("crossingTarget"));
		} catch (edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined e) {
			SmartDashboard.putString("crossingTarget","0");
		}
		
	}

	/**
	 * 
	 */
	public void configureRobotStartPosition()
	{
		int x = 0;
		int y = 0;
		while (found == false)
		{
			if (field[x][y] == 'M')
			{
				found = true;
			}
			if (found == false)
			{
				x++;
				if (x > 324)
				{
					x = 0;
					y++;
				}
			}
			if (y > 318)
			{
				System.out.println("no previous marker found found");
				found = true;
			}
		}
		
		field[x][y] = 'F';
		
		double xMeasure = 239 + SmartDashboard.getNumber("robotX");
		double yMeasure = SmartDashboard.getNumber("robotY");
//		double xMeasure = 300;
//		double yMeasure = 318;
		
		field[(int) xMeasure][(int) yMeasure] = 'M';
		
		try {
			write = new FileWriter(fl);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		}
		
		try {
			for (int t=0; t < fieldY; t++)
			{
				for (int l=0; l < fieldX; l++)
				{
					write.write(field[l][t]);
					write.write(',');
				}
				write.write(System.getProperty("line.separator"));
			}
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		targetObstacle = SmartDashboard.getString("crossingTarget").charAt(0);
	}
}
