
package org.usfirst.frc.team1891.strongholdrobot2016;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.LinkedList;

import org.usfirst.frc.team1891.arduino.Arduino;
import org.usfirst.frc.team1891.drivesystem.*;
import org.usfirst.frc.team1891.drivesystem.DriveSystem.driveModes;
import org.usfirst.frc.team1891.filewriter.LogWriter;
import org.usfirst.frc.team1891.joysticks.*;
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
public class StrongholdRobot_2016 extends IterativeRobot {

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
	boolean expelBallTimerStart=false;
	Timer expelBallTimer;
	int testVal =0;
	double ballFirst =5.0;
	double ballLast = 5.0;
	int test =0;
	MachineState autoMachine;
	int autoState=0;
	boolean autoStateThreeQuickStop=false;
	boolean autoStateFourQuickStop=false;
	Timer testFireTime;
	Arduino lights;
	/**
	 * Boolean to tell if the ball has been fired or not.
	 */
	public static boolean autoBallFired=false;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		
		
		
		
		log = new LogWriter();
		log.appendMessageToLog("Robot init started");
		joy = new JoystickControl();
		joy.init(new Joystick(0), new Joystick(1));
		motors = new LinkedList<MotorAndSide>();
		try {
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(4)), "RIGHT", true));//Has encoder
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(3)), "RIGHT", false));
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(1)), "LEFT",true));//Has encoder
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(2)), "LEFT", true));
		} catch (InvalidSideException e) {
			log.appendMessageToLog("Invalid side was initialized");
			e.printStackTrace();
		}
		ballCollect = new TalonSRX(new CANTalon(5));
		rearShooter = new TalonSRX(new CANTalon(6));
		drive= new DriveSystem(motors);
		solenoids = new LinkedList<SolenoidBoth>();
		solenoids.add(new SolenoidBoth(1, 0));//Shooter solenoid
		solenoids.add(new SolenoidBoth(3, 2));//Solenoid that launches ball.
		compressor  = new LinkedList<Compressor>();
		compressor.add(new Compressor(0));
		pn = new PnController(solenoids, compressor);
//		pn.turnAllCompressorsOn();
		expelBallTimer= new Timer();
		centerRobo = new RobotCentering(160, 320);
		testFireTime= new Timer();
		lights = new Arduino(4);
		elbow = new TalonSRX(new CANTalon(7));
		elbow.initVoltage();
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

//		stateMachine.initiateMachine();
//		stateMachine.findShotestPath();

		drive.setDriveSystem(driveModes.TANK_DRIVE_PID);
		drive.enableAll();

		autoMachine = new MachineState();
		autoMachine.initiateMachine();
	}
	/**
	 * This function
	 *  is called periodically during autonomous
	 */
	public void autonomousPeriodic() {	
//		autoMachine.update();
//		try {
//			autoState=autoMachine.getState();
//		} catch (InvalidStateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		switch(autoState){
//			case 0://Finding crossable defense
//				System.out.println("State 0");
//				break;
//			case 1:// Routing to defense
//				System.out.println("State 1");
//				autoMachine.findShotestPath();
//				break;
//			case 2://Moving to defense
//				System.out.println("State 2");
//				drive.drive(new JoyVector(-0.15,-1,0,0));//Go forwards
//				break;
//			case 3://Crossing defense
//				if(autoMachine.isTurnLeft()){
//					drive.drive(new JoyVector(-0.3,0,0,0));
//				}else if(autoMachine.isTurnRight()){
//					drive.drive(new JoyVector(0.3,0,0,0));
//				}else{
//					if(!autoStateThreeQuickStop){//Do a quick stop to stop the turning.
//						drive.drive(new JoyVector(0,0,0,0));
//						Timer.delay(0.1);
//						autoStateThreeQuickStop=true;
//					}
//					drive.drive(new JoyVector(-0.15,-1,0,0));
//				}
//				System.out.println("State 3");
//				break;
//			case 4://Moving to shooting postion
//				System.out.println("State 4");
//				drive.drive(new JoyVector(0,0,0,0));
//				if(autoMachine.isTurnLeft()){
//					drive.drive(new JoyVector(-0.3,0,0,0));
//				}else if(autoMachine.isTurnRight()){
//					drive.drive(new JoyVector(0.3,0,0,0));
//				}else{
//					if(!autoStateFourQuickStop){//Do a quick stop to stop the turning.
//						drive.drive(new JoyVector(0,0,0,0));
//						Timer.delay(0.1);
//						autoStateFourQuickStop=true;
//					}
//					drive.drive(new JoyVector(-0.15,-1,0,0));
//				}
//				break;
//				
//			case 5://Finding goal target
//				System.out.println("State 5");
//				JoyVector tmp = centerRobo.centerRobot();
//				if(tmp!=null){
//					drive.drive(tmp);
//				}
//				break;
//			case 6://Firing at target
//				System.out.println("State 6");
//				//Firing code
//				break;
//			case 7://End autonomous 
//				System.out.println("State 7");
//				//#Dance
//				break;
//		}
		
	}
	
	
	public void teleopInit(){
		System.out.println("In teleop init");
		drive.setDriveSystem(driveModes.TANK_DRIVE_PID);
		drive.enableAll();
		autoMachine = new MachineState();

		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue){
			lights.writeTeam(0);
		}
		else{
			lights.writeTeam(1);
		}
	}
	
	
	
	/**
	 * 
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		drive.drive(joy.getData());
		if(joy.button(3)){//Collect ball
			ballCollect.setVoltage(12);
			rearShooter.setVoltage(2);
			
			pn.extend(0);
		}else{//Do nothing
			pn.retract(0);
			ballCollect.setVoltage(0);
			rearShooter.setVoltage(0);
		}
		
		if(joy.button(1)){//Shoot ball
				pn.extend(1);
				testFireTime.start();
		}else if(testFireTime.get()>1){
			pn.retract(1);
			testFireTime.stop();
			testFireTime.reset();
		}
		
		if(joy.button(2)){

			ballCollect.setVoltage(2);
			rearShooter.setVoltage(-3);
		}
		
		if (DriverStation.getInstance().getBatteryVoltage() < 12){
			lights.lowBattery();
		}
		if (elbow.getFeedbackValuePosition() > -588)
		{
			elbow.setVoltage(-5);
		}
		else if (elbow.getFeedbackValuePosition() < - 588 && elbow.getFeedbackValuePosition() > -700)
		{
			elbow.setVoltage(-3.3);
		}
		else
		{
			elbow.setVoltage(2);
		}
	}

	
	public void testInit(){
		drive.setDriveSystem(driveModes.TANK_DRIVE_PID);
		drive.enableAll();
	}
	
	/**
	 * This function is called periodically during test mode
	 */
	@SuppressWarnings("static-access")
	public void testPeriodic() {
		drive.drive(joy.getData());		
	}
}
