package org.usfirst.frc.team1891.visionsystem;


import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

public class OpenCVProcessing 
{
	String addr ="10.18.91.21";




	//	public void setAddress() {
	//		VC = new VideoCapture();
	//		try {
	//			Thread.sleep(100);
	//		} catch (InterruptedException e) {
	//			e.printStackTrace();
	//		}
	//		Runtime.getRuntime().addShutdownHook(new Shutdownhook());
	//	}

	public Mat getImage() {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat image = new Mat();
		
		VideoCapture VC = new VideoCapture();
		VC.open("http://10.18.91.21/view/viewer_index.shtml?id=0");
		if (VC.isOpened()) {
			System.out.println("Video is captured");
		}
		VC.read(image);

//		Imgproc.resize(image, image, new Size(480, 360));
		File outputfile = new File("initial.png");
//		try {
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return image;
	}

//	private static class Shutdownhook extends Thread {
//		public void run() {
//			System.out.println("Shutting down");
//			VC.release();
//
//		}
//	}

	public void start() {
	}

	public Mat filterHSV() {
		Mat img = getImage();
		Mat out = new Mat();
//		System.out.println(img.);
		
		// Converts the color from BGR to HSV
		Imgproc.cvtColor(img, out, Imgproc.COLOR_BGR2HSV_FULL); // Will

		Core.inRange(out, new Scalar(84, 0, 96), new Scalar(180, 255, 255), out);
		File outputfile = new File("saved.png");
//		try {
//			ImageIO.write((RenderedImage) out, "png", outputfile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return out;
	}

}
