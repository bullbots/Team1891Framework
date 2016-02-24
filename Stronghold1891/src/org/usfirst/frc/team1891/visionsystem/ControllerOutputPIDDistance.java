package org.usfirst.frc.team1891.visionsystem;

import org.usfirst.frc.team1891.joysticks.JoyVector;

import edu.wpi.first.wpilibj.PIDOutput;

public class ControllerOutputPIDDistance implements PIDOutput{
JoyVector joyVect;
	
	int c = 0;
	
	public void pidWrite(double output) {
		joyVect = new JoyVector(0, output, 0 ,0);
		
//		if (++c >10) {
//			System.out.println("pid out:"+output);
//			c=0;
//		}
		
		
	}
	
	/**
	 * @return the turning joy vector
	 */
	public JoyVector getTurnVector(){
		return joyVect;
	}

}
