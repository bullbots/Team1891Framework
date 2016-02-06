
package org.usfirst.frc.team1891.robot;

import java.util.Random;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.util.UncleanStatusException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Command autonomousCommand;

	PWM loader;
	DigitalInput readyForLoad, loaded;
	Victor vicBottom;
	Victor vicTop;
	Servo loaderServ1, loaderServ2;
	Joystick joy1;
	Timer time;
	boolean startTimer=false;
	CANJaguar jagLeft1, jagLeft5, jagRight6, jagRight4;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		/**
		 *Digital port 0 is the bottom switch on the shooter.
		 *Digital port 1 is the top switch on the shooter.
		 *PWM 1 is trigger servo 1
		 *PWM 2 is trigger servo 2
		 *PWM 3 is the loader Vix
		 *PWM 4 is the Victor in the front of the shooter, Vic 2
		 *PWM 5 servo for the locking mechanism for the wench
		 *PWM 6 is the other Victor
		 *Analog Input 0 is the shooter tilt
		 */
		jagLeft1 = new CANJaguar(1);
		jagLeft5 = new CANJaguar(5);
		jagRight6 = new CANJaguar(6);
		jagRight4 = new CANJaguar(4);
		loader = new PWM(3); 
		readyForLoad = new DigitalInput(1);
		loaded = new DigitalInput(0);
		vicBottom = new Victor(6);
		vicTop=new Victor(4);
		loaderServ1 = new Servo(1);
		loaderServ2 = new Servo(2);
		joy1 = new Joystick(0);
		time= new Timer();
	}

	
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//Sees if there is a frisbee in the loader, then starts the timer for the loading VIX
		if(readyForLoad.get()==true && loaded.get()!=true){//When the frisbee is ready for loading.
			startTimer=true;
			time.start();
		}else if(!startTimer){
			loader.setRaw(0);
		}
		
		
		//Run the loading vix
		if(startTimer && time.get()<3 && !loaded.get()){//starts the loader if startTimer is true, 
			//it has been running for less than 3sec, and there is nothing already loaded.
			loader.setRaw(160);
		}else {
			startTimer=false;
		}

		
		//starts the victors when button 3 is pressed.
		if(joy1.getRawButton(6) && joy1.getRawButton(11)){
			double joyVal = (-0.2*(joy1.getZ()-1));//This equation changes the joystick z scale from 0-1 instead of -1 to 1.
			//If you multiply by 0.5 that will allow the motors to run at full speed, 0.25 sets max at half speed. Using 0.2
			vicBottom.set(joyVal*1.5);
			vicTop.set(joyVal);
		}else{//stop victor when the button is not pressed.
			vicBottom.set(0);
			vicTop.set(0);
		}
		
		// and deploys the frisbee when button 1 is clicked.
//		if(loaded.get()==true){
			if(joy1.getRawButton(1)){
				loaderServ1.set(1);
				loaderServ2.set(0);
//			}
			
		}else{
			loaderServ1.set(0.55);
			loaderServ2.set(0.48);
		}
		
		//run the jags for drive
		double joyXVal = joy1.getX();
		double joyYVal = joy1.getY();
		double rightSidePower = (joyXVal+joyYVal);
		double leftSidePower = (joyXVal -joyYVal);
		jagLeft1.set(leftSidePower*8);
		jagLeft5.set(leftSidePower*8);
		jagRight4.set(rightSidePower*8);
		jagRight6.set(rightSidePower*8);
	}

	





















	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomousCommand != null) autonomousCommand.start();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to 
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) autonomousCommand.cancel();
		jagLeft1.setVoltageMode();
		jagLeft5.setVoltageMode();
		jagRight4.setVoltageMode();
		jagRight6.setVoltageMode();
		jagLeft1.enable();
		jagLeft5.enable();
		jagRight4.enable();
		jagRight6.enable();
	}

	/**
	 * This function is called when the disabled button is hit.
	 * You can use it to reset subsystems before shutting down.
	 */
	public void disabledInit(){

	}


	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}
	
	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		
	}
}
