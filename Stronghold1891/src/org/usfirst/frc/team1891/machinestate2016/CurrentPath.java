package org.usfirst.frc.team1891.machinestate2016;

import java.util.ArrayList;

/**
 * A class to represent a path in the field
 * @author Tyler
 *
 */
public class CurrentPath implements Cloneable{
	private int numOfTurns=0;
	private ArrayList<Point> lst= new ArrayList<Point>();
	private final int SHIFT_AMOUNT=26;
	private final int MAX_NUM_OF_TURNS=2;


	/**
	 * Constructor of a CurrentPath to copy
	 * @param lst the list to copy
	 * @param numOfTurns the number of turns to copy.
	 */
	public CurrentPath (ArrayList<Point> lst, int numOfTurns){
		this.numOfTurns=numOfTurns;
		this.lst=lst;
	}

	/**
	 * Default constructor
	 */
	public CurrentPath(){

	}

	/**
	 * @return The path that the robot should move on.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Point> getPath() {
		return (ArrayList<Point>) lst.clone();
	}

	/**
	 * Adds the potential point to the current path
	 * @param newPoint the new point to be added
	 * @param solutionPoint the point where a solution is found.
	 * @param field the field the robot will be playing in, in accordance with the MachineState input
	 * @return 0 If the move was bad, 1 if the move was good, and two if the point was a solution point.
	 */
	public int addPoint(Point newPoint, Point solutionPoint, char[][] field){

		//Pointless move, next advance is the same as the previous move.
		if(lst.size() > 0){
			if(newPoint.getX()==lst.get(lst.size()-1).getX() && newPoint.getY()==lst.get(lst.size()-1).getY()){
				return 0;
			}
		}
		
		//Test not within SHIFT_AMOUNT blocks of a W in all directions
		try{
			for(int i=0; i<SHIFT_AMOUNT;i++){
				if(field[(int) newPoint.getX()][(int) (newPoint.getY()+i)]=='W' ||
					field[(int) newPoint.getX()+i][(int) (newPoint.getY())]=='W' ||
					field[(int) newPoint.getX()][(int) (newPoint.getY()-i)]=='W' ||
					field[(int) newPoint.getX()-i][(int) (newPoint.getY())]=='W'){
					return 0;
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			return 0;
		}

		//Test to see if the point is already in the list
		for(Point p : lst){
			if(p.getX()==newPoint.getX() && p.getY() == newPoint.getY()){
				return 0;
			}
		}

		//Test to see if new point is on the same linear path as the rest of the points to reduce turning.
		if(lst.size() >= 2){
			Point first = lst.get(lst.size()-1);
			Point prevFirst = lst.get(lst.size()-2);
			if(first.getX() == prevFirst.getX()){
				//Linear along the X axis
				if(newPoint.getX()==first.getX()){
					//The new point is linear with the previous two points, no turn was taken, as long as the number of turns is less than MAX_NUM_OF_TURNS this is a valid move.
					lst.add(newPoint);
					return checkForSolutionPoint(newPoint, solutionPoint);
				}else{
					//The robot has taken a turn, increment the number of times turned, if the number is less than four, other wise its a bad move.
					if(numOfTurns<MAX_NUM_OF_TURNS){
						//Good move
						numOfTurns++;
						lst.add(newPoint);
						return checkForSolutionPoint(newPoint, solutionPoint);
					}else{
						//Bad move
						return 0;
					}

				}
			}else if(first.getY() == prevFirst.getY()){
				//Linear along the Y axis
				if(newPoint.getY()==first.getY()){
					//The new point is linear with the previous two points, no turn was taken, as long as the number of turns is less than MAX_NUM_OF_TURNS this is a valid move.
					lst.add(newPoint);
					return checkForSolutionPoint(newPoint, solutionPoint);
				}else{
					//The robot has taken a turn, increment the number of times turned, if the number is less than four, other wise its a bad move.
					if(numOfTurns<MAX_NUM_OF_TURNS){
						//Good move
						numOfTurns++;
						lst.add(newPoint);
						return checkForSolutionPoint(newPoint, solutionPoint);
					}else{
						//Bad move
						return 0;
					}	
				}
			}else{
				//Pointless move, means turned 90 degrees in less than 3 inches
				return 0;
			}
		}else{
			lst.add(newPoint);
			return checkForSolutionPoint(newPoint, solutionPoint);
		}
	}

	/**
	 * @return The number of turns the robot will have taken on this path.
	 */
	public int getNumOfTurns(){
		return numOfTurns;
	}

	/**
	 * 
	 * @param testPoint The point being added
	 * @param solutionPoint The shooting position
	 * @return 1 if not solution point 2 if solution point.
	 */
	private int checkForSolutionPoint(Point testPoint, Point solutionPoint){
		if(testPoint.getX()==solutionPoint.getX() && testPoint.getY()==solutionPoint.getY()){
			return 2;
		}
		return 1;

	}

	/**
	 * @return The point at the front of the path. or null if there is no head.
	 */
	public Point getHeadOfPath(){
		if(lst.size()==0){
			return null;
		}
		return lst.get(lst.size()-1);
	}
}
