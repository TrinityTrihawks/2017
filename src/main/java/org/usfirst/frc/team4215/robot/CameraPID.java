package main.java.org.usfirst.frc.team4215.robot;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class CameraPID implements PIDSource, Runnable {
	AxisCamera cameraBack = CameraServer.getInstance().addAxisCamera("Back", "10.42.15.37");
	private double centerX = 0.0;			//Creates the variable centerX. 
	int IMG_WIDTH = 640;
	int IMG_HEIGHT = 480;
	private final Object imgLock = new Object();
	Thread visionThread;
	double turn;

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
	}

	@Override
	public synchronized double pidGet() {		
		return turn;
	}

	@Override
	public void run() {
		cameraBack = CameraServer.getInstance().addAxisCamera("Back", "10.42.15.37");
		cameraBack.setResolution(IMG_WIDTH, IMG_HEIGHT);
		
		visionThread = new VisionThread(cameraBack, new Pipeline(), pipeline -> {

			 if (!pipeline.filterContoursOutput().isEmpty()) {
		            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
		            synchronized (imgLock) {
		                centerX = r.x + (r.width / 2);
		                double offSet = centerX - (IMG_WIDTH / 2);
		        		turn = offSet/IMG_WIDTH;
		                //System.out.println(centerX); 	//if the code is actually working,
		                //System.out.println("Current Center X variable");          //a number should be displayed
		            }
		        }
		      else {
		    	  System.out.println("No Contours");
		      }
		    });
		visionThread.setDaemon(true);
		
	}

}
