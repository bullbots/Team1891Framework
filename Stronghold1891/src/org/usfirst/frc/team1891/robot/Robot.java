
package org.usfirst.frc.team1891.robot;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.LinkedList;

import org.usfirst.frc.team1891.arduino.Arduino;
import org.usfirst.frc.team1891.drivesystem.*;
import org.usfirst.frc.team1891.drivesystem.DriveSystem.driveModes;
import org.usfirst.frc.team1891.filewriter.LogWriter;
import org.usfirst.frc.team1891.joysticks.*;
import org.usfirst.frc.team1891.machinestate2016.FieldConfig;
import org.usfirst.frc.team1891.machinestate2016.InvalidStateException;
import org.usfirst.frc.team1891.machinestate2016.MachineState;
import org.usfirst.frc.team1891.machinestate2016.Point;
import org.usfirst.frc.team1891.motorcontroller.TalonSRX;
import org.usfirst.frc.team1891.navx.NavXSubsystem;
import org.usfirst.frc.team1891.pneumatics.PnController;
import org.usfirst.frc.team1891.pneumatics.SolenoidBoth;
import org.usfirst.frc.team1891.visionsystem.RobotCentering;

import com.kauailabs.navx.frc.AHRS;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//	MachineState stateMachine;
	//	LogWriter log;
	//	JoystickControl joy;
	//	DriveSystem drive;
	//	LinkedList<MotorAndSide> motors;
	//	TalonSRX ballCollect;
	//	TalonSRX rearShooter;
	RobotCentering centerRobo;
	//	SmartDashboard dash;

	Encoder en;
	CANJaguar jag;
	MachineState stateMachine;
	LogWriter log;
	JoystickControl joy;
	DriveSystem drive;
	LinkedList<MotorAndSide> motors;
	TalonSRX ballCollect;
	TalonSRX rearShooter;
	TalonSRX elbow;
	PnController pn;
	LinkedList<SolenoidBoth> solenoids;
	LinkedList<Compressor> compressor;
	Timer expelBallTimer;
	boolean testTimerStart=false;
	int testVal =0;
	double ballFirst =5.0;
	double ballLast = 5.0;
	int test =0;
	MachineState autoMachine;
	int autoState=0;
	boolean autoStateThreeQuickStop=false;
	boolean autoStateFourQuickStop=false;
	Arduino lights;
	boolean printedState1=false, printedState2=false, printedState3 =false, printedState4 =false, printedState5 =false, printedState6=false, printedState7=false;
	FieldConfig preGameConfig;
	Talon sr0, sr1;
	/**
	 * Boolean to tell if the ball has been fired or not.
	 */
	public static boolean autoBallFired=false;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		expelBallTimer=new Timer();		
		log = new LogWriter();
		log.appendMessageToLog("Robot init started");
		joy = new JoystickControl();
		joy.init(new Joystick(0), new Joystick(1));
		motors = new LinkedList<MotorAndSide>();
		try {
			//add masters before slaves
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(3)), "RIGHT", true));//Has encoder master
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(1)), "LEFT", true));//Has encoder master

			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(2)), "LEFT", false));//slave (pleb)
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(0)), "RIGHT", false));//slave (pleb)

		} catch (InvalidSideException e) {
			log.appendMessageToLog("Invalid side was initialized");
			e.printStackTrace();

		}
		ballCollect = new TalonSRX(new CANTalon(5));
		drive= new DriveSystem(motors);
//		preGameConfig = new FieldConfig();
//		centerRobo = new RobotCentering(160, 320);
		sr0= new Talon(0);
		sr1=new Talon(1);
	}


	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
	public void autonomousInit() {
		drive.setDriveSystem(driveModes.TANK_DRIVE_PID);
		drive.enableAll();
		printedState1=false;
		printedState2=false;
		printedState3 =false;
		printedState4 =false;
		printedState5 =false; 
		printedState6=false;
		printedState7=false;
		autoMachine = new MachineState();
		autoMachine.initiateMachine();
		preGameConfig.configureRobotStartPosition();
	}
	/**
	 * This function
	 *  is called periodically during autonomous
	 */
	public void autonomousPeriodic() {	
		
		autoMachine.update();
		try {
			autoState=autoMachine.getState();
		} catch (InvalidStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

		switch(autoState){
		case 0://Finding crossable defense
			System.out.println("State 0");
			break;
		case 1:// Routing to defense
			if(!printedState1){
				System.out.println("State 1");
				printedState1=true;
			}
			autoMachine.findShotestPath();

			break;
		case 2://Moving to defense
			if(!printedState2){
				System.out.println("State 2");
				printedState2=true;
			}
//			drive.drive(new JoyVector(-0.15,-1,0,0));//Go forwards
			drive.drive(new JoyVector(0,-0.29,0,0));
			break;
		case 3://Crossing defense
			if(!printedState3){
				System.out.println("State 3");
				printedState3=true;
			}
			if(autoMachine.isTurnLeft()){
//				drive.drive(new JoyVector(-0.3,0,0,0));
				drive.drive(new JoyVector(-0.25,0,0,0));
			}else if(autoMachine.isTurnRight()){
//				drive.drive(new JoyVector(0.3,0,0,0));
				drive.drive(new JoyVector(0.25,0,0,0));
			}else{
				if(!autoStateThreeQuickStop){//Do a quick stop to stop the turning.
//					drive.drive(new JoyVector(0,0,0,0));
					drive.drive(new JoyVector(0,0,0,0));
					Timer.delay(0.1);
					autoStateThreeQuickStop=true;
				}
//				drive.drive(new JoyVector(-0.15,-1,0,0));
				drive.drive(new JoyVector(0,-0.29,0,0));
			}
			break;
		case 4://Moving to shooting postion
			if(!printedState4){
				System.out.println("State 4");
				printedState4=true;
			}
			drive.drive(new JoyVector(0,0,0,0));
			if(autoMachine.isTurnLeft()){
//				drive.drive(new JoyVector(-0.3,0,0,0));
				drive.drive(new JoyVector(-0.25,0,0,0));
			}else if(autoMachine.isTurnRight()){
//				drive.drive(new JoyVector(0.3,0,0,0));
				drive.drive(new JoyVector(0.25,0,0,0));
			}else{
				if(!autoStateFourQuickStop){//Do a quick stop to stop the turning.
//					drive.drive(new JoyVector(0,0,0,0));
					drive.drive(new JoyVector(0,0,0,0));
					Timer.delay(0.1);
					autoStateFourQuickStop=true;
				}
//				drive.drive(new JoyVector(-0.15,-1,0,0));
				drive.drive(new JoyVector(0,-0.29,0,0));
			}
			break;

		case 5://Finding goal target
			if(!printedState5){
				System.out.println("State 5");
				printedState5=true;
			}
			JoyVector tmp = centerRobo.centerRobot();
			if(tmp!=null){
				drive.drive(tmp);
			}
			break;
		case 6://Firing at target
			if(!printedState6){
				System.out.println("State 6");
				printedState6=true;
			}
			//Firing code
			break;
		case 7://End autonomous 
			if(!printedState7){
				System.out.println("State 7");
				printedState7=true;
			}
			//#Dance
			break;
		}

	}


	
	public void teleopInit(){
		System.out.println("In teleop init");
		drive.setDriveSystem(driveModes.TANK_DRIVE_PID);
		drive.enableAll();
		SmartDashboard.putNumber("sr0", 0.0);
		SmartDashboard.putNumber("sr1", 0.0);
	}



	/**
	 * 
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		drive.drive(joy.getData());
		if(joy.button(1)){
			sr0.set(SmartDashboard.getNumber("sr0"));
			sr1.set(SmartDashboard.getNumber("sr1"));
		}else{
			sr0.set(0);
			sr1.set(0);
		}
	}


	public void testInit(){
		drive.setDriveSystem(driveModes.TANK_DRIVE_PID);
		drive.enableAll();
		expelBallTimer.stop();
		expelBallTimer.reset();
		testTimerStart=false;
	}

	/**
	 * This function is called periodically during test mode
	 */
	@SuppressWarnings("static-access")
	public void testPeriodic() {
//		if(!testTimerStart){
//			expelBallTimer.start();
//			testTimerStart=true;
//		}
//		System.out.println(expelBallTimer.get());
//		
//		if(expelBallTimer.get()>=12.00){
//			drive.drive(new JoyVector(0,0,0,0));
//			drive.breakAll();
//		}else{
//			drive.drive(new JoyVector(0,-0.29,0,0));
//		}
		preGameConfig.configureRobotStartPosition();
	}
}
