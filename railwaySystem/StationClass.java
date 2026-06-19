package railwaySystem;

import dataStructures.*;

/**
 * @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
 * @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
 */
public class StationClass implements UpdateStation {

	private static final long serialVersionUID = 1L;

	private String name;
	private List<String> railways;
	//key: date of departure; value: number of the train
	private OrderedDictionary<TrainDate, List<Integer>> trains;

	//constructor
	public StationClass(String name) {
		this.name = name;
		railways = new OrderedList<>();
		trains = new BinarySearchTree<>();
	}

	public String getName() {
		return name;
	}

	public void addRailway(Railway railway) {
		railways.add(0, railway.getName());
	}

	public void removeRailway(Railway railway) {
		railways.remove( railway.getName());
	}

	public Iterator<String> railwaysIterator() {
		return railways.iterator();
	}

	public void addTrain(TrainDate date, int trainNumber) {
		List<Integer> trainsList = trains.find(date);
		if(trainsList == null)
			trainsList = new OrderedList<>();
		
		trainsList.add(0, trainNumber);
		trains.insert(date, trainsList);
	}
	
	public void removeTrain(TrainDate date, int trainNumber) {
		List<Integer> trainsList = trains.find(date);		
		int i = 0;
		boolean found = false;
		while(!found && i < trainsList.size()) {
			if(trainsList.get(i) == trainNumber) {
				trainsList.remove(i);
				found = true;
			}
			i++;
		}
		if(trainsList.size() == 0) 
			trains.remove(date);
			
	}

	public Iterator<Entry<TrainDate, List<Integer>>> trainsIterator() {	
		return trains.iterator();
	}
	
	public int getNrOfRailways() {
		return railways.size();
	}

}
