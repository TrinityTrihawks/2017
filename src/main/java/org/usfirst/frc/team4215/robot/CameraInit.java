package main.java.org.usfirst.frc.team4215.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionThread;
import main.java.org.usfirst.frc.team4215.robot.Pipeline;

public class CameraInit implements Runnable {
	private VisionThread visionThread;			//Creates Vision Thread for future use
	private double centerX = 0.0;			//Creates the variable centerX. 
	
	private final Object imgLock = new Object();

	public CameraInit() {
		
	}

	@Override
	public void run() {
		// Get the Axis camera from CameraServer
		//AxisCamera camera = CameraServer.getInstance().addAxisCamera("Front", "10.42.15.39");
		// Set the resolution
		//camera.setResolution(640, 480);
					
		
		AxisCamera cameraBack = CameraServer.getInstance().addAxisCamera("Back", "10.42.15.37");
		// Set the resolution
		cameraBack.setResolution(640, 480);
				
		// Get a CvSink. This will capture Mats from the camera
		CvSink cvSink = CameraServer.getInstance().getVideo();
		// Setup a CvSource. This will send images back to the Dashboard
		CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 640, 480);

		// Mats are very memory expensive. Lets reuse this Mat.
		Mat mat = new Mat();
	    visionThread = new VisionThread(cameraBack, new Pipeline(), pipeline -> {

		 if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            synchronized (imgLock) {
	                centerX = r.x + (r.width / 2);	                	                
	                System.out.println(centerX); 	//if the code is actually working,
	                System.out.println("Current Center X variable");          //a number should be displayed
	            }
	        }
	      else {
	    	  System.out.println("No Contours");
	      }
	    });

		// This cannot be 'true'. The program will never exit if it is. This
		// lets the robot stop this thread when restarting robot code or
		// deploying.
		while (!Thread.interrupted()) {
			// Tell the CvSink to grab a frame from the camera and put it
			// in the source mat.  If there is an error notify the output.
			if (cvSink.grabFrame(mat) == 0) {
				// Send the output the error.
				outputStream.notifyError(cvSink.getError());
				// skip the rest of the current iteration
				continue;
			}
			// Put a rectangle on the image
			Imgproc.rectangle(mat, new Point(100, 100), new Point(400, 400),
					new Scalar(255, 255, 255), 5);
			// Give the output stream a new image to display
			outputStream.putFrame(mat);
		}

	}

}
