package railwaySystem;

import dataStructures.*;

/**
* @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
* @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
*/
public class ScheduleClass implements Schedule {

	private static final long serialVersionUID = 1L;
	
	private List<Arrival> arrivals;

	public ScheduleClass(List<Arrival> arrivals) {
		this.arrivals = arrivals;
	}
	
	public Iterator<Arrival> getArrivals() {
		return arrivals.iterator();
	}

}
