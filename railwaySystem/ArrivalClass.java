package railwaySystem;


/**
* @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
* @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
*/
public class ArrivalClass implements Arrival {
	
	private static final long serialVersionUID = 1L;

	private Station station;
	private TrainDate date;
	
	//constructor
	public ArrivalClass(Station station, TrainDate date) {
		this.station = station;
		this.date = date;
	}
	
	public Station getStation() {
		return station;
	}
	
	public String getStationName() {
		return station.getName();
	}
	
	public TrainDate getDate() {
		return date;
	}
	
    public String getFormattedDate() {
    	return date.getDate();
    }

}
