package org.usfirst.frc.team1891.drivesystem;
import java.util.LinkedList;
import org.usfirst.frc.team1891.filewriter.*;
import org.usfirst.frc.team1891.joysticks.JoyVector;
import org.usfirst.frc.team1891.machinestate2016.Point;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class controls the drive system as a whole for the robot. There are a few configuration methods that should
 * be called but after configuration it is just a matter of calling drive() every iteration.
 * @author Tyler Manning
 *
 */
public class DriveSystem {

	private static LinkedList<MotorAndSide> motorList= null;
	private static double rampRate=1;
	private static double test=0;
	LogWriter log = new LogWriter();
	Timer rampTime = new Timer();//Timer used for the rampRate. 
	private double distancePerWheelRevolution=0; //The distance the robot travels per revolution of the wheel
	private static double handicapCompensation=0.4;
	private static LinkedList<Point> drivePath;
	private static boolean hasDrivePath=false;
	/**
	 * @return the distance moved per revolution of the wheel
	 */
	public double getDistancePerWheelRevolution() {
		return distancePerWheelRevolution;
	}

	/**
	 * @param distancePerWheelRevolution sets the distance the robot moves per revolution of the wheel.
	 */
	public void setDistancePerWheelRevolution(double distancePerWheelRevolution) {
		this.distancePerWheelRevolution = distancePerWheelRevolution;
	}

	//TODO: Involve these variables in the voltage equation.
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
		/**
		 * The tank drive system using PID control
		 */
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
		DriveSystem.motorList=motorList;
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
		DriveSystem.rampRate = rampRate;
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
	@SuppressWarnings("null")
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
	//	
	//	/**
	//	 * Dive method to be used into autonomous mode
	//	 * @param vec the vector to drive on
	//	 * @param path the path the robot should travel on, each input should be an inch that it should move.
	//	 */
	//	public void driveAuto(JoyVector vec){
	//		switch(currentDrive){
	//		case TANK_DRIVE://Copy case statements for different drive systems.
	//			driveTankDrive(vec);
	//			break;
	//		case TANK_DRIVE_PID:
	//			driveTankDrivePID(vec);
	//			break;
	//		default:
	//			break;
	//		}
	//	}

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

		//	    Get X and Y from the Joystick, do whatever scaling and calibrating you need to do based on your hardware.
		//	    Invert X
		//	    Calculate R+L (Call it V): V =(100-ABS(X)) * (Y/100) + Y
		//	    Calculate R-L (Call it W): W= (100-ABS(Y)) * (X/100) + X
		//	    Calculate R: R = (V+W) /2
		//	    Calculate L: L= (V-W)/2
		//	    Do any scaling on R and L your hardware may require.
		//	    Send those values to your Robot.
		//	    Go back to 1.
		double joyXInput=vec.getX_comp()*100;
		double joyYInput=vec.getY_comp()*100;
		double rightScal=((100-Math.abs(joyYInput))*(joyXInput/100)+joyXInput);
		double leftScal=((100-Math.abs(joyXInput))*(joyYInput/100)+joyYInput);
		double rightSideVoltageUnscaled=((rightScal+leftScal)/2);
		double leftSideVoltageUnscaled=((rightScal-leftScal)/2);
		double rightSideVoltage=((rightSideVoltageUnscaled*3)/25)*rampRate;
		double leftSideVoltage=((leftSideVoltageUnscaled*3)/25)*rampRate;
		for(MotorAndSide m: motorList){
			if(m.jag!=null){
				if(m.side.equals("RIGHT")){
					m.getJag().setVoltage(rightSideVoltage);
				}else{
					m.getJag().setVoltage(leftSideVoltage);
				}
			}else if(m.talonSRX!=null){
				if(m.side.equals("RIGHT")){
					m.getTalonSRX().setVoltage(rightSideVoltage);
				}else{
					m.getTalonSRX().setVoltage(leftSideVoltage);
				}
			}
		}
		//TODO deal with rampRate
	}

	private static void driveTankDrivePID(JoyVector vec) {

		//For the 2016 robot assume we go 60 inches/second

		double joyXInput=vec.getX_comp()*100;
		double joyYInput=vec.getY_comp()*100;
		double rightScal=((100-Math.abs(joyYInput))*(joyXInput/100)+joyXInput);
		double leftScal=((100-Math.abs(joyXInput))*(joyYInput/100)+joyYInput);
		double rightSideVoltageUnscaled=((rightScal+leftScal)/2);
		double leftSideVoltageUnscaled=((rightScal-leftScal)/2);
		double rightSideVoltage=((rightSideVoltageUnscaled)/100) *300;//*100rpm
		double leftSideVoltage=((leftSideVoltageUnscaled)/100)*300;
		for(MotorAndSide m: motorList){
			if(m.jag!=null){
				if(m.side.equals("RIGHT")){

				}else{

				}
			}else if(m.talonSRX!=null){
				//TODO: change to generalize

				if(m.side.equals("RIGHT")){
					if(m.isMaster()){
						m.getTalonSRX().setPositionPID(rightSideVoltage);
					}
				}else{

					if(m.isMaster()){
						m.getTalonSRX().setPositionPID(leftSideVoltage);
					}
				}
			}
		}
	}

	/**
	 * Enables all the motors in their correct mode. If you are driving in PID mode then master and slaves will also be set.
	 * If you set motors in PID mode, be sure to set the correct motors in master mode(the ones that have the ncoder on them should be 
	 * set to master mode), if this is not done correctly, the drive system will fail. Master motors MUST be instantiated and added
	 * to the list prior to their slaves.
	 */
	public void enableAll(){
		int masterRight = 0;
		int masterLeft = 0;
		for(MotorAndSide m : motorList){
			if(currentDrive == driveModes.TANK_DRIVE){
				if(m.jag!=null){
					m.getJag().initVoltage();
				}else if(m.talonSRX!=null){
					m.getTalonSRX().initVoltage();
				}
			}else if(currentDrive == driveModes.TANK_DRIVE_PID){
				//TODO: change to generalize
				if(m.jag!=null){
					m.getJag().initSpeed();
				}else if(m.talonSRX!=null){
					if(m.isMaster){
						m.getTalonSRX().initSpeed();
						if(m.getSide()=="RIGHT"){
							masterRight=m.getTalonSRX().getID();
						}else if(m.getSide()=="LEFT"){
							masterLeft=m.getTalonSRX().getID();
						}
					}else{
						if(m.getSide()=="RIGHT"){
							m.getTalonSRX().followMeMode(masterRight);
						}else if(m.getSide()=="LEFT"){
							m.getTalonSRX().followMeMode(masterLeft);
						}
					}
				}
			}
		}
	}


	/**
	 * Enables break mode on all the current motors.
	 */
	public void breakAll(){
		for(MotorAndSide m : motorList){
			if(m.jag!=null){
				m.getJag().initSpeed();
			}else if(m.talonSRX!=null){
				m.getTalonSRX().setBreak();
			}

		}
	}

}