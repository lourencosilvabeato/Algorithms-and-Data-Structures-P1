package railwaySystem;


/**
* @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
* @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
*/
public class TrainDateClass implements TrainDate, Comparable<TrainDate> {

	private static final long serialVersionUID = 1L;
	
	private int hour;
    private int minute;

    //constructor
    public TrainDateClass(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public String getDate() {
        return String.format("%02d:%02d", hour, minute);
    }

    /**
     * Creates a new TrainDateClass with a given hour and minute.
     * @param hour: the hour part of the date.
     * @param minute: minute part of the date.
     * @return a new instance of TrainDate.
     */
    public static TrainDate of(int hour, int minute) {
        return new TrainDateClass(hour, minute);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isAfter(TrainDate other) {
        return this.compareTo(other) > 0;
    }

    public boolean isBefore(TrainDate other) {
        return this.compareTo(other) < 0;
    }
    
    public int compareTo(TrainDate other) {
        if (this.getHour() != other.getHour())
            return Integer.compare(this.getHour(), other.getHour());
        return Integer.compare(this.getMinute(), other.getMinute());
    }

    public int timeDifference(TrainDate other) {
        int mins1 = this.hour * 60 + this.minute;
        int mins2 = other.getHour() * 60 + other.getMinute();
        return mins2 - mins1;
    }
    
    public int timeDifference(int time) {
        int mins1 = this.hour * 60 + this.minute;
        return time - mins1;
    }

}