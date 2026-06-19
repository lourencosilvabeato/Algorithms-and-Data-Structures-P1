package railwaySystem;

import java.io.Serializable;

import dataStructures.Entry;
import dataStructures.Iterator;
import dataStructures.List;

/**
* @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
* @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
*/
public interface Station extends Serializable {

	/**
	 * Get the station's name.
	 * @return name.
	 */
	String getName();
	

	/**
	 * Get an iterator of railways of a station.
	 * @return iterator.
	 */
	Iterator<String> railwaysIterator();
	
	/**
	 * Get an iterator of all trains that pass by this station at a certain date.
	 * @return iterator.
	 */
	Iterator<Entry<TrainDate, List<Integer>>> trainsIterator();
	
	/**
	 * Get number of railways that include this station.
	 * @return number of railways.
	 */
	int getNrOfRailways();
}
