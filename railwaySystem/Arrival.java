package railwaySystem;

import java.io.Serializable;

/**
* @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
* @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
*/
public interface Arrival extends Serializable {

	/**
	 * Get the arrival's station.
	 * @return station.
	 */
	Station getStation();
	
	/**
	 * Get the station's name.
	 * @return name.
	 */
	String getStationName();
	
	/**
	 * Get the arrival's date.
	 * @return date.
	 */
	TrainDate getDate();
	
	/**
	 * Get the arrival's date in a formatted manner.
	 * @return formatted date.
	 */
	String getFormattedDate();
	
}
