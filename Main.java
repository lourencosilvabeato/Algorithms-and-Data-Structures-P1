import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import exceptions.*;
import dataStructures.*;
import railwaySystem.*;

/**
* @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
* @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
*/
public class Main {

	// user's commands
	private static final String IL = "IL";
	private static final String RL = "RL";
	private static final String CL = "CL";
	private static final String IH = "IH";
	private static final String RH = "RH";
	private static final String CH = "CH";
	private static final String MH = "MH";
	private static final String TA = "TA";
	private static final String CE = "CE";
	private static final String LC = "LC";

	//file to upload
	private static final String DATA_FILE = "file.data";

	//program's feedback
	private static final String TERMINATED = "Aplicação terminada.";
	private static final String RAILWAY_ADDED = "Inserção de linha com sucesso.";
	private static final String EXISTENT_RAILWAY = "Linha existente.";
	private static final String INEXISTENT_RAILWAY = "Linha inexistente.";
	private static final String SUCCESSFULLY_REMOVED_RAILWAY = "Remoção de linha com sucesso.";
	private static final String SCHEDULE_ADDED = "Criação de horário com sucesso.";
	private static final String INVALID_SCHEDULE = "Horário inválido.";
	private static final String INEXISTENT_SCHEDULE = "Horário inexistente.";
	private static final String INEXISTENT_STARTING_STATION = "Estação de partida inexistente.";
	private static final String IMPOSSIBLE_ROUTE = "Percurso impossível.";
	private static final String SUCCESSFULLY_REMOVED_SCHEDULE = "Remoção de horário com sucesso.";
	private static final String INEXISTENT_STATION = "Estação inexistente.";



	/**
	 * Command interpreter.
	 */
	public static void main(String[] args) {

		TrainSystem trainSystem = load();
		Scanner in = new Scanner(System.in);
		String cmd = "";
		do {
			cmd = in.next().toUpperCase();
			switch (cmd) {
			case IL -> addRailway(in, trainSystem);
			case RL -> removeRailway(in, trainSystem);
			case CL -> listStations(in, trainSystem);
			case IH -> addSchedule(in, trainSystem);
			case RH -> removeSchedule(in, trainSystem);
			case CH -> checkSchedule(in, trainSystem);
			case MH -> bestSchedule(in, trainSystem);
			case CE -> listRailways(in, trainSystem);
			case LC -> listTrains(in, trainSystem);
			case TA -> System.out.println(TERMINATED);
			}

		} while (!cmd.equals(TA));
		save(trainSystem);
	}

	/**
	 * Registers a new railway into the system.
	 */
	private static void addRailway(Scanner in, TrainSystem trainSystem) {
		try {
			String railwayName = in.nextLine().trim();
			String stationName = "";
			List<String> list = new DoubleList<>();
			while(true) {
				stationName = in.nextLine();
				if(stationName.isEmpty())
					break;
				list.addLast(stationName);
			}
			trainSystem.addRailway(railwayName, list);
			System.out.println(RAILWAY_ADDED);
		}
		catch (ExistentRailwayException e) {
			System.out.println(EXISTENT_RAILWAY);
		}	
	}

	/**
	 * Removes a railway from the system.
	 */
	private static void removeRailway(Scanner in, TrainSystem trainSystem) {

		try {
			String railwayName = in.nextLine().trim();
			trainSystem.removeRailway(railwayName);
			System.out.println(SUCCESSFULLY_REMOVED_RAILWAY);
		}
		catch(InexistentRailwayException e) {
			System.out.println(INEXISTENT_RAILWAY);
		}	
	}

	/**
	 * Lists all the stations of a given railway.
	 */
	private static void listStations(Scanner in, TrainSystem trainSystem) {

		try {
			String railwayName = in.nextLine().trim();
			Iterator<Station> it = trainSystem.stationsIterator(railwayName);
			while(it.hasNext()) {
				Station station = it.next();
				System.out.println(station.getName());
			}
		}
		catch(InexistentRailwayException e) {
			System.out.println(INEXISTENT_RAILWAY);
		}	
	}

	/**
	 * Adds a new schedule to a given railway.
	 */
	private static void addSchedule(Scanner in, TrainSystem trainSystem) {

		try {
			String railwayName = in.nextLine().trim();
			int trainNumber = in.nextInt();
			in.nextLine();
			String line = "";
			OrderedDictionary<String, TrainDate> newSchedule = new BinarySearchTree<>();
			List<String> scheduleStations = new DoubleList<>();
			while(true) {
				line = in.nextLine();
				if(line.isEmpty())
					break;				
				String[] arr = line.split(" ");
				String time = arr[arr.length-1];
				arr[arr.length-1] = "";
				String stationName = String.join(" ", arr).trim();
				scheduleStations.addLast(stationName);
				String[] timeSplit = time.split(":");
				int hour = Integer.parseInt(timeSplit[0]);
				int minute = Integer.parseInt(timeSplit[1]);
				TrainDate date = TrainDateClass.of(hour, minute);
				newSchedule.insert(stationName, date);
			}
			trainSystem.addSchedule(railwayName, trainNumber, newSchedule, scheduleStations);
			System.out.println(SCHEDULE_ADDED);
		}
		catch(InexistentRailwayException e) {
			System.out.println(INEXISTENT_RAILWAY);
		}
		catch(InvalidScheduleException e) {
			System.out.println(INVALID_SCHEDULE);
		}
	}

	/**
	 * Removes a schedule from a given railway.
	 */
	private static void removeSchedule(Scanner in, TrainSystem trainSystem) {

		try {
			String railwayName = in.nextLine().trim();
			String info = in.nextLine();
			String[] arr = info.split(" ");
			String time = arr[arr.length-1];
			arr[arr.length-1] = "";
			String stationName = String.join(" ", arr).trim();
			String[] timeSplit = time.split(":");
			int hour = Integer.parseInt(timeSplit[0]);
			int minute = Integer.parseInt(timeSplit[1]);
			TrainDate date = TrainDateClass.of(hour, minute);
			trainSystem.removeSchedule(railwayName, stationName, date);
			System.out.println(SUCCESSFULLY_REMOVED_SCHEDULE);
		}
		catch(InexistentRailwayException e) {
			System.out.println(INEXISTENT_RAILWAY);
		}
		catch(InvalidScheduleException e) {
			System.out.println(INEXISTENT_SCHEDULE);
		}
	}

	/**
	 * Lists the schedule of a given railway.
	 */
	private static void checkSchedule(Scanner in, TrainSystem trainSystem) {

		try {
			String railwayName = in.nextLine().trim();
			String stationName = in.nextLine().trim();

			if(trainSystem.hasSchedule(railwayName, stationName)) {
			Iterator<Entry<TrainDate, Schedule>> it = trainSystem.schedulesIterator(railwayName, stationName);
			Iterator<Entry<TrainDate, Integer>> it2 = trainSystem.trainNumberIterator(railwayName, stationName);
			while(it.hasNext()) {
				int trainNumber = it2.next().getValue();
				System.out.println(trainNumber);
				Schedule s = it.next().getValue();
				Iterator<Arrival> it3 = trainSystem.getArrivals(s);
				while(it3.hasNext()) {
					Arrival a = it3.next();
					System.out.println(a.getStationName() + " " + a.getFormattedDate());
				}
			}
		}
		}
		catch(InexistentRailwayException e) {
			System.out.println(INEXISTENT_RAILWAY);
		}
		catch(InexistentStartingStationException e) {
			System.out.println(INEXISTENT_STARTING_STATION);
		}
	}

	/**
	 * Determines the best schedule to reach a destination on time.
	 */
	private static void bestSchedule(Scanner in, TrainSystem trainSystem) {

		try {
			String railwayName = in.nextLine().trim();
			String firstStationName = in.nextLine().trim();
			String finalStationName = in.nextLine().trim();
			String time = in.nextLine();
			String[] timeSplit = time.split(":");
			int hour = Integer.parseInt(timeSplit[0]);
			int minute = Integer.parseInt(timeSplit[1]);
			TrainDate date = TrainDateClass.of(hour, minute);

			Iterator<Arrival> it = trainSystem.bestScheduleIterator(railwayName, firstStationName, finalStationName, date);
			System.out.println(trainSystem.bestScheduleTrain(railwayName, firstStationName, finalStationName, date));
			while(it.hasNext()) {
				Arrival a = it.next();
				System.out.println(a.getStationName() + " " + a.getFormattedDate());
			}
		}
		catch(InexistentRailwayException e) {
			System.out.println(INEXISTENT_RAILWAY);
		}
		catch(InexistentStartingStationException e) {
			System.out.println(INEXISTENT_STARTING_STATION);
		}
		catch(ImpossibleRouteException e) {
			System.out.println(IMPOSSIBLE_ROUTE);
		}
	}
	
	/**
	 * Lists all the railways of a given station.
	 */
	private static void listRailways(Scanner in, TrainSystem trainSystem) {

		try {
			String stationName = in.nextLine().trim();
			Iterator<String> it = trainSystem.railwaysIterator(stationName);
			while(it.hasNext()) {
				String railwayName = it.next();
				System.out.println(railwayName);
			}
		}
		catch(InexistentStationException e) {
			System.out.println(INEXISTENT_STATION);
		}	
	}
	
	/**
	 * Lists all the trains of a given station.
	 */
	private static void listTrains(Scanner in, TrainSystem trainSystem) {

		try {
			String stationName = in.nextLine().trim();
			Iterator<Entry<TrainDate, List<Integer>>> it = trainSystem.trainsIterator(stationName);
			while(it.hasNext()) {
				Entry<TrainDate, List<Integer>> e = it.next();
				Iterator<Integer> it2 = e.getValue().iterator();
				while(it2.hasNext()) {
					int ID = it2.next();
					System.out.println("Comboio " + ID + " " + e.getKey().getDate());
				}
			}
		}
		catch(InexistentStationException e) {
			System.out.println(INEXISTENT_STATION);
		}	
	}

	// saves the code in the file.
	private static void save(TrainSystem trainSystem) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE));
			oos.writeObject(trainSystem);
			oos.flush();
			oos.close();
		}
		catch (IOException e) {
		}
	}

	// loads the code from the file.
	private static TrainSystem load() {
		try {
			ObjectInputStream ois =
					new ObjectInputStream(new FileInputStream(DATA_FILE));
			TrainSystem trainSystem = (TrainSystem) ois.readObject();
			ois.close();
			return trainSystem;
		}
		catch (IOException e) {
			return new TrainSystemClass();
		}
		catch (ClassNotFoundException e) {
			return new TrainSystemClass(); 
		}
	}

}
