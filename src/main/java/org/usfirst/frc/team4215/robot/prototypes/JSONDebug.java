package org.usfirst.frc.team4215.robot.prototypes;

import org.json.JSONObject;

/**
 * Interface requiring the debug method so that it can be utilized by the Debugger class.
 * @author Jack Rausch
 *
 */
public interface JSONDebug {

	/**
	 * This method allows us to access and log private variables for debugging purposes.
	 * Put the variables in a JSON document.
	 * @see <a href="JSON Tutorial">http://www.studytrails.com/java/json/java-org-json/</a>
	 * @author Jack Rausch
	 * @return JSONObject
	 */
	JSONObject debug();
	
}
