package railwaySystem;

import exceptions.*;

import java.io.Serializable;

import dataStructures.*;


/**
 * @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
 * @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
 */
public class TrainSystemClass implements TrainSystem, Serializable {

	private static final long serialVersionUID = 1L;

	// key: railway's name; value: railway
	private Dictionary<String, Railway> railways;

	// key: station's name; value: station
	private Dictionary<String, Station> stations;

	//constructor
	public TrainSystemClass() {
		railways = new SepChainHashTable<>();
		stations = new SepChainHashTable<>();
	}

	public void addRailway(String railwayName, List<String> list) throws ExistentRailwayException {

		Railway railway = railways.find(railwayName.toLowerCase());
		if(railway != null)
			throw new ExistentRailwayException();

		List<Station> stationsList = new DoubleList<>();
		for(int i=0; i<list.size(); i++) {
			String s = list.get(i);
			Station station = stations.find(s.toLowerCase());
			if(station == null) {
				station = new StationClass(s);
				stations.insert(s.toLowerCase(), station);
			}
			stationsList.addLast(station);
		}
		railway = new RailwayClass(railwayName, stationsList);
		railways.insert(railwayName.toLowerCase(), railway);
		
		for(int i = 0; i < stationsList.size(); i++)
			((UpdateStation)stationsList.get(i)).addRailway(railway);
	}

	public void removeRailway(String railwayName) throws InexistentRailwayException {


		Railway railway = railways.find(railwayName.toLowerCase());
		if(railway == null)
			throw new InexistentRailwayException();
		String firstStationName = railway.getFirstStation().getName();
		String lastStationName = railway.getLastStation().getName();
		((UpdateRailway)railway).removeAllSchedulesFromStations(firstStationName);
		((UpdateRailway)railway).removeAllSchedulesFromStations(lastStationName);
		Iterator<Station> it = railway.stationsIterator();
		while(it.hasNext()) {
			Station s = it.next();
			if(s.getNrOfRailways() == 1)
				stations.remove(s.getName().toLowerCase());
			((UpdateStation)s).removeRailway(railway);
		}
		railways.remove(railwayName.toLowerCase());
	}

	public Iterator<Station> stationsIterator(String railwayName) throws InexistentRailwayException {

		Railway railway = railways.find(railwayName.toLowerCase());
		if(railway == null)
			throw new InexistentRailwayException();

		return railway.stationsIterator();
	}

	public void addSchedule(String railwayName, int trainNumber, OrderedDictionary<String, TrainDate> newSchedule, List<String> scheduleStations)
			throws InexistentRailwayException, InvalidScheduleException {

		Railway railway = railways.find(railwayName.toLowerCase());
		if(railway == null)
			throw new InexistentRailwayException();	
		List<Arrival> arrivals = new DoubleList<>();
		for(int i = 0; i < newSchedule.size(); i++) {
			String s = scheduleStations.get(i);
			Station station = stations.find(s.toLowerCase());
			if(station == null)
				throw new InvalidScheduleException();
			TrainDate d = newSchedule.find(s);
			Arrival a = new ArrivalClass(station, d);
			arrivals.addLast(a);
		}
		((UpdateRailway)railway).addSchedule(trainNumber, arrivals);
	}

	public void removeSchedule(String railwayName, String stationName, TrainDate date) throws InexistentRailwayException, InvalidScheduleException {

		Railway railway = railways.find(railwayName.toLowerCase());
		if(railway == null)
			throw new InexistentRailwayException();
		((UpdateRailway)railway).removeSchedule(stationName, date);
	}

	public Iterator<Entry<TrainDate, Schedule>> schedulesIterator(String railwayName, String stationName) throws InexistentStartingStationException {
		Railway railway = railways.find(railwayName.toLowerCase());
		return railway.schedulesIterator(stationName);
	}

	public Iterator<Entry<TrainDate, Integer>> trainNumberIterator(String railwayName, String stationName) {
		Railway railway = railways.find(railwayName.toLowerCase());
		return railway.trainNumberIterator(stationName);
	}

	public Iterator<Arrival> bestScheduleIterator(String railwayName, String firstStation, String lastStation,
			TrainDate arrivalTime) throws InexistentStartingStationException, InexistentRailwayException,ImpossibleRouteException {
		Railway railway = railways.find(railwayName.toLowerCase());
		if(railway == null)
			throw new InexistentRailwayException();
		return railway.bestScheduleIterator(firstStation, lastStation, arrivalTime);
	}

	public int bestScheduleTrain(String railwayName, String firstStation, String lastStation,TrainDate arrivalTime) throws InexistentStartingStationException, ImpossibleRouteException, InexistentRailwayException {
		Railway railway = railways.find(railwayName.toLowerCase());
		if(railway == null)
			throw new InexistentRailwayException();
		return railway.bestScheduleTrain(firstStation, lastStation, arrivalTime);
	}
	
	public 	Iterator<String> railwaysIterator(String stationName) throws InexistentStationException {
	
		Station station = stations.find(stationName.toLowerCase());
		if(station == null)
			throw new InexistentStationException();
		return station.railwaysIterator();
	}
	
	public Iterator<Entry<TrainDate, List<Integer>>> trainsIterator(String stationName) throws InexistentStationException {
		Station station = stations.find(stationName.toLowerCase());
		if(station == null)
			throw new InexistentStationException();
		return station.trainsIterator();
	}
	
	public Iterator<Arrival> getArrivals(Schedule schedule) {
		return schedule.getArrivals();
	}
	
	public boolean hasSchedule(String railwayName, String stationName) throws InexistentRailwayException {
		Railway railway = railways.find(railwayName.toLowerCase());
		if(railway == null)
			throw new InexistentRailwayException();
		return railway.hasSchedule(stationName);
	}

}
