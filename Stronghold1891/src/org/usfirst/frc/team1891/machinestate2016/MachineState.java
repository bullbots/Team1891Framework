package org.usfirst.frc.team1891.machinestate2016;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import org.usfirst.frc.team1891.filewriter.LogWriter;

/**
 * A class that controls the state of the machine.
 * @author Tyler
 *
 */
public class MachineState{
	//0. Find crossable defense
	//1. Route path to defense
	//2. Traverse to defense
	//3. Cross defense
	//4. Fine goal target
	//5. Move to shooting position
	//6. Fire at target.
	//7. Look for rebound and begin moving back.
	private int fieldX=351;//inches
	private int fieldY=319;//inches
	private int stateNum=0;
	private final int SHIFT_AMOUNT=23;//To be used in path validation
	private char[][] field= new char[fieldX][fieldY];
	private Point startingPosition=null;
	private Point shootingPosition=null;
	private String path="/home/lvuser/fieldcsv/field.csv";//"C:\\Users\\Tyler\\OneDrive\\FIRST\\Stronghold\\Stronghold1891\\fieldcsv\\field.csv";
	private LinkedList<Point> solutionPath = new LinkedList<Point>();//The solution path once it is found.
	private LinkedList<Point> lineThroughShooting = new LinkedList<Point>();//A line of points through the shooting position
	private LinkedList<Point> lineThroughStarting = new LinkedList<Point>();//A line of points through the starting position
	private LinkedList<Point> driveLine = new LinkedList<Point>();//A line that connects the shooting line and the starting line.
	/**
	 *0. Find crossable defense, robot should be looking for closest possible defense.
	 *1. Route path to defense, while the crossable defense can be found robot should be routing a path to the defense.
	 *2. Traverse to defense, while there is still a viable path to the defense, move to the defense.
	 *3. Cross defense, robot should be crossing the defense, at this point the state of the machine should not reduce.
	 *4. Find goal target, robot should be looking for the nearest and easiest goal to shoot into.
	 *5. Move to shooting position, while the robot can still find the target goal it should be moving into shooting position.
	 *6. Fire at target, robot should fire the ball.
	 *7. Look for rebound and begin moving back, at this point the robot should scan the field for a missed shot, if not it should return.
	 * @return the number of the current state
	 * @throws InvalidStateException when the state of the machine was invalid
	 */
	public int getState() throws InvalidStateException{
		LogWriter fw = new LogWriter();
		fw.appendErrorToLog("Current State: "+stateNum);
		if(stateNum<0 || stateNum>7){throw new InvalidStateException();}
		return stateNum;
	}

	/**
	 * This method should be called to update the current state of the machine.
	 */
	public void update(){
		switch(stateNum){
		case 0://Finding crossable defense
			break;
		case 1:// Routing to defense
			break;
		case 2://Moving to defense
			break;
		case 3://Crossing defense
			break;
		case 4://Finding goal target.
			break;
		case 5://Moving to shooting position
			break;
		case 6://Firing at target
			break;
		case 7://End autonomous 
			break;
		}
	}

	/**
	 *Initializes the machine, must be called before states will be valid.
	 */
	public void initiateMachine(){
		BufferedReader br = null;
		String line = " ";
		try {
			//Read in field config file
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			for(int i=0;((line = br.readLine())) != null ;i++ ) {
				line=line.trim();
				line=line.replaceAll("	", "");
				line=line.replace(",", "");
				char[] charset = line.toCharArray();
				for(int j=0; j<fieldX ;j++){
					field[j][i]=charset[j];
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Efficiently finds the sortest path to the shooting position, to be run in initialization code.
	 */
	public void findShotestPath(){
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: "+(startTime/1000));
		//W== wall, robot cannot go over and should not be within 12 cells of the R
		//G== goal tower
		//R==ramps
		//C==crossable defenses
		//F==floor
		//M==Robot
		//S==near shooting position.
		//General algorithm
		//Start where the R character is found, move position up one in all four directions if valid position add it to a list
		//of points and continue until a point reaches the postion S.
		//Valid positions include: positions that are not within 16 characters of a W, a point is not a redundant point in the list, as
		//this would create an infinite loop of going back and forth and would crush efficiency. The path must also not contain more that 4 turns.
		//This is a brute force algorithm.		

		//Find starting position
		for(int i=0; i<fieldX && startingPosition ==null; i++){
			for(int j=0;j<fieldY && startingPosition ==null; j++){
				if( field[i][j] == 'M'){
					startingPosition = new Point(i, j);
				}
			}

		}

		//Find shooting position
		for(int i=0; i<fieldX && shootingPosition ==null; i++){
			for(int j=0;j<fieldY && shootingPosition ==null; j++){
				if( field[i][j] == 'S'){
					shootingPosition = new Point(i, j);

				}
			}
		}
		
		//Create shooting line
		for(int i=0; i<fieldY; i++){
			lineThroughShooting.add(new Point(shootingPosition.getX(), i));
		}
		
		//Create starting line
		for(int i=0; i<fieldY; i++){
			lineThroughStarting.add(new Point(startingPosition.getX(), i));
		}
		
		//Create first connection line
		for(int i=0; i<Math.abs((shootingPosition.getX()-startingPosition.getX())); i++){
			driveLine.add(new Point(i+shootingPosition.getX(), fieldY-1));
		}
		
		
		//Finding the actual path
		boolean pathFound=false;
		while(pathFound!=true){
			if(validatePath(driveLine)){
				//Yay, good path
				pathFound=true;
				removeAllUselessPath();
			}else{
				//bad path, try again.
				for(int i=0; i<driveLine.size(); i++){
					driveLine.get(i).y--;
				}
			}
		}
		
		
		
//		for(int i=0; i<fieldY; i++){
//			for(int j=0; j<fieldX; j++){
//				boolean path=false;
//				for(int x=0; x<solutionPath.size();x++){
//					if(solutionPath.get(x).getX() == j && solutionPath.get(x).getY() ==i &&field[j][i]!='S' &&field[j][i]!='M'){
//						System.out.print('T');
//						path=true;
//					}
//				}
//				if(!path){
//					System.out.print(field[j][i]);
//				}
//			}
//			System.out.println();
//		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total runtime: "+totalTime);
	}
	
	private void removeAllUselessPath() {
		//Trim shooting line
		
		if(driveLine.getFirst().getY() > shootingPosition.getY()){
			//If the drive line is above, north
			for(int i=0; i< lineThroughShooting.size();i++){
				if(lineThroughShooting.get(i).getY() < shootingPosition.getY()){
					lineThroughShooting.remove(i);
					i--;
				}else if(lineThroughShooting.get(i).getY() > driveLine.getFirst().getY()){
					lineThroughShooting.remove(i);
					i--;
				}
			}
		}else if(driveLine.getFirst().getY() < shootingPosition.getY()){
			//If the drive line is below, south
			for(int i=0; i< lineThroughShooting.size();i++){
				if(lineThroughShooting.get(i).getY() > shootingPosition.getY()){
					lineThroughShooting.remove(i);
					i--;
				}else if(lineThroughShooting.get(i).getY() < driveLine.getFirst().getY()){
					lineThroughShooting.remove(i);
					i--;
				}
			}
		}else{
			//If they are on the same level
			lineThroughShooting.clear();
		}
		
		//Trim starting line
		if(driveLine.getFirst().getY() > shootingPosition.getY()){
			//If the drive line is above, north
			for(int i=0; i< lineThroughStarting.size();i++){
				if(lineThroughStarting.get(i).getY() < startingPosition.getY()){
					lineThroughStarting.remove(i);
					i--;
				}else if(lineThroughStarting.get(i).getY() > driveLine.getFirst().getY()){
					lineThroughStarting.remove(i);
					i--;
				}
			}
		}else if(driveLine.getFirst().getY() < shootingPosition.getY()){
			//If the drive line is below, south
			for(int i=0; i< lineThroughStarting.size();i++){
				if(lineThroughStarting.get(i).getY() > startingPosition.getY()){
					lineThroughStarting.remove(i);
					i--;
				}else if(lineThroughStarting.get(i).getY() < driveLine.getFirst().getY()){
					lineThroughStarting.remove(i);
					i--;
				}
			}
		}else{
			//If they are on the same level
			lineThroughShooting.clear();
		}
		
		appendPathsToSolutionPath();
		
	}

	private void appendPathsToSolutionPath() {
		for(int i=0; i<lineThroughShooting.size();i++){
			solutionPath.add(lineThroughShooting.get(i));
		}
		
		for(int i=0; i<lineThroughStarting.size();i++){
			solutionPath.add(lineThroughStarting.get(i));
		}
		
		for(int i=0; i<driveLine.size();i++){
			solutionPath.add(driveLine.get(i));
		}
		
	}

	private boolean validatePath(LinkedList<Point> path){
		for(int i=0; i<path.size(); i++){
			try{
				for(int j=0; j<SHIFT_AMOUNT;j++){
					if(field[(int) path.get(i).getX()][(int) (path.get(i).getY()+j)]=='W' ||
						field[(int) path.get(i).getX()][(int) (path.get(i).getY()-j)]=='W'){
						return false;
					}
				}
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println("test");
				return false;
			}
		}
		
		return true;
		
	}
}
