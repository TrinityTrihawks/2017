package main.java.org.usfirst.frc.team4215.robot.steamworks;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.AxisCamera;
//import com.ctre.CANTalon;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.vision.VisionThread;
import main.java.org.usfirst.frc.team4215.robot.steamworks.Pipeline;

public class VisionTest {

	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	// Sets Camera Image Resolution
	
	private VisionThread visionThread;			//Creates Vision Thread for future use
	private double centerX = 0.0;			//Creates the variable centerX. 
	
	private final Object imgLock = new Object();
	public Object visionStop;
	
	AxisCamera camera;
	
	public void visionInit() {
		CameraServer server = CameraServer.getInstance();
		camera = server.addAxisCamera("10.42.15.15");
	    server.startAutomaticCapture();	//Begins getting video from the camera
	    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    
	    visionThread = new VisionThread(camera, new Pipeline(), pipeline -> {
	        if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            synchronized (imgLock) {
	                centerX = r.x + (r.width / 2);
	                System.out.println(centerX); 	//if the code is actually working,
	                System.out.println("Current Center X variable");          //a number should be displayed
	            }
	        }
	    });
	    
	        	    	
		}
	
	public void visionStart(){
		visionThread.start();
	}
	@SuppressWarnings("deprecation")
	public void visionStop(){
		visionThread.stop();
	}
	
}
	

