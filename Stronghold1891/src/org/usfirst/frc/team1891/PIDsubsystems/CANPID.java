package org.usfirst.frc.team1891.PIDsubsystems;

import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.CANSpeedController.ControlMode;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * @author Tyler
 *
 */
public class CANPID implements CANSpeedController {


	  /**
	   * Common interface for getting the current set speed of a speed controller.
	   *
	   * @return The current set speed. Value is between -1.0 and 1.0.
	   */
	  public double get() {
		return 0;
	}

	  /**
	   * Common interface for setting the speed of a speed controller.
	   *
	   * @param speed The speed to set. Value should be between -1.0 and 1.0.
	   * @param syncGroup The update group to add this Set() to, pending
	   *        UpdateSyncGroup(). If 0, update immediately.
	   */
	  public void set(double speed, byte syncGroup) {
	}

	  /**
	   * Common interface for setting the speed of a speed controller.
	   *
	   * @param speed The speed to set. Value should be between -1.0 and 1.0.
	   */
	  public void set(double speed) {
	}
	  /**
	   * Gets the current control mode.
	   *
	   * @return the current control mode
	   */
	  public ControlMode getControlMode() {
		return null;
	}

	  /**
	   * Sets the control mode of this speed controller.
	   *
	   * @param mode the the new mode
	   */
	  public void setControlMode(int mode) {
	}

	  /**
	   * Set the proportional PID constant.
	   */
	  public void setP(double p) {
	}

	  /**
	   * Set the integral PID constant.
	   */
	  public void setI(double i) {
	}

	  /**
	   * Set the derivative PID constant.
	   */
	  public void setD(double d) {
	}


	  /**
	   * Get the current input (battery) voltage.
	   *
	   * @return the input voltage to the controller (in Volts).
	   */
	  public double getBusVoltage() {
		return 0;
	}

	  /**
	   * Get the current output voltage.
	   *
	   * @return the output voltage to the motor in volts.
	   */
	  public double getOutputVoltage() {
		return 0;
	}

	  /**
	   * Get the current being applied to the motor.
	   *
	   * @return the current motor current (in Amperes).
	   */
	  public double getOutputCurrent() {
		return 0;
	}

	  /**
	   * Get the current temperature of the controller.
	   *
	   * @return the current temperature of the controller, in degrees Celsius.
	   */
	  public double getTemperature() {
		return 0;
	}

	  /**
	   * Return the current position of whatever the current selected sensor is.
	   *
	   * See specific implementations for more information on selecting feedback
	   * sensors.
	   *
	   * @return the current sensor position.
	   */
	  public double getPosition() {
		return 0;
	}

	  /**
	   * Return the current velocity of whatever the current selected sensor is.
	   *
	   * See specific implementations for more information on selecting feedback
	   * sensors.
	   *
	   * @return the current sensor velocity.
	   */
	  public double getSpeed() {
		return 0;
	}

	  /**
	   * Set the maximum rate of change of the output voltage.
	   *
	   * @param rampRate the maximum rate of change of the votlage, in Volts / sec.
	   */
	  public void setVoltageRampRate(double rampRate) {
	}

	  /**
	   * Common interface for inverting direction of a speed controller.
	   *
	   * @param isInverted The state of inversion true is inverted.
	   */
	  public void setInverted(boolean isInverted) {
	}

	  /**
	   * Common interface for returning if a speed controller is in the inverted
	   * state or not.
	   *$
	   * @return isInverted The state of the inversion true is inverted.
	   *
	   */
	  public boolean getInverted() {
		return false;
	}



	  /**
	   * Disable the speed controller
	   */
	  public void disable() {
	}

	
	public void pidWrite(double output) {
		
	}

	@Override
	public void setPID(double p, double i, double d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getP() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getI() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getD() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSetpoint(double setpoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getSetpoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getError() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startLiveWindowMode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopLiveWindowMode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initTable(ITable subtable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ITable getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSmartDashboardType() {
		// TODO Auto-generated method stub
		return null;
	}

}
