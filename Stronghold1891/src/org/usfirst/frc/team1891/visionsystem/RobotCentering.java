package org.usfirst.frc.team1891.visionsystem;

import org.usfirst.frc.team1891.joysticks.JoyVector;

/**
 * This class is used for robot centering based on the vision processing input.
 * @author Tyler
 *
 */
public class RobotCentering {
	private double optimalWidth;
	private double optimalHeight;
	private double currentWidth;
	private double currentHeight;
	private double speed=0.3;
	private double optimalCenterX;
	private double currentCenterX;
	private VisionProcessing vp = new VisionProcessing();
	
	
	/**
	 * Constructor for RobotCentering. If you do not know what the values should be, place the robot 
	 * in the exact position that you would like it to drive to. Then use the GRIP GUI to find the values that you want.
	 * If the values change between testing cycles make sure that the camera is in a fixed position as well as the target.
	 * @param optimalWidth set this value to the optimal width input to be centered on.
	 * @param optimalHeight set this value to the optimal height input to be centered on.
	 * @param optimalCenterX set this value to the optimal center X input to be centered on.
	 */
	public RobotCentering(double optimalWidth, double optimalHeight, double optimalCenterX){
		this.optimalWidth=optimalWidth;
		this.optimalHeight=optimalHeight;
		this.optimalCenterX=optimalCenterX;
	}
	
	/**
	 * Returns an emulated joystick value based on where the robot should be turning so that it centered on the 
	 * optimal values.
	 * @return the JoyVector where the robot should drive.
	 */
	public JoyVector centerRobot(){
		currentWidth=vp.getWidth();
		currentHeight=vp.getHeight();
		currentCenterX=vp.getCenterX();
		
		
		double yAxisTurnAmount;
		double xAxisTurnAmount;
		
		
		
		
		if(currentWidth == 0){// If the robot does not see a target, it needs to spin around until it does.
			return new JoyVector(0, speed, 0, 0);
		}
		
		if((optimalHeight-currentHeight) <= (0.1*optimalHeight)){//If the current height is within 10% of the optimal height, good enough
			yAxisTurnAmount=0;
		}else{
			if(currentHeight>optimalHeight){//To close
				yAxisTurnAmount=-speed;
			}else{//to far
				yAxisTurnAmount=speed;
			}
		}
		
		if((optimalWidth-currentWidth) <= (0.1*optimalWidth)){//If the current width is within 10% of the optimal width, good enough
			xAxisTurnAmount=0;
		}else{
			if(currentCenterX> optimalCenterX){//to far right
				xAxisTurnAmount=-speed;
			}else{//to far left
				xAxisTurnAmount=speed;
			}
		}
		
		
		
		return new JoyVector(yAxisTurnAmount, xAxisTurnAmount,0,0);
	}
}
