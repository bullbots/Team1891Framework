package org.usfirst.frc.team1891.drivesystem;
import java.util.LinkedList;
import org.usfirst.frc.team1891.filewriter.*;
import org.usfirst.frc.team1891.joysticks.JoyVector;

import edu.wpi.first.wpilibj.Timer;

/**
 * @author Egan Schafer, Tyler Manning
 *
 */
public class DriveSystem {
	
	private static LinkedList<MotorAndSide> motorList= null;
	private double rampRate=0;
	LogWriter log = new LogWriter();
	Timer rampTime = new Timer();//Timer used for the rampRate. 
	private int rightSideReverse=1;//Set to negative one if the right side needs negative voltage.
	private int leftSideReverse=-1;//Set to positive one if the left side needs positive voltage.
	/**
	 * @author Tyler
	 *Enumeration with all the different drive system.
	 *Can be easily expanded for more use. Commented drive systems are not in use.
	 *You will also need to expand the set drive system method if more drive systems are added.
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
		for(MotorAndSide m: motorList){
			if(m.jag!=null){
				m.getJag();
			}else if(m.talonSRX!=null){
				
			}
		}
	}
	
	private static void driveTankDrivePID(JoyVector vec) {
		// TODO Auto-generated method stub
		
	}
	
}