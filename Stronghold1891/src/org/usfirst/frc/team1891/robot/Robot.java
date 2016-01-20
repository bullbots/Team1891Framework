
package org.usfirst.frc.team1891.robot;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team1891.drivesystem.*;
import org.usfirst.frc.team1891.filewriter.*;
import org.usfirst.frc.team1891.joysticks.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    Encoder en;
    CANJaguar jag;
	MachineState stateMachine;
	LogWriter log;
	JoystickControl joy;
	DriveSystem drive;
	Joystick joyMain;
	int testNum;
	boolean initCalled=false;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	log.appendMessageToLog("Robot init started");
    	joy = new JoystickControl();
        jag = new CANJaguar(2);
        stateMachine = new MachineState();
        drive = new DriveSystem();
        joyMain = new Joystick(0);
//        joy = new JoystickControl(joyMain);
        int testNum;
        
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
    	try{
	    	while(true){
	    		stateMachine.update();
	    		switch(stateMachine.getState()){
	    		case 0://Finding crossable defense
	    			break;
	    		case 1:// Routing to defense
	    			break;
	    		case 2://Moving to defense
	    			break;
	    		case 3://Crossing defense
	    			break;
	    		case 4://Finding goal target.
	    			break;
	    		case 5://Moving to shooting position
	    			break;
	    		case 6://Firing at target
	    			break;
	    		case 7://End autonomous 
	    			break;
	    		}
	    	}
    	}catch(InvalidStateException e){
    		log.appendErrorToLog("The state was invalid");
    	}
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	jag.set(60);
    	System.out.println(jag.getSpeed());
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	if(joyMain.getRawButton(1) || testNum==0){
    		testNum=0;
    		org.usfirst.frc.team1891.drivesystem.Robot driveRobo = new org.usfirst.frc.team1891.drivesystem.Robot();
    		if(!initCalled){
    			driveRobo.teleopInit();
    			initCalled=true;
    		}
    		driveRobo.teleopPeriodic();
    	}else if(joyMain.getRawButton(2) || testNum==1){
    		testNum=1;
    	}else if(joyMain.getRawButton(3) || testNum==2){
    		testNum=2;
    	}else if(joyMain.getRawButton(4) || testNum==3){
    		testNum=3;
    	}
    }
    
}
