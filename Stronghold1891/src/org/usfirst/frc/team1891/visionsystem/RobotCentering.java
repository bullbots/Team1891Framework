package org.usfirst.frc.team1891.visionsystem;

import org.usfirst.frc.team1891.joysticks.JoyVector;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is used for robot centering based on the vision processing input.
 * @author Tyler
 *
 */
public class RobotCentering {
	private double optimalHeight;
	private double optimalCenterX;
	CameraPIDWidth cPID;
	ControllerOutputPIDWidth controlOutWidth;
	PIDController centerControlWidth;
	CameraPIDDistance cPIDDist;
	ControllerOutputPIDDistance controlOutDistance;
	PIDController centerControlDistance;
	public static double resolutionX=320;
	public static double resolutionY=240;
	public static boolean isCentered=false;
	/**
	 * Constructor for RobotCentering. If you do not know what the values should be, place the robot 
	 * in the exact position that you would like it to drive to. Then use the GRIP GUI to find the values that you want.
	 * If the values change between testing cycles make sure that the camera is in a fixed position as well as the target.
	 * @param optimalHeight set this value to the optimal height input to be centered on.
	 * @param optimalCenterX set this value to the optimal center X input to be centered on.
	 */
	@SuppressWarnings("deprecation")
	public RobotCentering(double optimalCenterY, double optimalCenterX){
		this.optimalHeight=optimalHeight;
		this.optimalCenterX=optimalCenterX;
		{
			//Center X Axis
			cPID = new CameraPIDWidth(optimalCenterX);
			controlOutWidth= new ControllerOutputPIDWidth();
			centerControlWidth = new PIDController(0.385, 0.012,0, cPID, controlOutWidth);
			centerControlWidth.setTolerance(5);
			centerControlWidth.setInputRange(-1.0, 1.0);
			centerControlWidth.setOutputRange(-0.2,0.2);
			centerControlWidth.setContinuous();
			centerControlWidth.enable();
//			SmartDashboard.putData("PID width", centerControlWidth);
		}
		{
			//Center Y axis
			cPIDDist = new CameraPIDDistance(optimalCenterY);
			controlOutDistance= new ControllerOutputPIDDistance();
			centerControlDistance = new PIDController(0.5, 0.0000,0, cPIDDist, controlOutDistance);
			centerControlDistance.setInputRange(-1, 1);
			centerControlDistance.setOutputRange(-1,1);
			centerControlDistance.setContinuous();
			centerControlDistance.enable();

		}
	}

	public JoyVector centerRobot(){
		JoyVector retVect=null;
//		if(controlOutWidth.getTurnVector().getX_comp()>0){
//			retVect = new JoyVector(controlOutWidth.getTurnVector().getX_comp()+0.2, controlOutDistance.getTurnVector().getY_comp(),0,0);
//		
//		}else{
//			retVect = new JoyVector(controlOutWidth.getTurnVector().getX_comp()-0.2, controlOutDistance.getTurnVector().getY_comp(),0,0);
//		}
//		retVect= new JoyVector(0,controlOutDistance.getTurnVector().getY_comp(),0,0);


		if(controlOutWidth.getTurnVector().getX_comp()>0){
			retVect = new JoyVector(controlOutWidth.getTurnVector().getX_comp()+0.2,0,0,0);
		
		}else{
			retVect = new JoyVector(controlOutWidth.getTurnVector().getX_comp()-0.2, 0,0,0);
		}
		
		
		return retVect;
	}

	public void setPIDVals(double p, double i, double d){
		centerControlWidth.setPID(p, i, d);
	}

}
