package org.usfirst.frc.team1891.visionsystem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.wpilibj.vision.AxisCamera;

public class Test 
{
	AxisCamera cam = new AxisCamera("10.18.91.21");
	
	public void testingStuff()
	{
		try {
			BufferedImage image = ImageIO.read((ImageInputStream) cam.getImage());
			
			byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	         Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	         mat.put(0, 0, data);

	         Mat mat1 = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC1);
	         Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2GRAY);

	         byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int)(mat1.elemSize())];
	         mat1.get(0, 0, data1);
	         BufferedImage image1 = new BufferedImage(mat1.cols(),mat1.rows(), BufferedImage.TYPE_BYTE_GRAY);
	         image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);

	         File ouptut = new File("grayscale.jpg");
	         ImageIO.write(image1, "jpg", ouptut);
	         
		} catch(Exception e)
		{
			
		}
	}

}
