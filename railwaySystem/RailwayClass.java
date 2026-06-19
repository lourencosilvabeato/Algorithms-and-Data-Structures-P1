package railwaySystem;


import exceptions.*;

import java.io.Serializable;

import dataStructures.*;

public class RailwayClass implements UpdateRailway, Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	// station's list used to maintain insertion order
	private List<Station> stationsList;

	// key: station name; value: station object
	private OrderedDictionary<String, Station> stations;

	// key: starting station; value: station's list by departure date
	private OrderedDictionary<String, OrderedDictionary<TrainDate, Schedule>> schedules;

	// key: starting station; value: trainID by departure date
	private OrderedDictionary<String, OrderedDictionary<TrainDate, Integer>> trains;

	//constructor
	public RailwayClass(String name, List<Station> stationsList) {
		this.name = name;
		this.stationsList = stationsList;
		schedules = new BinarySearchTree<>();
		trains = new BinarySearchTree<>();
		stations = createOrderedDictionary(stationsList);
	}


	public String getName() {
		return name;
	}
	
	public boolean hasSchedule(String stationName) {
		return schedules.find(stationName) != null;
	}
	
	public Station getFirstStation() {
		return stationsList.get(0);
	}
	
	public Station getLastStation() {
		return stationsList.get(stationsList.size()-1);
	}

	public Iterator<Station> stationsIterator() {
		return stationsList.iterator();
	}

	/**
	 * Creates an ordered OrderedDictionary with the station name as the key
	 * and the correspondent station as the value.
	 * @param list: list of station to insert.
	 * @return stations OrderedDictionary.
	 */
	private OrderedDictionary<String, Station> createOrderedDictionary(List<Station> list) {

		OrderedDictionary<String, Station> stations = new BinarySearchTree<String, Station>();
		Iterator<Station> it = list.iterator();
		while (it.hasNext()) {
			Station s = it.next();
			stations.insert(s.getName(), s);
		}
		return stations;

	}

	public void addSchedule(int trainNumber, List<Arrival> arrivals) throws InvalidScheduleException {

		String firstStation = arrivals.get(0).getStation().getName();
		if (!isTerminal(firstStation) || !isStationValid(firstStation, arrivals)
				|| !isScheduleValid(firstStation, arrivals))
			throw new InvalidScheduleException();

		Schedule schedule = new ScheduleClass(arrivals);
		TrainDate date = arrivals.get(0).getDate();

		OrderedDictionary<TrainDate, Schedule> trainSchedule = schedules.find(firstStation);
		if (trainSchedule == null)
			trainSchedule = new BinarySearchTree<>();
		else if(trainSchedule.find(date) != null || schedulesOverlap(firstStation, arrivals, date, trainSchedule))
			throw new InvalidScheduleException();

		for(int i = 0; i < arrivals.size(); i++) {
			Station s = arrivals.get(i).getStation();
			TrainDate d = arrivals.get(i).getDate();
			((UpdateStation)s).addTrain(d, trainNumber);
		}

		trainSchedule.insert(date, schedule);
		schedules.insert(firstStation, trainSchedule);

		OrderedDictionary<TrainDate, Integer> trainsByArrival = trains.find(firstStation);
		if (trainsByArrival == null) {
			trainsByArrival = new BinarySearchTree<>();
		}
		trainsByArrival.insert(date, trainNumber);
		trains.insert(firstStation, trainsByArrival);
	}

	/**
	 * Checks if the addition of a new train schedule causes any overlap with existing schedules.
	 * @param firstStation: name of the first station of the new schedule.
	 * @param arrivals: list of arrivals for the new schedule.
	 * @param newDate: date of the new schedule.
	 * @param trainSchedule: ordered dictionary containing the existing train schedules.
	 * @return true if the new schedule overlaps with any existing schedule, false otherwise.
	 */
	private boolean schedulesOverlap(String firstStation, List<Arrival> arrivals, TrainDate newDate, OrderedDictionary<TrainDate, Schedule> trainSchedule) {
		boolean overlaps = false;

		List<TrainDate> dates = new OrderedList<>();
		Iterator<Entry<TrainDate, Schedule>> it = trainSchedule.iterator();
		while(it.hasNext()) {
			TrainDate d = it.next().getKey();
			dates.addLast(d);
		}

		dates.add(0, newDate);
		int pos = dates.find(newDate);
		TrainDate prev = null;
		if(pos != 0)
			prev = dates.get(pos-1);
		TrainDate next = null;
		if(pos != dates.size()-1)
			next = dates.get(pos+1);

		if(overlaps(-1, prev, trainSchedule, arrivals) || overlaps(1, next, trainSchedule, arrivals)) {
			dates.remove(newDate);
			overlaps = true;
		}

		return overlaps;
	}

	/**
	 * Checks for overlap between a new train schedule and an existing schedule.
	 * @param type: specifies whether to check the predecessor (-1) or the successor (1).
	 * @param date: date of the existing schedule to compare against.
	 * @param trainSchedule: ordered dictionary containing the existing train schedules.
	 * @param arrivals: list of arrivals for the new schedule.
	 * @return true if there is any overlap, false otherwise.
	 */
	private boolean overlaps(int type, TrainDate date, OrderedDictionary<TrainDate, Schedule> trainSchedule, List<Arrival> arrivals) {

		if(date == null)
			return false;

		Schedule s = trainSchedule.find(date);
		if(s == null)
			return false;
		Iterator<Arrival> newScheduleIt = arrivals.iterator();
		Iterator<Arrival> it = s.getArrivals();
		while(newScheduleIt.hasNext()) {
			Arrival newArrival = newScheduleIt.next();
			it.rewind();
			while(it.hasNext()) {
				Arrival arrival = it.next();
				if(newArrival.getStation().equals(arrival.getStation())) {
					if(type == -1) {
						if(!newArrival.getDate().isAfter(arrival.getDate()))
							return true;
					}
					else {
						if(!newArrival.getDate().isBefore(arrival.getDate()))
							return true;
					}
				}
				else
					continue;
			}		
		}
		return false;
	}

	/**
	 * Checks if the given station is a terminal station (first or last of the railway).
	 * @param firstStation: station to check.
	 * @return true if it is, false otherwise.
	 */
	private boolean isTerminal(String firstStation) {
		return firstStation.equals(stationsList.get(0).getName())
				|| firstStation.equals(stationsList.get(stations.size() - 1).getName());
	}

	/**
	 * Checks if the schedule's stations are in the same order as they are in the railway.
	 * @param stationName: first station's name.
	 * @param arrivals: list of stations to check.
	 * @return true if they are, false otherwise.
	 */
	private boolean isStationValid(String stationName, List<Arrival> arrivals) {

		boolean valid = true;
		if (stationsList.get(0).getName().equals(stationName)) {
			int i = 0;
			while (i < arrivals.size() - 1 && valid) {
				if (stationsList.find(arrivals.get(i).getStation()) > stationsList.find(arrivals.get(i + 1).getStation()))
					valid = false;
				i++;
			}
		} else {
			int i = arrivals.size() - 1;
			while (i > 0 && valid) {
				if (stationsList.find(arrivals.get(i).getStation()) > stationsList.find(arrivals.get(i - 1).getStation()))
					valid = false;
				i--;
			}
		}
		return valid;
	}

	/**
	 * Checks if the schedule's dates are in ascending order.
	 * @param stationName: first station's name.
	 * @param arrivals: list of stations to check.
	 * @return true if they are, false otherwise.
	 */
	private boolean isScheduleValid(String stationName, List<Arrival> arrivals) {

		int i = 0;
		boolean valid = true;
		if (arrivals.get(0).getStation().getName().equals(stationName)) {
			while (i < arrivals.size() - 1 && valid) {
				if (!arrivals.get(i).getDate().isBefore(arrivals.get(i + 1).getDate()))
					valid = false;
				i++;
			}
		} else {
			while (i < arrivals.size() - 1 && valid) {
				if (!arrivals.get(i).getDate().isAfter(arrivals.get(i + 1).getDate()))
					valid = false;
				i++;
			}
		}
		return valid;
	}


	public void removeSchedule(String stationName, TrainDate date) throws InvalidScheduleException {

		OrderedDictionary<TrainDate, Schedule> scheduleByArrival = schedules.find(stationName);

		if (scheduleByArrival == null || scheduleByArrival.find(date) == null)
			throw new InvalidScheduleException();
		OrderedDictionary<TrainDate, Integer> trainsByArrival = trains.find(stationName);
		
		Schedule schedule = scheduleByArrival.find(date);
		Iterator<Arrival> it = schedule.getArrivals();
		int ID = trainsByArrival.find(date);
		while(it.hasNext()) {
			Arrival a = it.next();
			Station s = a.getStation();
			TrainDate d = a.getDate();
			((UpdateStation)s).removeTrain(d, ID);
		}
		scheduleByArrival.remove(date);
		trainsByArrival.remove(date);
	
	}
	
	public void removeAllSchedulesFromStations(String stationName) {
		
		OrderedDictionary<TrainDate, Schedule> scheduleByArrival = schedules.find(stationName);
		OrderedDictionary<TrainDate, Integer> trainsByArrival = trains.find(stationName);
		if(scheduleByArrival == null || trainsByArrival == null)
			return;
		Iterator<Entry<TrainDate, Schedule>> it = scheduleByArrival.iterator();
		while(it.hasNext()) {
			Entry<TrainDate, Schedule> e = it.next();
			TrainDate date = e.getKey();
			Schedule s = e.getValue();
			Iterator<Arrival> it2 = s.getArrivals();
			int ID = trainsByArrival.find(date);
			while(it2.hasNext()) {
				Arrival a = it2.next();
				Station st = a.getStation();
				TrainDate d = a.getDate();
				((UpdateStation)st).removeTrain(d, ID);
			}
			
		}

	}

	public Iterator<Entry<TrainDate, Schedule>> schedulesIterator(String stationName) throws InexistentStartingStationException {

		if (!isTerminal(stationName))
			throw new InexistentStartingStationException();
		
		return schedules.find(stationName).iterator();
	
	}

	public Iterator<Entry<TrainDate, Integer>> trainNumberIterator(String stationName) {

		return trains.find(stationName).iterator();
	}

	/**
	 * Checks if a given station is before the other given station
	 * (in terms of position).
	 * @param firstStation: first station's name.
	 * @param lastStation: last station's name.
	 * @return true if the first station comes first on the OrderedDictionary, false otherwise.
	 */
	private boolean isBefore(String firstStation, String lastStation) {
		boolean foundFirst = false;
		boolean foundSecond = false;
		int i = 0;
		int idx1 = 0;
		int idx2 = 0;
		while (i < stations.size() && !foundFirst || !foundSecond) {
			if (stationsList.get(i).getName().equals(firstStation)) {
				idx1 = i;
				foundFirst = true;
			}
			if (stationsList.get(i).getName().equals(lastStation)) {
				idx2 = i;
				foundSecond = true;
			}
			i++;
		}

		return idx1 < idx2;
	}

	/**
	 * Searches for an arrival on a given list, based on its station's name.
	 * @param stationName: the arrival's station's name.
	 * @param schedule: list of arrivals to check.
	 * @return the arrival if it exists, null otherwise.
	 */
	private Arrival getArrival(Schedule schedule, String stationName) {
		Arrival a = null;
		Iterator<Arrival> it = schedule.getArrivals();
		while(it.hasNext() && a == null) {
			Arrival arrival = it.next();
			if(arrival.getStationName().equals(stationName))
				a = arrival;
		}
		return a;
	}

	/**
	 * Determines the best schedule (the one which arrives closest to the given arrival time)
	 * of this railway's schedules
	 * @param firstStation: first station's name.
	 * @param lastStation: last station's name.
	 * @param arrivalTime: time to arrive at the destination.
	 * @throws InexistentStartingStationException if the first station of the wanted schedule doesn't exist on this railway.
	 * @throws ImpossibleRouteException if: - the last station of the wanted schedule doesn't exist on the railway.
	 * 										- there are no schedules before wanted destination time.
	 * @return the departure date of the best schedule.
	 */
	private TrainDate bestSchedule(String firstStation, String lastStation, TrainDate arrivalTime)
			throws InexistentStartingStationException, ImpossibleRouteException {

		if (stations.find(firstStation) == null)
			throw new InexistentStartingStationException();
		if (stations.find(lastStation) == null)
			throw new ImpossibleRouteException();

		int minTimeDifference = Integer.MAX_VALUE;
		TrainDate departure = null;
		String station = stationsList.get(0).getName();
		if (!isBefore(firstStation, lastStation))
			station = stationsList.get(stations.size() - 1).getName();

		OrderedDictionary<TrainDate, Schedule> trainSchedule = schedules.find(station);
		Iterator<Entry<TrainDate, Schedule>> it = trainSchedule.iterator();
		while (it.hasNext()) {
			Entry<TrainDate, Schedule> entry = it.next();
			Schedule s = entry.getValue();
			if (getArrival(s, lastStation) == null)
				continue;
			int timeDif = getArrival(s, lastStation).getDate().timeDifference(arrivalTime);
			if (timeDif >= 0 && timeDif < minTimeDifference) {
				minTimeDifference = timeDif;
				departure = entry.getKey();
			}
		}
		if (arrivalTime.timeDifference(minTimeDifference) > 0)
			throw new ImpossibleRouteException();
		return departure;
	}

	public Iterator<Arrival> bestScheduleIterator(String firstStation, String lastStation, TrainDate arrivalTime)
			throws InexistentStartingStationException, ImpossibleRouteException {

		String station = stationsList.get(0).getName();
		if (!isBefore(firstStation, lastStation))
			station = stationsList.get(stations.size() - 1).getName();

		TrainDate departure = bestSchedule(firstStation, lastStation, arrivalTime);
		OrderedDictionary<TrainDate, Schedule> trainSchedule = schedules.find(station);
		Schedule s = trainSchedule.find(departure);
		return s.getArrivals();
	}

	public int bestScheduleTrain(String firstStation, String lastStation, TrainDate arrivalTime)
			throws InexistentStartingStationException, ImpossibleRouteException {

		String station = stationsList.get(0).getName();
		if (!isBefore(firstStation, lastStation))
			station = stationsList.get(stations.size() - 1).getName();

		TrainDate departure = bestSchedule(firstStation, lastStation, arrivalTime);
		OrderedDictionary<TrainDate, Integer> trainByDate = trains.find(station);
		int ID = trainByDate.find(departure);
		return ID;
	}

}