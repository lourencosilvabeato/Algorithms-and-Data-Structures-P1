package railwaySystem;


import java.io.Serializable;

import dataStructures.Entry;
import dataStructures.Iterator;
import exceptions.ImpossibleRouteException;
import exceptions.InexistentStartingStationException;

/**
 * @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
 * @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
 */
public interface Railway extends Serializable {
	
	/**
	 * Get the railway's name.
	 * @return name.
	 */
	String getName();
	
	/**
	 * checks if station has any schedule associated.
	 * @param stationName: station to be checked
	 * @return true if has schedule, false otherwise.
	 */
	boolean hasSchedule(String stationName);
	
	/**
	 * Get the first station in a railway.
	 * @return station.
	 */
	Station getFirstStation();
	
	/**
	 * Get the last station in a railway.
	 * @return station.
	 */
	Station getLastStation();

	/**
	 * Get an iterator of all the stations belonging to the railway.
	 * @return iterator.
	 */
	Iterator<Station> stationsIterator();

	/**
	 * Get an iterator of each train's list of arrivals (that start in the specified station).
	 * @param stationName: name of the first station of the schedule.
	 * @throws InexistentStartingStationException if the first station of the wanted schedule doesn't exist on this railway.
	 * @return iterator.
	 */
	Iterator<Entry<TrainDate, Schedule>> schedulesIterator(String stationName) throws InexistentStartingStationException;

	/**
	 * Get an iterator of each train's number (that start in the specified station).
	 * @param stationName: name of the first station of the schedule.
	 * @return iterator.
	 */
	Iterator<Entry<TrainDate, Integer>> trainNumberIterator(String stationName);

	/**
	 * Get an iterator of the best schedule (the one which arrives closest to the given arrival time)
	 * of this railway's schedules.
	 * @param firstStation: name of the first station of the schedule.
	 * @param lastStation: name of the first station of the schedule.
	 * @param arrivalTime: time to arrive at the destination.
	 * @throws InexistentStartingStationException if the first station of the wanted schedule doesn't exist on the railway.
	 * @throws ImpossibleRouteException if: - the last station of the wanted schedule doesn't exist on the railway.
	 * 										- there are no schedules before wanted destination time.	 * @return iterator.
	 */
	Iterator<Arrival> bestScheduleIterator(String firstStation, String lastStation, TrainDate arrivalTime) throws InexistentStartingStationException, ImpossibleRouteException;

	/**
	 * Get the best schedule's train number.
	 * @param firstStation: name of the first station of the schedule.
	 * @param lastStation: name of the first station of the schedule.
	 * @param arrivalTime: time to arrive at the destination.
	 * @throws InexistentStartingStationException if the first station of the wanted schedule doesn't exist on the railway.
	 * @throws ImpossibleRouteException if: - the last station of the wanted schedule doesn't exist on the railway.
	 * 										- there are no schedules before wanted destination time.	 * @return train number.
	 */
	int bestScheduleTrain(String firstStation, String lastStation, TrainDate arrivalTime) throws InexistentStartingStationException, ImpossibleRouteException;


}
