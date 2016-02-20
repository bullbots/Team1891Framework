
package org.usfirst.frc.team1891.strongholdrobot2016;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.LinkedList;

import org.usfirst.frc.team1891.drivesystem.*;
import org.usfirst.frc.team1891.drivesystem.DriveSystem.driveModes;
import org.usfirst.frc.team1891.filewriter.LogWriter;
import org.usfirst.frc.team1891.joysticks.*;
import org.usfirst.frc.team1891.machinestate2016.MachineState;
import org.usfirst.frc.team1891.motorcontroller.TalonSRX;
import org.usfirst.frc.team1891.pneumatics.PnController;
import org.usfirst.frc.team1891.pneumatics.SolenoidBoth;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class StrongholdRobot_2016 extends IterativeRobot {
	Encoder en;
	CANJaguar jag;
	MachineState stateMachine;
	LogWriter log;
	JoystickControl joy;
	DriveSystem drive;
	LinkedList<MotorAndSide> motors;
	SmartDashboard test;
	TalonSRX ballCollect;
	TalonSRX rearShooter;
	PnController pn;
	LinkedList<SolenoidBoth> solenoids;
	LinkedList<Compressor> compressor;
	boolean expelBallTimerStart=false;
	Timer expelBallTimer;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		log = new LogWriter();
		log.appendMessageToLog("Robot init started");
		joy = new JoystickControl();
		joy.init(new Joystick(0));
		motors = new LinkedList<MotorAndSide>();
		try {
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(4)), "RIGHT"));//Has encoder
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(3)), "RIGHT"));
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(1)), "LEFT"));//Has encoder
			motors.add(new MotorAndSide(new TalonSRX(new CANTalon(2)), "LEFT"));
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
		pn.turnAllCompressorsOn();
		expelBallTimer= new Timer();
//		test=new SmartDashboard();
//		vp = new OpenCVProcessing();
//		stateMachine= new MachineState();
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
	}
	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		drive.drive(null);
	}
	public void teleopInit(){
		System.out.println("In teleop init");
		drive.setDriveSystem(driveModes.TANK_DRIVE);
		drive.enableAll();
	}
	
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		drive.drive(joy.getData());
		if(joy.button(3)){//Collect ball
			ballCollect.setVoltage(12);
			rearShooter.setVoltage(2);
			pn.extend(0);
			pn.retract(1);
		}else{//Do nothing
			pn.retract(0);
			pn.retract(1);
			ballCollect.setVoltage(0);
			rearShooter.setVoltage(0);
		}
		
		if(joy.button(1)){//Shoot ball
				pn.extend(1);
		}
		
		if(joy.button(2)){

			ballCollect.setVoltage(12);
			rearShooter.setVoltage(-12);
		}
		
	}

	
	public void testInit(){
		
	}
	
	/**
	 * This function is called periodically during test mode
	 */
	@SuppressWarnings("static-access")
	public void testPeriodic() {
		JoyVector tmp =joy.getData();
		test.putNumber("Joystick X", tmp.getX_comp());
		test.putNumber("Joystick Y", tmp.getY_comp());
	}
}
