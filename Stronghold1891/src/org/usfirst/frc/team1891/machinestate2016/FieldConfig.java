package org.usfirst.frc.team1891.machinestate2016;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
	int fieldX = 351;
	int fieldY = 319;
	char[][] field;
	
	/**
	 * 
	 */
	public FieldConfig()
	{
//		fl = new File("/home/lvuser/fieldcsv/field.csv");
		fl = new File("/c:/Users/408robot.MVHS-ROBOT-LT/workspace/Stronghold/Stronghold1891/fieldcsv/field.csv");
		try {
			write = new FileWriter(fl);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			read = new BufferedReader(new FileReader(fl));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		field = new char[fieldX][fieldY];
		try {
			for(int i=0;((line = read.readLine())) != null ;i++ ) {
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
		}
		found = false;
//		dash = new SmartDashboard();
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
				System.out.println("success");
			}
			if (found == false)
			{
				x++;
				if (x > 350)
				{
					x = 1;
					y++;
				}
				System.out.println("fail");
			}
			if (y > 318)
			{
				System.out.println("none found");
				found = true;
			}
		}
		
//		int offset = x + y*351;
//		try {
//			write.write("M", offset, 1);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		previousX = line.indexOf("M");
//		double xMeasure = dash.getNumber("dkdj");
//		double yMeasure = dash.getNumber("s");
//		double x = 250 + xMeasure;
//		double y = yMeasure;
//		int offset = (int) (x + y*351);
//		try {
//			file.write("M", offset, 1);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
