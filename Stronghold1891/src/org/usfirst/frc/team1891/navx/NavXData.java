package org.usfirst.frc.team1891.navx;

/**
 * @author Josh
 *
 */
public class NavXData {
	
	private double yaw;
	private double angle;
	private double worldLinearAccelX;
	private double worldLinearAccelY;
	private boolean moving;
	private double velocityX;
	private double velocityY;
	private double displacementX;
	private double displacementY;
	private double rawGyroX;
	private double rawGyroY;
	private double rawGyroZ;
	private double rawAccelX;
	private double rawAccelY;
	private double rawAccelZ;
	private double rawMagX;
	private double rawMagY;
	private double rawMagZ;
	/**
	 * @param yaw 
	 * @param angle 
	 * @param worldLinearAccelX 
	 * @param worldLinearAccelY 
	 * @param moving 
	 * @param velocityX 
	 * @param velocityY 
	 * @param displacementX 
	 * @param displacementY 
	 * @param rawGyroX 
	 * @param rawGyroY 
	 * @param rawGyroZ 
	 * @param rawAccelX 
	 * @param rawAccelY 
	 * @param rawAccelZ 
	 * @param rawMagX 
	 * @param rawMagY 
	 * @param rawMagZ 
	 * 
	 */
	public NavXData(double yaw, double angle, double worldLinearAccelX, double worldLinearAccelY, boolean moving,double velocityX, double velocityY,
		double displacementX, double displacementY, double rawGyroX, double rawGyroY, double rawGyroZ, double rawAccelX, double rawAccelY,
		double rawAccelZ, double rawMagX, double rawMagY, double rawMagZ){
		
		this.yaw = yaw;
		this.angle = angle;
		this.worldLinearAccelX = worldLinearAccelX;
		this.worldLinearAccelY = worldLinearAccelY;
		this.moving = moving;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.displacementX = displacementX;
		this.displacementY = displacementY;
		this.rawGyroX = rawGyroX;
		this.rawGyroY = rawGyroY;
		this.rawGyroZ = rawGyroZ;
		this.rawAccelX = rawAccelX;
		this.rawAccelY = rawAccelY;
		this.rawAccelZ = rawAccelZ;
		this.rawMagX = rawMagX;
		this.rawMagY = rawMagY;
		this.rawMagZ = rawMagZ;
	}
	
	/**
	 * @return yaw
	 */
	public double getYaw(){
		return yaw;
	}
	
	/**
	 * @return angle
	 */
	public double getAngle(){
		return angle;
	}
	
	/**
	 * @return world linear acceleration X
	 */
	public double getWorldLinearAccelX(){
		return worldLinearAccelX;
	}
	/**
	 * @return world linear acceleration Y
	 */
	public double getWorldLinearAccelY(){
		return worldLinearAccelY;
	}
	/**
	 * @return if it's moving
	 */
	public boolean isMoving(){
		return moving;
	}
	/**
	 * @return x velocity
	 */
	public double getVelocityX(){
		return velocityX;
	}
	/**
	 * @return y velocity
	 */
	public double getVelocityY(){
		return velocityY;
	}
	/**
	 * @return x displacement
	 */
	public double getDisplacementX(){
		return displacementX;
	}
	/**
	 * @return y displacement
	 */
	public double getDisplacementY(){
		return displacementY;
	}
	/**
	 * @return raw gyro data x
	 */
	public double getRawGyroX(){
		return rawGyroX;
	}
	/**
	 * @return raw gyro data y
	 */
	public double getRawGyroY(){
		return rawGyroY;
	}
	/**
	 * @return raw gyro data z
	 */
	public double getRawGyroZ(){
		return rawGyroZ;
	}
	/**
	 * @return raw x acceleration
	 */
	public double getRawAccelX(){
		return rawAccelX;
	}
	/**
	 * @return raw y acceleration
	 */
	public double getRawAccelY(){
		return rawAccelY;
	}
	/**
	 * @return raw z acceleration
	 */
	public double getRawAccelZ(){
		return rawAccelZ;
	}
	/**
	 * @return raw magnitude x
	 */
	public double getRawMagX(){
		return rawMagX;
	}
	/**
	 * @return raw magnitude y
	 */
	public double getRawMagY(){
		return rawMagY;
	}
	/**
	 * @return raw magnitude z
	 */
	public double getRawMagZ(){
		return rawMagZ;
	}
}
