package railwaySystem;

import java.io.Serializable;

import dataStructures.List;
import exceptions.InvalidScheduleException;

public interface UpdateRailway extends Railway, Serializable {

	
	/**
	 * Adds the new schedule to this railway. The schedule is defined by its train and by its list of arrivals.
	 * @param trainNumber: number of the train.
	 * @param arrivals: list of stations from the schedule to add.
	 *	@throws InvalidScheduleException if:- there is at least one station from the list that doesn't belong to the railway.
	 *										- the first station of the given list is not a terminal station.
	 * 										- the schedule's stations are not in the same ordered as they are on the railway
	 * 										- the schedule's dates are not in ascending order.
	 */
	void addSchedule(int trainNumber, List<Arrival> arrivals) throws InvalidScheduleException;

	/**
	 * Removes the schedule from this railway.
	 * @param stationName: name of the first station of the schedule to remove.
	 * @param date: date of the departure of the schedule to remove.
	 * @throws InvalidScheduleException if: - the first station is not a terminal station
	 * 										- the schedule's stations are not in the same ordered as they are on the railway
	 * 										- the schedule's dates are not in ascending order.
	 */
	void removeSchedule(String stationName, TrainDate date) throws InvalidScheduleException;
	
	/**
	 * Removes all schedules that start with a certain station.
	 * @param stationName: name of the first station of the schedule to remove.
	 */
	void removeAllSchedulesFromStations(String stationName);

}
