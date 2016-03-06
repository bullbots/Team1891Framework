package org.usfirst.frc.team1891.navx;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.concurrent.ArrayBlockingQueue;
/**
 * @author Josh
 *
 */
public class NavXSubsystem {
	 AHRS nav;
	 /**
	 * the package that contains the NavX Data
	 */
	public NavXData data;
	 int aveLength = 12;
	 ArrayBlockingQueue<Float> Yaw = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Double> Angle = new ArrayBlockingQueue<Double>(aveLength+1);
	 ArrayBlockingQueue<Float> WorldLinearAccelX = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> WorldLinearAccelY = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> VelocityX = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> VelocityY = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> DisplacementX = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> DisplacementY = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> RawGyroX = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> RawGyroY = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> RawGyroZ = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> RawAccelX = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> RawAccelY = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> RawAccelZ = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> RawMagX = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> RawMagY = new ArrayBlockingQueue<Float>(aveLength+1);
	 ArrayBlockingQueue<Float> RawMagZ = new ArrayBlockingQueue<Float>(aveLength+1);
	 
	 
	 
	double yaw;
	double angle;
	double worldLinearAccelX;
	double worldLinearAccelY;
	boolean moving;
	double velocityX;
	double velocityY;
	double displacementX;
	double displacementY;
	double rawGyroX;
	double rawGyroY;
	double rawGyroZ;
	double rawAccelX;
	double rawAccelY;
	double rawAccelZ;
	double rawMagX;
	double rawMagY;
	double rawMagZ;
	 
	 
	 /**
	 * @param system the AHRS system being used.
	 */
	public NavXSubsystem(){
		try {
            nav = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
		
		
		
	 }
	/**
	 * @return the updated data from the NavX
	 */
	public NavXData getData(){
		updateQueue();
		
		
		return data = new NavXData(yaw, angle, worldLinearAccelX, worldLinearAccelY, moving, velocityX, velocityY, displacementX, displacementY,
				rawGyroX, rawGyroY, rawGyroZ, rawAccelX, rawAccelY, rawAccelZ, rawMagX, rawMagY, rawMagZ);
	}
	
	/**
	 * 
	 */
	public void updateQueue(){
		add(Yaw,nav.getYaw());
		remove(Yaw);
		yaw = ave(Yaw.toArray(new Float[Yaw.size()]));
		SmartDashboard.putNumber(   "IMU_Yaw",              yaw);
		
		add(Angle, nav.getAngle());
		remove(Angle);
		angle = ave(Angle.toArray(new Double[Angle.size()]));
		SmartDashboard.putNumber(   "IMU_TotalYaw",         angle);
		
		add(WorldLinearAccelX,nav.getWorldLinearAccelX());
		remove(WorldLinearAccelX);
		worldLinearAccelX = ave(WorldLinearAccelX.toArray(new Float[WorldLinearAccelX.size()]));
		SmartDashboard.putNumber(   "IMU_Accel_X",          worldLinearAccelX);

		add(WorldLinearAccelY,nav.getWorldLinearAccelY());
		remove(WorldLinearAccelY);
		worldLinearAccelY = ave(WorldLinearAccelX.toArray(new Float[WorldLinearAccelY.size()]));
		SmartDashboard.putNumber(   "IMU_Accel_Y",          worldLinearAccelY);
		
		moving = nav.isMoving();
		SmartDashboard.putBoolean(  "IMU_IsMoving",         moving);
		
		add(VelocityX,nav.getVelocityX());
		remove(VelocityX);
		velocityX = ave(VelocityX.toArray(new Float[VelocityX.size()]));
		SmartDashboard.putNumber(   "Velocity_X",           velocityX);
		
		add(VelocityY,nav.getVelocityY());
		remove(VelocityY);
		velocityY = ave(VelocityY.toArray(new Float[VelocityY.size()]));
		SmartDashboard.putNumber(   "Velocity_Y",           velocityY);
		
		add(DisplacementX,nav.getDisplacementX());
		remove(DisplacementX);
		displacementX = ave(DisplacementX.toArray(new Float[DisplacementX.size()]));
		SmartDashboard.putNumber(   "Displacement_X",       displacementX);
		
		add(DisplacementY,nav.getDisplacementY());
		remove(DisplacementY);
		displacementY = ave(DisplacementY.toArray(new Float[DisplacementY.size()]));
		SmartDashboard.putNumber(   "Displacement_Y",       displacementY);
		
		add(RawGyroX,nav.getRawGyroX());
		remove(RawGyroX);
		rawGyroX = ave(RawGyroX.toArray(new Float[RawGyroX.size()]));
		SmartDashboard.putNumber(   "RawGyro_X",            rawGyroX);
		
		add(RawGyroY,nav.getRawGyroY());
		remove(RawGyroY);
		rawGyroY = ave(RawGyroY.toArray(new Float[RawGyroY.size()]));
		SmartDashboard.putNumber(   "RawGyro_Y",            rawGyroY);
		
		add(RawGyroZ,nav.getRawGyroZ());
		remove(RawGyroZ);
		rawGyroZ = ave(RawGyroZ.toArray(new Float[RawGyroZ.size()]));
		SmartDashboard.putNumber(   "RawGyro_Z",            rawGyroZ);
		
		add(RawAccelX,nav.getRawAccelX());
		remove(RawAccelX);
		rawAccelX = ave(RawAccelX.toArray(new Float[RawAccelX.size()]));
		SmartDashboard.putNumber(   "RawAccel_X",           rawAccelX);
		
		add(RawAccelY,nav.getRawAccelY());
		remove(RawAccelY);
		rawAccelY = ave(RawAccelY.toArray(new Float[RawAccelY.size()]));
		SmartDashboard.putNumber(   "RawAccel_Y",           rawAccelY);
		
		add(RawAccelZ,nav.getRawAccelZ());
		remove(RawAccelZ);
		rawAccelZ = ave(RawAccelZ.toArray(new Float[RawAccelZ.size()]));
		SmartDashboard.putNumber(   "RawAccel_Z",           rawAccelZ);
		
		add(RawMagX,nav.getRawMagX());
		remove(RawMagX);
		rawMagX = ave(RawMagX.toArray(new Float[RawMagX.size()]));
		SmartDashboard.putNumber(   "RawMag_X",             rawMagX);
		
		add(RawMagY,nav.getRawMagY());
		remove(RawMagY);
		rawMagY = ave(RawMagY.toArray(new Float[RawMagY.size()]));
		SmartDashboard.putNumber(   "RawMag_Y",             rawMagY);
		
		add(RawMagZ,nav.getRawMagZ());
		remove(RawMagZ);
		rawMagZ = ave(RawMagZ.toArray(new Float[RawMagZ.size()]));
		SmartDashboard.putNumber(   "RawMag_Z",             rawMagZ);
		
		
	}
	
	/**
	 * @param array
	 * @return average value of Float array
	 */
	public double ave(Float[] array){
		double average = 0;
		double sum = 0;
		for(int i=0; i < array.length; i++){
			sum = sum + array[i];
		}
		average = sum/array.length;
		return average;
	}
	/**
	 * @param array
	 * @return average value of Double array
	 */
	public double ave(Double[] array){
		double average = 0;
		double sum = 0;
		for(int i=0; i < array.length; i++){
			sum = sum + array[i];
		}
		average = sum/array.length;
		return average;
	}
	
	/**
	 * @param queue is the queue you wish to pass,
	 * @param arrayData is the data you want to add
	 */
	public void add(ArrayBlockingQueue<Float> queue, float arrayData){
		if(queue.size() < aveLength+1){
			queue.add(arrayData);
		}
	}
	
	/**
	 * @param queue is the queue you wish to pass,
	 * @param arrayData is the data you want to add
	 */
	public void add(ArrayBlockingQueue<Double> queue, double arrayData){
		if(queue.size() < aveLength+1){
			queue.add(arrayData);
		}
	}
	
	
	/**
	 * @param queue is the queue you wish to pass,
	 */
	public void remove(ArrayBlockingQueue<?> queue){
		if(queue.size() > aveLength){
			queue.remove();
		}
	}
}
