package org.usfirst.frc.team1891.navx;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
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
	 ArrayBlockingQueue<Float> Yaw = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Double> Angle = new ArrayBlockingQueue<Double>(aveLength);
	 ArrayBlockingQueue<Float> WorldLinearAccelX = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> WorldLinearAccelY = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> VelocityX = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> VelocityY = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> DisplacementX = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> DisplacementY = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> RawGyroX = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> RawGyroY = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> RawGyroZ = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> RawAccelX = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> RawAccelY = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> RawAccelZ = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> RawMagX = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> RawMagY = new ArrayBlockingQueue<Float>(aveLength);
	 ArrayBlockingQueue<Float> RawMagZ = new ArrayBlockingQueue<Float>(aveLength);
	 
	 
	 
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
	double rawMagZ;;
	 
	 
	 /**
	 * @param system the AHRS system being used.
	 */
	public NavXSubsystem(AHRS system){
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
		Yaw.add(nav.getYaw());
		Yaw.remove();
		yaw = ave(Yaw.toArray(new Float[Yaw.size()]));
		
		Angle.add(nav.getAngle());
		Angle.remove();
		angle = ave(Angle.toArray(new Double[Angle.size()]));
		
		WorldLinearAccelX.add(nav.getWorldLinearAccelX());
		WorldLinearAccelX.remove();
		worldLinearAccelX = ave(WorldLinearAccelX.toArray(new Float[WorldLinearAccelX.size()]));

		WorldLinearAccelY.add(nav.getWorldLinearAccelY());
		WorldLinearAccelY.remove();
		worldLinearAccelY = ave(WorldLinearAccelX.toArray(new Float[WorldLinearAccelY.size()]));
		
		moving = nav.isMoving();
		
		VelocityX.add(nav.getVelocityX());
		VelocityX.remove();
		velocityX = ave(VelocityX.toArray(new Float[VelocityX.size()]));
		
		VelocityY.add(nav.getVelocityY());
		VelocityY.remove();
		velocityY = ave(VelocityY.toArray(new Float[VelocityY.size()]));
		
		DisplacementX.add(nav.getDisplacementX());
		DisplacementX.remove();
		displacementX = ave(DisplacementX.toArray(new Float[DisplacementX.size()]));
		
		DisplacementY.add(nav.getDisplacementY());
		DisplacementY.remove();
		displacementY = ave(DisplacementY.toArray(new Float[DisplacementY.size()]));
		
		RawGyroX.add(nav.getRawGyroX());
		RawGyroX.remove();
		rawGyroX = ave(RawGyroX.toArray(new Float[RawGyroX.size()]));
		
		RawGyroY.add(nav.getRawGyroY());
		RawGyroY.remove();
		rawGyroY = ave(RawGyroY.toArray(new Float[RawGyroY.size()]));
		
		RawGyroZ.add(nav.getRawGyroZ());
		RawGyroZ.remove();
		rawGyroZ = ave(RawGyroZ.toArray(new Float[RawGyroZ.size()]));
		
		RawAccelX.add(nav.getRawAccelX());
		RawAccelX.remove();
		rawAccelX = ave(RawAccelX.toArray(new Float[RawAccelX.size()]));
		
		RawAccelY.add(nav.getRawAccelY());
		RawAccelY.remove();
		rawAccelY = ave(RawAccelY.toArray(new Float[RawAccelY.size()]));
		
		RawAccelZ.add(nav.getRawAccelZ());
		RawAccelZ.remove();
		rawAccelZ = ave(RawAccelZ.toArray(new Float[RawAccelZ.size()]));
		
		RawMagX.add(nav.getRawMagX());
		RawMagX.remove();
		rawMagX = ave(RawMagX.toArray(new Float[RawMagX.size()]));
		
		RawMagY.add(nav.getRawMagY());
		RawMagY.remove();
		rawMagY = ave(RawMagY.toArray(new Float[RawMagY.size()]));
		
		RawMagZ.add(nav.getRawMagZ());
		RawMagZ.remove();
		rawMagZ = ave(RawMagZ.toArray(new Float[RawMagZ.size()]));
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
}
