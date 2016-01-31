package org.usfirst.frc.team1891.visionsystem;

import org.usfirst.frc.team1891.filewriter.LogWriter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * @author Zach
 *
 */
public class VisionProcessing 
{
	NetworkTable table;
	LogWriter log;
	/**
	 * 
	 */
	public VisionProcessing()
	{
		log = new LogWriter();
		table = NetworkTable.getTable("GRIP/targets");
	}
	/**
	 * @return
	 */
	public double getArea()
	{
		double[] Area = new double[0];
    		double[] areas = table.getNumberArray("area", Area);
    		System.out.print("Areas: ");
    		for(double area : areas)
    		{
    			log.appendMessageToLog(area + " ");
    			return area;
    		}
			return 0;
	}
	/**
	 * @return
	 */
	public double getHeight()
	{
		double[] Height = new double[0];
		
    		double[] heights = table.getNumberArray("height", Height);
    		System.out.print("Height: ");
    		for(double height : heights)
    		{
    			log.appendMessageToLog(height + " ");
    			return height;
    		}
			return 0;
	}
	/**
	 * @return
	 */
	public double getSolidity()
	{
		double[] Solid = new double[0];
		
    		double[] solids = table.getNumberArray("Solidity", Solid);
    		System.out.print("Solidity: ");
    		for(double solid : solids)
    		{
    			log.appendMessageToLog(solid + " ");
    			return solid;
    		}
			return 0;
    	
	}
	/**
	 * @return
	 */
	public double getCenterY()
	{
		double[] CenterY = new double[0];
    		double[] centersY = table.getNumberArray("CenterY", CenterY);
    		System.out.print("CenterY: ");
    		for(double center : centersY)
    		{
    			log.appendMessageToLog(center + " ");
    			return center;
    		}
			return 0;	
	}
	public double getWidth()
	{
		double[] Width = new double[0];
    		double[] widths = table.getNumberArray("width", Width);
    		System.out.print("Width: ");
    		for(double width : widths)
    		{
    			log.appendMessageToLog(width + " ");
    			return width;
    		}
			return 0;
	}
	public double getCenterX()
	{
		double[] CenterX = new double[0];
		double[] centersX = table.getNumberArray("centerX", CenterX);
		System.out.print("CenterX: ");
		for(double centerX : centersX)
		{
			log.appendMessageToLog(centerX + " ");
			return centerX;
		}
		return 0;
	}
	
	
	public void outputData()
	{
		
		double[] Area = new double[0];
    	double[] yCenter = new double[0];
    	while (true)
    	{
    		double[] areas = table.getNumberArray("area", Area);
    		System.out.print("Areas: ");
    		for(double area : areas)
    		{
    			System.out.print(area + " ");
    		}
    		System.out.println();
    		Timer.delay(1);
    		double[] yCenters = table.getNumberArray("centerY", yCenter);
    		System.out.print("centerY: ");
    		for(double centersY : yCenters)
    		{
    			System.out.print(centersY + " ");
    		}
    		System.out.println();
    		Timer.delay(1);
    	}
	}

}
