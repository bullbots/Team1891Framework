
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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	SmartDashboard dash = new SmartDashboard();
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
		if(joy1.getRawButton(6) && joy1.getRawButton(7)){
			if(buttonToggle(10)){
				double joyVal = ((-joy1.getRawAxis(2)+1)/2*.5);
				
				vicBottom.set(joyVal*1.5);
				vicTop.set(joyVal);
			}
			else{
				double joyVal = ((-joy1.getRawAxis(2)+1)/2*.06);
				vicBottom.set(joyVal*1.5);
				vicTop.set(joyVal);
			}
			
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
			double joyYVal = 0;
			double joyXVal = 0;
		//if(Math.abs(joy1.getRawAxis(0))<.05){
			joyXVal = joy1.getRawAxis(0);
		//}
		//if(Math.abs(joy1.getRawAxis(0))<.05){
			joyYVal = joy1.getRawAxis(1);
		//}
		
		double rightSidePower = (joyXVal+joyYVal);
		double leftSidePower = (joyXVal -joyYVal);
		jagLeft1.set(leftSidePower*6*scale(leftSidePower, rightSidePower));
		jagLeft5.set(leftSidePower*6*scale(leftSidePower, rightSidePower));
		jagRight4.set(rightSidePower*6*scale(leftSidePower, rightSidePower));
		jagRight6.set(rightSidePower*6*scale(leftSidePower, rightSidePower));
		dash.putNumber("Left Side",leftSidePower);
		dash.putNumber("Right Side",rightSidePower);
	}
	
	/**
	 * @param left
	 * @param right
	 * @return
	 */
	public double scale(double left, double right){
		left = Math.abs(left);
		right = Math.abs(right);
		double max = 1;
		if(left >=right && left>1){
			max = 1/left;
		}
		if(right >=left && right>1){
			max = 1/right;
		}
		
		
		return max;
	}
	
	
	boolean press = false;
	boolean release = true;
	boolean on = false;
	/**
	 * @param index
	 * @return
	 */
	public boolean buttonToggle(int index){
		
		if (!press && button(index) && release){
			press=true;
			on = true;
		}
		else if (press && !button(index) && release){
			press=false;
			release = false;
			on = true;
		}
		else if (!press && button(index) && !release){
			press = true;
			on = false;
		}
		else if (press && !button(index) && !release){
			press = false;
			release = true;
			on = false;
		}
		return on;
	}

	/**
	 * @param button
	 * @return
	 */
	public boolean button(int button)
	{
		return joy1.getRawButton(button);
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
