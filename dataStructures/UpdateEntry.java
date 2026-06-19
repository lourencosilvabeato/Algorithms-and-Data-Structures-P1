package dataStructures;

public interface UpdateEntry<K,V> extends Entry<K, V> {

	/**
	 * Sets the value of an entry.
	 * @param value to set
	 */
	void setValue(V value);
}
