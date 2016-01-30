package org.usfirst.frc.team1891.drivesystem;
import java.util.LinkedList;
import org.usfirst.frc.team1891.filewriter.*;
import org.usfirst.frc.team1891.joysticks.JoyVector;

import edu.wpi.first.wpilibj.Timer;

/**
 * This class controls the drive system as a whole for the robot. There are a few configuration methods that should
 * be called but after configuration it is just a matter of calling drive() every iteration.
 * @author Tyler Manning
 *
 */
public class DriveSystem {

	private static LinkedList<MotorAndSide> motorList= null;
	private static double rampRate=0.1;
	LogWriter log = new LogWriter();
	Timer rampTime = new Timer();//Timer used for the rampRate. 
	private static int rightSideReverse=1;//Set to negative one if the right side needs negative voltage.
	private static int leftSideReverse=-1;//Set to positive one if the left side needs positive voltage.
	/**
	 * Enumeration with all the different drive system.
	 *Can be easily expanded for more use. Commented drive systems are not in use.
	 *You will also need to expand the set drive system method if more drive systems are added.
	 * @author Tyler
	 *
	 */
	public enum driveModes {
		/**
		 *The tank drive system.
		 */
		TANK_DRIVE,
		TANK_DRIVE_PID
		//			MECHNINUM_DRIVE,
		//			OMNI_DRIVE,
		//			TWO_WHEEL_DRIVE
	};

	private driveModes currentDrive;

	/**
	 * The input of all motors on the current system, ensure that all motors have been instantiated
	 * @param motorList a list of ALL motors on the current system.
	 */
	public DriveSystem(LinkedList<MotorAndSide> motorList){
		this.motorList=motorList;
	}

	/**
	 * This method is used to set the rampRate for a specific robot, it is on a scal of 0-1.
	 * The ramp rate is used to control how fast the motor speeds up my multiplying joystick input by a scalar.
	 * As time goes on the ramp rate will eventually hit 100, so the set ramp rate is just the initial value of the ramp rate.
	 * @param rampRate should be used to set the ramp rate for a specific robot.
	 * @throws InvalidRampRateException when the input is not between 0.0 and 1.0
	 */
	public void setRampRate(double rampRate) throws InvalidRampRateException {
		if(rampRate>1.0){throw new InvalidRampRateException();}
		this.rampRate = rampRate;
	}

	/**
	 * Sets the drive system for the current instance of the robot.
	 * @param drive the drive system of the robot.
	 */
	public void setDriveSystem(driveModes drive){
		switch(drive){
		case TANK_DRIVE:
			log.appendMessageToLog("Tank drive choosen");
			currentDrive=driveModes.TANK_DRIVE;
			break;
		case TANK_DRIVE_PID:
			log.appendMessageToLog("Tank drive with PID choosen");
			currentDrive=driveModes.TANK_DRIVE_PID;
			break;
		}
	}

	/**
	 * Advances the drive system given a vector from org.usfirst.frc.team1891.joysticks.
	 * This method should be used primarily in telop as autonomous drive will operate differently.
	 * @param vec the vector to drive on.
	 */
	public void drive(JoyVector vec){
		switch(currentDrive){
		case TANK_DRIVE://Copy case statements for different drive systems.
			driveTankDrive(vec);
			break;
		case TANK_DRIVE_PID:
			driveTankDrivePID(vec);
			break;
		default:
			break;
		}
	}

	/**
	 * Method to configure the inverseness of the right side of the robot.
	 * @param invrt true if the right side needs negative voltage, false if positive voltage.
	 */
	public void setRightSideInverse(boolean invrt){
		if(invrt){
			rightSideReverse=-1;
		}else{
			rightSideReverse=1;
		}
	}

	/**
	 * Method to configure the inverseness of the right side of the robot.
	 * @param invrt true if the left side needs negative voltage, false if positive voltage.
	 */
	public void setLeftSideInverse(boolean invrt){
		if(invrt){
			leftSideReverse=-1;
		}else{
			leftSideReverse=1;
		}
	}

	private static void driveTankDrive(JoyVector vec){
		double rightSideSet=rightSideReverse*rampRate*vec.getY_comp()*computeThetaScalar("RIGHT", vec)*12;
		double leftSideSet=leftSideReverse*rampRate*vec.getY_comp()*computeThetaScalar("LEFT", vec)*12;
		//		System.out.println("Right Side Power "+ rightSideSet);
		//		System.out.println("Left Side Power "+ leftSideSet);
		//		double rightSideSet=rightSideReverse*rampRate*vec.getY_comp()*12;
		//		double leftSideSet=leftSideReverse*rampRate*vec.getY_comp()*12;
		for(MotorAndSide m: motorList){
			if(m.jag!=null){
				if(m.side.equals("RIGHT")){
					m.getJag().setVoltage(rightSideSet);
				}else{
					m.getJag().setVoltage(leftSideSet);
				}
			}else if(m.talonSRX!=null){
				//TODO: talon code.
				if(m.side.equals("RIGHT")){

				}else{

				}
			}
		}
		//TODO deal with rampRate
	}

	private static double computeThetaScalar(String side, JoyVector vec) {
		//		double angle = vec.getAngle(); TODO tell josh to calculate the error.
		double angle = Math.atan2(vec.getY_comp(), vec.getX_comp());
		angle=-1*Math.toDegrees(angle);
		System.out.println(angle);
		if(side.equals("RIGHT")){//return a value for the right side.
			if(angle>=0 && angle<=90){//Quadrant 1
				angle=(angle/90);
				return angle;
			}else if(angle>=90 && angle<=180){//Quadrant 2
				angle=angle-90;
				angle=(angle/90);
				return (1-angle);
			}else if(angle >=-180 && angle <=-90){//Quadrant 3
				
			}else{//Quadrant 4
				
			}
		}else if(side.equals("LEFT")){//return a value for the left side.
			if(angle>=0 && angle<=90){//Quadrant 1
				angle=(angle/90);
				return (1-angle);
			}else if(angle>=90 && angle<=180){//Quadrant 2
				angle=angle-90;
				angle=(angle/90);
				return (angle);
			}else if(angle >=-180 && angle <=-90){//Quadrant 3
				
			}else{//Quadrant 4
				
			}
		}
		return 0;
	}

	private static void driveTankDrivePID(JoyVector vec) {

	}

	/**
	 * Enables all the motors in their correct mode.
	 */
	public void enableAll(){
		//TODO: enable all types of motors and in the correct mode.
		for(MotorAndSide m : motorList){
			m.getJag().initVoltage();
		}
	}

}