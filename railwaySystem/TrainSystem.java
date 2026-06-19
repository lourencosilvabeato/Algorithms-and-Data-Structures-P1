package railwaySystem;

import java.io.Serializable;

import dataStructures.Entry;
import dataStructures.Iterator;
import dataStructures.List;
import dataStructures.OrderedDictionary;
import exceptions.ExistentRailwayException;
import exceptions.ImpossibleRouteException;
import exceptions.InexistentRailwayException;
import exceptions.InexistentStartingStationException;
import exceptions.InexistentStationException;
import exceptions.InvalidScheduleException;

/**
* @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
* @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
*/
public interface TrainSystem extends Serializable {

	/**
	 * Registers a new railway into the system.
	 * @param railwayName: name of the railway.
	 * @param list: list of the railway's stations.
	 * @throws ExistentRailwayException if the railway already exists.
	 */
	void addRailway(String railwayName, List<String> list) throws ExistentRailwayException;
	
	/**
	 * Removes the given railway from the system.
	 * @param railwayName: name of the railway.
	 * @throws InexistentRailwayException if the railway doesn't already exist.
	 */
	void removeRailway(String railwayName) throws InexistentRailwayException;
	
	/**
	 * Lists all the stations of the given railway.
	 * @param railwayName: name of the railway.
	 * @throws InexistentRailwayException if the railway doesn't already exist.
	 * @return iterator.
	 */
	Iterator<Station> stationsIterator(String railwayName) throws InexistentRailwayException;
	
	/**
	 * Adds a new schedule to a given railway. The schedule is defined by its train and by its list of arrivals.
	 * @param railwayName: name of the railway.
	 * @param trainNumber: number of the train.
	 * @param newSchedule: key - name of the station // value - date of departure
	 * @param scheduleStations: list of stations from the schedule to add.
	 * @throws InexistentRailwayException if the railway doesn't already exist.
	 *	@throws InvalidScheduleException if:- there is at least one station from the list that doesn't belong to the railway.
	 *										- the first station of the given list is not a terminal station.
	 * 										- the schedule's stations are not in the same ordered as they are on the railway
	 * 										- the schedule's dates are not in ascending order.
	 */
	void addSchedule(String railwayName, int trainNumber, OrderedDictionary<String, TrainDate> newSchedule, List<String> scheduleStations) throws InexistentRailwayException, InvalidScheduleException;

	/**
	 * Removes a schedule from a given railway.
	 * @param railwayName: name of the railway.
	 * @param stationName: name of the first station of the schedule to remove.
	 * @param date: date of the departure of the schedule to remove.
	 * @throws InexistentRailwayException if the railway doesn't already exist.
	 * @throws InvalidScheduleException if: - the first station is not a terminal station
	 * 										- the schedule's stations are not in the same ordered as they are on the railway
	 * 										- the schedule's dates are not in ascending order.
	 */
	void removeSchedule(String railwayName, String stationName, TrainDate date) throws InexistentRailwayException, InvalidScheduleException;

	/**
	 * Get an iterator of each train's list of arrivals (that start in the specified station).
	 * @param railwayName: name of the railway.
	 * @param stationName: name of the first station of the schedule.
	 * @throws InexistentRailwayException if the railway doesn't already exist.
	 * @throws InexistentStartingStationException if the first station of the wanted schedule doesn't exist on the railway.
	 * @return iterator.
	 */
	Iterator<Entry<TrainDate, Schedule>> schedulesIterator(String railwayName, String stationName) throws InexistentStartingStationException;

	/**
	 * Get an iterator of each train's number (that start in the specified station).
	 * @param railwayName: name of the railway.
	 * @param stationName: name of the first station of the schedule to remove.
	 * @return iterator.
	 */
	Iterator<Entry<TrainDate, Integer>> trainNumberIterator(String railwayName, String stationName);

	/**
	 * Get an iterator of the best schedule (the one which arrives closest to the given arrival time)
	 *  of this railway's schedules.
	 * @param railwayName: name of the railway.
	 * @param firstStation: name of the first station of the schedule.
	 * @param lastStation: name of the first station of the schedule.
	 * @param arrivalTime: time to arrive at the destination.
	 * @throws InexistentRailwayException if the railway doesn't already exist.
	 * @throws InexistentStartingStationException if the first station of the wanted schedule doesn't exist on the railway.
	 * @throws ImpossibleRouteException if the last station of the wanted schedule doesn't exist on the railway.
	 * @return iterator.
	 */
	Iterator<Arrival> bestScheduleIterator(String railwayName, String firstStation, String lastStation, TrainDate arrivalTime) throws InexistentRailwayException, InexistentStartingStationException, ImpossibleRouteException;

	/**
	 * Get the best schedule's train number.
	 * @param railwayName: name of the railway.
	 * @param firstStation: name of the first station of the schedule.
	 * @param lastStation: name of the first station of the schedule.
	 * @param arrivalTime: time to arrive at the destination.
	 * @throws InexistentRailwayException if the railway doesn't already exist.
	 * @throws InexistentStartingStationException if the first station of the wanted schedule doesn't exist on the railway.
	 * @throws ImpossibleRouteException if the last station of the wanted schedule doesn't exist on the railway.
	 * @return train number.
	 */
	int bestScheduleTrain(String railwayName, String firstStation, String lastStation,TrainDate arrivalTime) throws InexistentRailwayException, InexistentStartingStationException, ImpossibleRouteException;

	/**
	 * Get an iterator of all of the railway names that include a certain station.
	 * @param stationName: name of the station.
	 * @throws InexistentStationException if the railway doesn't already exist.
	 * @return iterator.
	 */
	Iterator<String> railwaysIterator(String stationName) throws InexistentStationException;

	/**
	 * Get an iterator of all of the trains that pass through a certain station at a certain date.
	 * (iterator of entries that contain both dates and train number).
	 * @param stationName: name of the station.
	 * @throws InexistentStartingStationException if the first station of the wanted schedule doesn't exist on the railway.
	 * @return iterator.
	 */
	Iterator<Entry<TrainDate, List<Integer>>> trainsIterator(String stationName) throws InexistentStationException;

	/**
	 * Get an iterator of all the arrivals in a schedule.
	 * @param schedule: a schedule of a station.
	 * @return iterator.
	 */
	Iterator<Arrival> getArrivals(Schedule schedule);
	
	/**
	 * checks if station has any schedule associated.
	 * @param railwayName: railway to be checked
	 * @param stationName: station to be checked
	 * @return true if has schedule, false otherwise.
	 */
	boolean hasSchedule(String railwayName, String stationName) throws InexistentRailwayException;
}
