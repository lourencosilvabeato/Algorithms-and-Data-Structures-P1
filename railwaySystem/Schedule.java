package railwaySystem;

import java.io.Serializable;

import dataStructures.Iterator;


/**
* @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
* @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
*/
public interface Schedule extends Serializable {

	/**
	 * Get an iterator of arrivals of a schedule.
	 * @return iterator.
	 */
	Iterator<Arrival> getArrivals();
}
