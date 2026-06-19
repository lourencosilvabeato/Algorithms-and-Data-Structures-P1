package railwaySystem;

import java.io.Serializable;

/**
* @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
* @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
*/
public interface UpdateStation extends Station, Serializable {

	/**
	 * Adds a railway name to the list of railways of this station.
	 * @param railwayName: name of the railway to add.
	 */
	void addRailway(Railway railway);

	/**
	 * Removes a railway name to the list of railways of this station.
	 * @param railwayName: name of the railway to remove.
	 */
	void removeRailway(Railway railway);
	
	/**
	 * Adds a train by it's number defined by its departure date to the train's dictionary.
	 * @param date: date of departure.
	 * @param trainNumber: number of the train to add.
	 */
	void addTrain(TrainDate date, int trainNumber);

	/**
	 * Removes a train by departure date from the train's dictionary.
	 * @param date: date of departure.
	 * @param trainNumber: number of the train to remove.
	 */
	void removeTrain(TrainDate date, int trainNumber);
}
