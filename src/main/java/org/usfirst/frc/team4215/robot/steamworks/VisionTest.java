package main.java.org.usfirst.frc.team4215.robot.steamworks;
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
import main.java.org.usfirst.frc.team4215.robot.steamworks.Pipeline;

public class VisionTest {

	private static final int IMG_WIDTH = 640;
	private static final int IMG_HEIGHT = 480;
	// Sets Camera Image Resolution
	
	private VisionThread visionThread;			//Creates Vision Thread for future use
	private double centerX = 0.0;			//Creates the variable centerX. 
	
	private final Object imgLock = new Object();
	public Object visionStop;
	
	AxisCamera camera;
	
	public void visionInit() {
		CameraServer server = CameraServer.getInstance();
		camera = server.addAxisCamera("10.42.15.15");
	   // server.startAutomaticCapture();	//Begins getting video from the camera
	    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    
	    visionThread = new VisionThread(camera, new Pipeline(), pipeline -> {
	    	
			CvSink cvSink = CameraServer.getInstance().getVideo();
			CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 640, 480);
			Mat mat = new Mat();
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
		visionThread.setDaemon(true);

	        	    	
		}
	
	public void visionStart(){
		visionThread.start();
	}
	public void visionStop() throws InterruptedException{
		//visionThread.join();
		//visionThread.wait(30);
	}
	
}
	

