package org.usfirst.frc.team4215.robot;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.vision.VisionRunner;;

/**
 * Processes Pipeline data
 * @author waweros
 *
 */
public class CameraPID implements PIDSource, VisionRunner.Listener<Pipeline> {
	
	int IMG_WIDTH = 320;
	int IMG_HEIGHT = 240;
	private final Object imgLock = new Object();
	Thread visionThread;
	double turn;
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}
	
	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}
	
	/**
	 * Returns the difference between the center
	 * of the image and the center of the contour scaled
	 * so that the maximum is 1
	 * @return turn
	 */
	@Override
	public synchronized double pidGet() {		
		return turn;
	}
	
	
	/**
	 * The Pipeline processer method
	 * @param pipeline
	 */
	@Override
	public void copyPipelineOutputs(Pipeline pipeline) {
		// TODO Auto-generated method stub
		double centerX;
		 if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            synchronized (imgLock) {
	                centerX = r.x + (r.width / 2);
	                double offSet = centerX - (IMG_WIDTH / 2);
	        		turn = offSet/IMG_WIDTH;
	                System.out.println(offSet); 	//if the code is actually working,
	              //  System.out.println("Current Center X variable");        //a number should be displayed
	            }
	        }
	      else {
	    	  System.out.println("No Contours");
	      }
	}

}
