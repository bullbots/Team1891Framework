
package org.usfirst.frc.team1891.motorcontroller;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Egan Schafer
 *
 */
public class TalonSRX {
	
	
	//Mode 0 is percent mode
	//Mode 1 is position mode
	//Mode 2 is speed mode
	//Mode 3 is current mode
	//Mode 4 is voltage mode
	int codesPerRev;
	CANTalon talon;
	int test=0;

	/**
	 * @param talon 
	 * 
	 */
	public TalonSRX(CANTalon talon)
	{
		this.talon = talon;
		codesPerRev = 350;
	}
	
	/**
	 * @param codes
	 */
	public void setCodesPerRev(int codes)
	{
		codesPerRev = codes;
	}
	
	/**
	 * 
	 */
	public void initPercentPID()
	{
		talon.setControlMode(0);
		talon.configEncoderCodesPerRev(codesPerRev);
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talon.enableControl();
	}
	
	/**
	 * 
	 * @param power
	 */
	public void setPercentagePID(double power)
	{
		talon.set(power);
	}
	
	/**
	 * 
	 */
	public void initPercent()
	{
		talon.setControlMode(0);
		talon.enableControl();
		
	}
	
	/**
	 *
	 * @param power
	 */
	public void setPercentage(double power)
	{
		talon.set(power);
	}
	
	/** 
	 * Sets specified jag to position mode with PID and enables control on it.
	 * @param jag specifies the jag to be set
	 * 
	 */
	public void initPositionPID()
	{
		talon.setControlMode(1);
		talon.configEncoderCodesPerRev(codesPerRev);
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		talon.setPID(0.01, 0, 0);
		talon.enable();
	}
	
	/** 
	 * Sets a postion for the talon to move motor to.
	 * @param postion specifies a position for a talon to be set to
	 * 
	 */
	public void setPositionPID(double postion)
	{
		talon.set(postion);
	}
	
	/**
	 * Sets specified talon to voltage mode with PID and enables it.
	 * @param jag specifies the talon to be set
	 * 
	 */
	public void initVoltagePID()
	{
		talon.changeControlMode(CANTalon.TalonControlMode.Speed);
		talon.configEncoderCodesPerRev(codesPerRev);
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talon.enableControl();
	}
	
	/**
	 * Sets the voltage output for the specified talon.
	 * @param volt specifies a voltage to set motor to be set
	 */
	public void setVoltagePID(double volt)
	{
		talon.set(volt);
	}
	
	/**
	 * Sets specified talon to voltage mode without PID and enables it.
	 */
	public void initVoltage()
	{
		talon.setControlMode(4);
		talon.configEncoderCodesPerRev(codesPerRev);
		talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		talon.enable();
	}
	
	/**
	 * Sets the voltage output for the specified talon.
	 * @param volt specifies a voltage to set motor to be set
	 */
	public void setVoltage(double volt)
	{
		talon.set(volt);
	}
	
	/**
	 * Sets specified talon to speed mode with PID and enables it.
	 */
	public void initSpeed()
	{
		talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		talon.reverseSensor(false);
		/* set the peak and nominal outputs, 12V means full */
		talon.configNominalOutputVoltage(+0.0f, -0.0f);
		talon.configPeakOutputVoltage(+12.0f, -12.0f);
		/* set closed loop gains in slot0 */
		talon.setProfile(0);
		talon.setF(0.3567);
		talon.setP(0.04825);
		talon.setI(0); 
		talon.setD(0);
		talon.changeControlMode(TalonControlMode.Speed);
	}
	
	
	
	/**
	 * Sets the speed for the motor to move at
	 * @param speed specifies a speed for motor to be set to
	 */
	public void setSpeed(double speed)
	{
		if(test++ <=10){
			System.out.println("Talon Velocity: "+talon.getAnalogInVelocity());
			test=0;
		}
		talon.set(speed);
	}
	
	public double getSpeed()
	{
		return talon.get();
	}
	
	public TalonControlMode getMode(){
		return talon.getControlMode();
	}
	
	public double getRawVoltageOutput(){
		return talon.getOutputVoltage();
	}
	
	public boolean isEnabled(){
		return talon.isControlEnabled();
	}
	
	public void followMeMode(int toFollow){
		talon.changeControlMode(TalonControlMode.Follower);
    	talon.set(toFollow);
	}
	
	public int getID(){
		return talon.getDeviceID();
	}
	
	public double getCurrentVelocity(){
		return talon.getAnalogInVelocity();
	}
	public double getFeedbackValuePosition()
	{
		return talon.getEncPosition();
	}
}
