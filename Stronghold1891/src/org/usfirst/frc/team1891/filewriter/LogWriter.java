package org.usfirst.frc.team1891.filewriter;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class contains code to write to the logs.
 * @author Tyler
 *
 */
public class LogWriter {
	
	/**
	 * This method will append a message to the log, this method should only be called if the message is not an eror message
	 * but rather a system message. If the method is an error message the "appendErrorToLog" method should be used instead.
	 * This method will automatically append SYSTEM: and the date to the message so do not add that into the message just 
	 * simply provite the string describing the error message
	 * @param message the message that needs to display in the log.
	 */
	public void appendMessageToLog(String message){
		String filename= "log";
		FileWriter fw;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		try {
			fw = new FileWriter(filename,true);
			fw.write("SYSTEM: "+message+" Date: "+dateFormat.format(date)+"\n");
			fw.close();
		} catch (IOException e1) {
		
		
		} 
	}
	
	/**
	 * This method will append an error to the log. Please look at appendMessageToLog for more information.
	 * This method will automatically append CRITICAL ERROR: and the date to the log so please do not include that
	 * in the string you provide.
	 * @param message the error message that you need to append to the log.
	 */
	public void appendErrorToLog(String message){
		String filename= "log";
		FileWriter fw;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		try {
			fw = new FileWriter(filename,true);
			fw.write("CRITICAL ERROR: "+ message+" Date: "+dateFormat.format(date)+"\n");
			fw.close();
		} catch (IOException e1) {
		
		
		} 
	}
}
