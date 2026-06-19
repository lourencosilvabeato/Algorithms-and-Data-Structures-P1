package railwaySystem;

import java.io.Serializable;

/**
* @author Tomás Sousa (68302) tsm.sousa@campus.fct.unl.pt
* @author Lourenço Beato (68461) lm.beato@campus.fct.unl.pt
*/
public interface TrainDate extends Comparable<TrainDate>, Serializable {

	/**
	 * Get date in a formatted manner.
	 * @return formatted date.
	 */
    String getDate();

    /**
     * Returns the hour part of the date.
     * @return the hour.
     */
    int getHour();

    /**
     * Returns the minute part of the date.
     * @return the minute.
     */
    int getMinute();
    
	/**
	 * Checks a date is after another given date.
     * @param other: the date to compare with.
	 * @return true if is, false otherwise.
	 */
    boolean isAfter(TrainDate other);

    /**
	 * Checks a date is before another given date.
     * @param other: the date to compare with.
	 * @return true if is, false otherwise.
	 */
    boolean isBefore(TrainDate other);

    /**
     * Compares this time to another time
	 * Checks a date is after another given date.
	 * @param other date.
	 * @return -1 if this date is earlier
	 * 			0 if both dates are equal
	 * 			1 if this date is later
	 */
    int compareTo(TrainDate other);

    /**
     * Calculates the difference in minutes between this time and the specified time.
     * @param other: the date to compare with.
     * @return the difference in minutes (positive if the other date is later, negative if earlier).
     */
    int timeDifference(TrainDate other);
    
    /**
     * Calculates the difference in minutes between this time and a specified time.
     * @param time: the time in minutes.
     * @return the difference in minutes.
     */
    int timeDifference(int time);
}