package org.usfirst.frc.team1891.visionsystem;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CameraPIDWidth implements PIDSource{
	private PIDSourceType pidType=PIDSourceType.kDisplacement;
	private double optimalValue;
	VisionProcessing vp = new VisionProcessing();
	
	private int c = 0;
	
	
	/**
	 * @param optimalValue the value to be cented on
	 */
	public CameraPIDWidth(double optimalValue){
		this.optimalValue=optimalValue;
	}
	
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidType=pidSource;
	}

	
	public PIDSourceType getPIDSourceType() {
		return pidType;
	}

	
	public double pidGet() {
		double value =  (optimalValue-vp.getCenterX());
//		if (++c >10) {
//			System.out.println("Signal:"+(value/RobotCentering.resolutionX));
//			c=0;
//		}

		return (value/RobotCentering.resolutionX);
	}


}
