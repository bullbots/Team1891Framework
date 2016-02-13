package org.usfirst.frc.team1891.machinestate2016;


public class testMachineState {
	
	public static void main(String[]args){
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time: "+(startTime/1000));
		MachineState st = new MachineState();
		st.initiateMachine();
		st.findShotestPath();
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total runtime: "+totalTime);

		
		
	}
	
	
}




