package org.usfirst.frc.team1891.machinestate2016;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import org.usfirst.frc.team1891.filewriter.LogWriter;
import org.usfirst.frc.team1891.robot.Robot;
import org.usfirst.frc.team1891.visionsystem.RobotCentering;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;

/**
 * A class that controls the state of the machine.
 * @author Tyler
 *
 */
public class MachineState{
	//0. Find crossable defense
	//1. Route path to defense
	//2. First leg of course
	//3. Second leg of course
	//4. Third leg of course.
	//5. Find goal target
	//6. Fire at target.
	//7. Look for rebound and begin moving back.
	private int fieldX=325;//inches
	private int fieldY=319;//inches
	private int stateNum=0;//The current state number of the state machine.
	private final int SHIFT_AMOUNT=23;//To be used in path validation
	private char[][] field= new char[fieldX][fieldY];
	private Point startingPosition=null;//The starting position that the robot is in.
	private Point shootingPosition=null;//The shooting position, this is where we want to get to.
	private String path="/home/lvuser/fieldcsv/field.csv";//"C:\\Users\\Tyler\\OneDrive\\FIRST\\Stronghold\\Stronghold1891\\fieldcsv\\field.csv";
	private LinkedList<Point> solutionPath = new LinkedList<Point>();//The solution path once it is found.
	private LinkedList<Point> lineThroughShooting = new LinkedList<Point>();//A line of points through the shooting position
	private LinkedList<Point> lineThroughStarting = new LinkedList<Point>();//A line of points through the starting position
	private LinkedList<Point> driveLine = new LinkedList<Point>();//A line that connects the shooting line and the starting line.
	private boolean pathFound=false;// True when the path is found from the starting postion to the shooting position.
	private static Timer stateTimer = new Timer();//The state timer used to find distance
	private double robotSpeed=55; //robot speed for the 2016 robot in inches per second.
	private boolean turnRight=false;//True if the robot needs to turn right during state 3 and 4
	private boolean turnLeft=false;//True if the robot needs to turn left during state 3 and 4
	AHRS nav = new AHRS(SPI.Port.kMXP);// The navx that is used to find the angle
	private double angleOffSet=5;//Used to offset the angle that the nav finds, because the robot keeps coasting after told to stop.
	
	
	private boolean stateTwoInitialized=false;//True when state two has been initialized
	private double stateTwoRunTime=0;//The total runtime of state2, used mainly for debugging purposes
	
	private boolean stateThreeInitialized=false;//True when state three has been initialized
	private double stateThreeRunTime;//The total runtime of state3, used mainly for debugging purposes
	private boolean stateThreeDoneTurning=true;//True when the robot is done turning
	private double stateThreeAngleToAchieve;//The angle that the robot needs to turn to.
	private boolean stateThreeTimerStarted=false;//True when the time has been started for this state.
	
	private boolean stateFourInitialized=false;//True when state four has been initialized
	private double stateFourRunTime;//The total runtime of state4, used mainly for debuggin purposes.
	private boolean stateFourDoneTurning=true;//True when the robot is done turning.
	private double stateFourAngleToAchieve;//The angle that the robot needs to turn to.
	private boolean stateFourTimerStarted=false;//True when the time has been started for this state.
	/**
	 *0. Find crossable defense, robot should be looking for closest possible defense.
	 *1. Route path to defense, while the crossable defense can be found robot should be routing a path to the defense.
	 *2. Runs the first leg of the course.
	 *3. Runs the second leg of the course, if the robot is at a defese, slow down.
	 *4. Runs the last leg of the course.
	 *5. Find goal target, robot should be looking for the nearest and easiest goal to shoot into.
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
			stateNum++;
			break;
		case 1:// Routing to defense
			if(pathFound){
				stateNum++;
			}
			break;
		case 2://First leg of course
			if(!stateTwoInitialized){
				stateTimer.stop();
				stateTimer.reset();
				stateTwoRunTime=(lineThroughStarting.size()/robotSpeed);
				stateTwoInitialized=true;
				stateTimer.start();
			}
			if(stateTimer.get() >=stateTwoRunTime){
				stateTimer.stop();
				stateTimer.reset();
				stateNum++;
				stateTwoInitialized=false;
			}
			break;
		case 3://Second leg of course
			if(!stateThreeInitialized){
				stateTimer.stop();
				stateTimer.reset();
				stateThreeRunTime=(driveLine.size()/robotSpeed);
				stateThreeInitialized=true;
				turnThatRobotStateThree();
			}
			if(turnRight){
				if(nav.getAngle()>=stateThreeAngleToAchieve){
					turnRight=false;
					stateTimer.start();
					stateThreeDoneTurning=true;
					stateThreeTimerStarted=true;
				}
			}
			
			if(turnLeft){
				if(nav.getAngle()<=stateThreeAngleToAchieve){
					turnLeft=false;
					stateTimer.start();
					stateThreeDoneTurning=true;
					stateThreeTimerStarted=true;
				}
			}
			if(stateThreeDoneTurning && !stateThreeTimerStarted){
				stateTimer.start();
				stateThreeTimerStarted=true;
			}
			
			if(stateTimer.get() >=stateThreeRunTime){
				stateTimer.stop();
				stateTimer.reset();
				stateNum++;
				stateThreeInitialized=false;
			}
			
			break;
		case 4://Last leg of course.
			if(!stateFourInitialized){
				stateTimer.stop();
				stateTimer.reset();
				stateFourRunTime=(lineThroughShooting.size()/robotSpeed);
				stateFourInitialized=true;
				turnThatRobotStateFour();
			}
			
			if(turnRight){
				if(nav.getAngle()>=stateFourAngleToAchieve){
					turnRight=false;
					stateTimer.start();
					stateFourTimerStarted=true;
				}
			}
			
			if(turnLeft){
				if(nav.getAngle()<=stateFourAngleToAchieve){
					turnLeft=false;
					stateTimer.start();
					stateFourTimerStarted=true;
				}
			}
			
			if(stateFourDoneTurning && !stateFourTimerStarted){
				stateTimer.start();
				stateFourTimerStarted=true;
			}
			
			if(stateTimer.get() >=stateFourRunTime){
				stateTimer.stop();
				stateTimer.reset();
				stateNum++;
				stateFourInitialized=false;
			}
			break;
		case 5://Finding goal target.
			if(RobotCentering.isCentered){
				stateNum++;
			}
			break;
		case 6://Firing at target
			if(Robot.autoBallFired){
				stateNum++;
			}
			break;
		case 7://End autonomous 
			break;
		}
	}

	private void turnThatRobotStateThree() {
		double currentAngle=nav.getAngle();
		turnRight=false;
		turnLeft=false;
		
		if(startingPosition.getY()<driveLine.getLast().getY()){//Turn Left
			stateThreeAngleToAchieve=currentAngle+(90-angleOffSet);
			turnRight=true;
			stateThreeDoneTurning=false;
		}else if(startingPosition.getY()>driveLine.getLast().getY()){
			stateThreeAngleToAchieve=currentAngle-(90-angleOffSet);
			turnLeft=true;
			stateThreeDoneTurning=false;
		}
	}
	
	private void turnThatRobotStateFour() {
		double currentAngle=nav.getAngle();
		turnRight=false;
		turnLeft=false;
		
		if(startingPosition.getY()<driveLine.getLast().getY()){//Turn Left
			stateFourAngleToAchieve=currentAngle+(90-angleOffSet);
			turnRight=true;
			stateFourDoneTurning=false;
		}else if(startingPosition.getY()>driveLine.getLast().getY()){
			stateFourAngleToAchieve=currentAngle-(90-angleOffSet);
			turnLeft=true;
			stateFourDoneTurning=false;
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
		//W== wall, robot cannot go over and should not be within 12 cells of the R
		//G== goal tower
		//R==ramps
		//1-5= various defenses, the first position is the low bar, the other positions increment from there.
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
				this.pathFound=true;
				removeAllUselessPath();
			}else{
				//bad path, try again.
				for(int i=0; i<driveLine.size(); i++){
					driveLine.get(i).y--;
				}
			}
		}

		//		for(int i=0; i<fieldX; i++){
		//			for(int j =0; j<fieldY; j++){
		//				boolean foundOne=false;
		//				for(int x=0; x<solutionPath.size() &&!foundOne; x++){
		//					if(solutionPath.get(x).getX() ==i && solutionPath.get(x).getY()==j){
		//						foundOne=true;
		//					}
		//				}
		//				if(foundOne){
		//					System.out.print('T');
		//				}else{
		//					System.out.print(field[i][j]);
		//				}
		//			}
		//			System.out.println();
		//		}
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

		for(int i=0; i<lineThroughStarting.size();i++){
			solutionPath.add(lineThroughStarting.get(i));
		}

		for(int i=0; i<driveLine.size();i++){
			solutionPath.add(driveLine.get(i));
		}

		for(int i=0; i<lineThroughShooting.size();i++){
			solutionPath.add(lineThroughShooting.get(i));
		}


	}

	private boolean validatePath(LinkedList<Point> path){
		for(int i=0; i<path.size(); i++){
			try{
				for(int j=0; j<SHIFT_AMOUNT;j++){
					//Check for right obstacle
					boolean goodObstacle=true;
					switch((field[(int) path.get(i).getX()][(int) (path.get(i).getY())])){
					case 'R':
						goodObstacle=true;
						break;
					case 'F':
						goodObstacle=true;
						break;
					case '1':
						if('1'==FieldConfig.targetObstacle){
							goodObstacle=true;
						}else{
							goodObstacle=false;
						}
						break;
					case '2':
						if('2'==FieldConfig.targetObstacle){
							goodObstacle=true;
						}else{
							goodObstacle=false;
						}
						break;
					case '3':
						if('3'==FieldConfig.targetObstacle){
							goodObstacle=true;
						}else{
							goodObstacle=false;
						}
						break;
					case '4':
						if('4'==FieldConfig.targetObstacle){
							goodObstacle=true;
						}else{
							goodObstacle=false;
						}
						break;
					case '5':
						if('5'==FieldConfig.targetObstacle){
							goodObstacle=true;
						}else{
							goodObstacle=false;
						}
						break;
					}
					//If there is no wall north OR south AND goodObstacle
					if((field[(int) path.get(i).getX()][(int) (path.get(i).getY()+j)]=='W' ||
					   field[(int) path.get(i).getX()][(int) (path.get(i).getY()-j)]=='W') &&
					   goodObstacle)
					   {
						return false;
					}
				}
			}catch(ArrayIndexOutOfBoundsException e){
			
				return false;
			}
		}

		return true;

	}

	/**
	 * @return true if the robot should turn right
	 */
	public boolean isTurnRight() {
		return turnRight;
	}

	/**
	 * @return true if the robot should turn left.
	 */
	public boolean isTurnLeft() {
		return turnLeft;
	}
	
	/**
	 * @return the angle of the robot
	 */
	public double getAngle(){
		return nav.getAngle();
	}

}