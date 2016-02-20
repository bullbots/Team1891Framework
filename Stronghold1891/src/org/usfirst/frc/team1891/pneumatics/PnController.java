package org.usfirst.frc.team1891.pneumatics;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.Compressor;

/**
 * @author Tyler
 *Pneumatics controller software.
 */
public class PnController {
	
	LinkedList<SolenoidBoth> solenoids;
	LinkedList<Compressor> compressors;
	/**
	 * Constuctor for the pneumatics control. It would be advisable to add the solenoids to the list 
	 * in a way that corresponds to their PCM value, same with the compressor.
	 * @param solenoids a list of all solenoids currently on the system.
	 * @param compressors a list of all compressors currently on the system.
	 */
	public PnController(LinkedList<SolenoidBoth> solenoids, LinkedList<Compressor> compressors){
		this.solenoids=solenoids;
		this.compressors=compressors;
	}
	
	/**
	 * Turns all compressors on that are on the system.
	 */
	public void turnAllCompressorsOn(){
		for(Compressor c : compressors){
			c.setClosedLoopControl(true);
		}
	}
	
	/**
	 * Turns all compressors off that are on the system.
	 */
	public void turnOffCompressorsOff(){
		for(Compressor c : compressors){
			c.setClosedLoopControl(false);
		}
	}
	
	/**
	 * Turns a specific compressor on the system on.
	 * @param index the index of the compressor you want to turn on.
	 */
	public void turnOnIndexCompressor(int index){
		compressors.get(index).setClosedLoopControl(true);
	}
	
	/**
	 * Turns a specific compressor on the system off.
	 * @param index the index of the compressor you want to turn off.
	 */
	public void turnOffIndexCompressor(int index){
		compressors.get(index).setClosedLoopControl(false);
	}
	
	/**
	 * Sets the solenoid value to true, please note if you are turning on the
	 * opposite solinoid you must call turnOffSolenoid before turning the opposite on.
	 * @param index of the solenoid
	 */
	public void extend(int index){
		solenoids.get(index).turnOn();
	}
	
	/**
	 * disables the current solenoid.
	 * @param index of the solenoid.
	 */
	public void retract(int index){
		solenoids.get(index).turnOff();
	}
	
}
