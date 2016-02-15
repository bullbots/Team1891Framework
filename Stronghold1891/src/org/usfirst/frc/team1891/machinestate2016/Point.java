package org.usfirst.frc.team1891.machinestate2016;

/**
 * Since the Roborio throws a NoClassDefFound error when I try to use the default java api
 * Point class, here is my own point class;
 * @author Tyler
 *
 */
public class Point {
	int x;
	int y;
	/**
	 * Constructor
	 * @param x coord
	 * @param y coord
	 */
	public Point(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	/**
	 * @return the x coord
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * @return the y coord
	 */
	public int getY(){
		return this.y;
	}
	
	public String toString(){
		return "["+x+","+y+"]";
	}
}
