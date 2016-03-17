
package org.usfirst.frc.team1891.strongholdrobot2016;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import java.util.LinkedList;
import org.usfirst.frc.team1891.arduino.Arduino;
import org.usfirst.frc.team1891.drivesystem.*;
import org.usfirst.frc.team1891.drivesystem.DriveSystem.driveModes;
import org.usfirst.frc.team1891.joysticks.*;
import org.usfirst.frc.team1891.machinestate2016.FieldConfig;
import org.usfirst.frc.team1891.machinestate2016.InvalidStateException;
import org.usfirst.frc.team1891.machinestate2016.MachineState;
import org.usfirst.frc.team1891.motorcontroller.TalonSRX;
import org.usfirst.frc.team1891.pneumatics.PnController;
import org.usfirst.frc.team1891.pneumatics.SolenoidBoth;
import org.usfirst.frc.team1891.visionsystem.RobotCentering;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class StrongholdRobot_2016 extends IterativeRobot {
	
	
	RobotCentering centerRobo;//This is the class that will control all robot centering code.
	JoystickControl driverJoy;//The driver joystick.
	DriveSystem drive;//The driver control system, all driving will be done through this class.
	LinkedList<MotorAndSide> motors;//The linked list that the drive system requres for all motors
	TalonSRX ballCollect;//The ball collector SRX, this is the talon that is in the front of the robot.
	TalonSRX rearShooter;//The shooter ball, this is the shooter talon that is in the rear of the robot.
	TalonSRX elbow;//The is the talon that controls the arm movement.
	PnController pn;//This is the pneumatic control class, it will control all pneumatic movement.
	LinkedList<SolenoidBoth> solenoids;//This list is for all of the solenoids that are on the robot, it will be passed to the PnController, solenoids will be acessed by index.
	LinkedList<Compressor> compressor;//This is a list of all the compressor that are on the robot, it will be passed to the PnController.
	MachineState autoMachine;//This is the class that controls all autonomous movement in auto mode and controls the state machine.
	int autoState=0;//This value represents the state of the machine in auto mode, please refer to the machine state documentation.
	boolean autoStateThreeQuickStop=false;//This value is used in autoPeriodic to provide a boolean flag for a quick stop in state 3.
	boolean autoStateFourQuickStop=false;//The value is used in autoPeriodic to provate a boolean flag for a quick stop in state 4.
	Timer ballShooterRectractTime;//This value is used to control the shooting solenoid so that it does not immediatly retract.
	Arduino lights;//This is the class that controls the arduino lights.
	boolean printedState1=false, printedState2=false, printedState3 =false, printedState4 =false, printedState5 =false, printedState6=false, printedState7=false;//Boolean flags for auto print statements.
	FieldConfig preGameConfig;//Class that controls pregame configuration.
	/**
	 * Boolean to tell if the ball has been fired or not.
	 */
	public static boolean autoBallFired=false;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		driverJoy = new JoystickControl();//Joystick for the driver
		driverJoy.init(new Joystick(0), new Joystick(1));//Instantiate joystick for the driver, make sure the co-driver controller is not on port 0 or 1.
		
		motors = new LinkedList<MotorAndSide>();//The list of motors to be passed to the drive system
		try {
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(4)), "RIGHT", true));//Has encoder
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(3)), "RIGHT", false));
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(1)), "LEFT",true));//Has encoder
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(2)), "LEFT", true));
		} catch (InvalidSideException e) {
			e.printStackTrace();
		}
		ballCollect = new TalonSRX(new CANTalon(5));//Instantite shooting talon
		rearShooter = new TalonSRX(new CANTalon(6));//Instantiate shooting talon
		drive= new DriveSystem(motors);//The drive system
		solenoids = new LinkedList<SolenoidBoth>();//List of all solenoids on the robot.
		solenoids.add(new SolenoidBoth(1, 0));//Shooter solenoid
		solenoids.add(new SolenoidBoth(3, 2));//Solenoid that launches ball.
		compressor  = new LinkedList<Compressor>();//List of all compressors (just one)
		compressor.add(new Compressor(0));
		pn = new PnController(solenoids, compressor);//Instantiate the PnController
		pn.turnAllCompressorsOn();//Turn on all compressors.
		centerRobo = new RobotCentering(160, 320);//Instantiate the auto robot centering, for a 320x160 image.
		lights = new Arduino(4);//Instatiate the arduino controller
		elbow = new TalonSRX(new CANTalon(7));//Instantiate the motor controller for the elbow.
		elbow.initVoltage();//Initate the arm motor controller in voltage mode.
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
			drive.drive(new JoyVector(-0.15,-1,0,0));//Go forwards
			break;
		case 3://Crossing defense
			if(!printedState3){
				System.out.println("State 3");
				printedState3=true;
			}
			if(autoMachine.isTurnLeft()){
				drive.drive(new JoyVector(-0.3,0,0,0));
			}else if(autoMachine.isTurnRight()){
				drive.drive(new JoyVector(0.3,0,0,0));
			}else{
				if(!autoStateThreeQuickStop){//Do a quick stop to stop the turning.
					drive.drive(new JoyVector(0,0,0,0));
					Timer.delay(0.1);
					autoStateThreeQuickStop=true;
				}
				drive.drive(new JoyVector(-0.15,-1,0,0));
			}
			break;
		case 4://Moving to shooting postion
			if(!printedState4){
				System.out.println("State 4");
				printedState4=true;
			}
			drive.drive(new JoyVector(0,0,0,0));
			if(autoMachine.isTurnLeft()){
				drive.drive(new JoyVector(-0.3,0,0,0));
			}else if(autoMachine.isTurnRight()){
				drive.drive(new JoyVector(0.3,0,0,0));
			}else{
				if(!autoStateFourQuickStop){//Do a quick stop to stop the turning.
					drive.drive(new JoyVector(0,0,0,0));
					Timer.delay(0.1);
					autoStateFourQuickStop=true;
				}
				drive.drive(new JoyVector(-0.15,-1,0,0));
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
		drive.drive(driverJoy.getData());
		if(driverJoy.button(3)){//Collect ball
			ballCollect.setVoltage(12);
			rearShooter.setVoltage(2);
			
			pn.extend(0);
		}else{//Do nothing
			pn.retract(0);
			ballCollect.setVoltage(0);
			rearShooter.setVoltage(0);
		}
		
		if(driverJoy.button(1)){//Shoot ball
				pn.extend(1);
				ballShooterRectractTime.start();
		}else if(ballShooterRectractTime.get()>1){
			pn.retract(1);
			ballShooterRectractTime.stop();
			ballShooterRectractTime.reset();
		}
		
		if(driverJoy.button(2)){

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
	public void testPeriodic() {
		drive.drive(driverJoy.getData());		
	}
}
