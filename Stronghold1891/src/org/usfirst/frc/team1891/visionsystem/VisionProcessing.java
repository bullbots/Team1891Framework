package org.usfirst.frc.team1891.visionsystem;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import org.usfirst.frc.team1891.filewriter.LogWriter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * @author Zach
 *
 */
public class VisionProcessing 
{
	private final static String[] GRIP_ARGS = new String[] {
	        "/usr/local/frc/JRE/bin/java", "-jar",
	        "/home/lvuser/grip.jar", "/home/lvuser/project.grip" };
	private final NetworkTable grip = NetworkTable.getTable("grip");
	NetworkTable table;
	LogWriter log;
	LinkedList<Double> centerXAverage = new LinkedList<Double>();
	/**
	 * Instantiates the LogWriter and the NeworkTable into the environmet
	 */
	public VisionProcessing()
	{
//		try {
//            Runtime.getRuntime().exec(GRIP_ARGS);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//		log = new LogWriter();
		table = NetworkTable.getTable("GRIP/targets");
	}
	/**
	 * @return the area of the object
	 */
	public double getArea()
	{
		double[] Area = new double[0];
		double[] areas = table.getNumberArray("area", Area);
		for(double area : areas)
		{
			return area;
			
		}
		return 0;
	}
	/**
	 * Outputs the height to the Riolog
	 * @return The height
	 */
	public double getHeight()
	{
		double[] height = new double[0];
		double [] heights = table.getNumberArray("height", height);
		for(double up : heights)
		{
			return up;
		}
		return 0;
		
	}
	/**
	 * Outputs the solidity to the Riolog
	 * @return The solidity
	 */
	public double getSolidity()
	{
		double[] solid = new double[0];
		double[] solidity = table.getNumberArray("solidity", solid);
		for(double solids : solidity)
		{
			return solids;
		}
		return 0;
    	
	}
	/**
	 * Outputs the YCenter to the Riolog
	 * @return The yCenter
	 */
	public double getCenterY()
	{
		double[] yCenter = new double[0];
		double[] yCenters = table.getNumberArray("centerY", yCenter);
		for(double centersY : yCenters)
		{
			return centersY;
		}
		return 0;
		
	}
	/**
	 *Outputs width to the Riolog 
	 * @return The width
	 */
	public double getWidth()
	{
		double[] width = new double[0];
		double[] widths = table.getNumberArray("width", width);
		for(double wid : widths)
		{
			return wid;
		}
		return 0;
	}
	/**
	 * Outputs the centerX to the Riolog
	 * @return The centerX
	 */
	public double getCenterX()
	{
		double[] getCenterX = new double[0];
		double avgTotal=0;
		double [] centerX = table.getNumberArray("centerX", getCenterX);
		for(double up : centerX)
		{
//			if(centerXAverage.size()<4){
//				centerXAverage.offer(up);
//			}else{
//				centerXAverage.poll();
//				centerXAverage.offer((Double)up);
//			}
//			for(int i=0; i<centerXAverage.size(); i++){
//				avgTotal=centerXAverage.get(i)+avgTotal;
//			}
//			return (avgTotal/centerXAverage.size());
			return up;
		}
		return 0;
	}
	/**
	 * Outputs all data to the Riolog
	 */
	public void outputData()
	{
		
		double[] Area = new double[0];
    	double[] yCenter = new double[0];
    	double[] height = new double[0];
    	double[] solid = new double[0];
    	double[] width = new double[0];
    	double[] xCenter = new double[0];
    	
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
    		
    		double [] heights = table.getNumberArray("height", height);
    		System.out.print("height: ");
    		for(double up : heights)
    		{
    			System.out.print(up + " ");
    		}
    		System.out.println();
    		Timer.delay(1);
    		
    		double[] solidity = table.getNumberArray("solidity", solid);
    		System.out.print("Solidity: ");
    		for(double solids : solidity)
    		{
    			System.out.print(solids + " ");
    		}
    		System.out.println();
    		Timer.delay(1);
    		
    		double[] widths = table.getNumberArray("width", width);
    		System.out.print("Width: ");
    		for(double wid : widths)
    		{
    			System.out.print(wid + " ");
    		}
    		System.out.println();
    		Timer.delay(1);
    		
    		double[] xCenters = table.getNumberArray("xCenter", xCenter);
    		System.out.print("xCenter: ");
    		for(double x : xCenters)
    		{
    			System.out.print(x + " ");
    		}
    		System.out.println();
    		Timer.delay(1);
	}

}
