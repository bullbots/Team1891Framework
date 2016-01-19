package org.usfirst.frc.team1891.navx;

import com.kauailabs.navx.frc.AHRS;
/**
 * @author Tyler
 *
 */
public class NavXSubsystem {
	 AHRS nav;
	 
	 /**
	 * @param system the AHRS system being used.
	 */
	public NavXSubsystem(AHRS system){
		 this.nav=system;
	 }
	
}
