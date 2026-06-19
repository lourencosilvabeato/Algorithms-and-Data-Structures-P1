package dataStructures;

import java.io.Serializable;

public class OrderedDictionaryClass<K extends Comparable<K>, V> implements OrderedDictionary<K, V> , Serializable {

	private static final long serialVersionUID = 1L;
	private List<Entry<K,V>> entries;
	private int size;

	public OrderedDictionaryClass() {
		this.entries = new DoubleList<>();
		this.size = 0;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public V find(K key) {
		
		Iterator<Entry<K,V>> it = entries.iterator();
		
		while(it.hasNext()) {
			Entry<K,V> e = it.next();
			if(e.getKey().equals(key))
				return e.getValue();
		}
		
		return null;
	}

	public V insert(K key, V value) {
	    Entry<K, V> newEntry = new EntryClass<>(key, value);
	    Iterator<Entry<K, V>> it = entries.iterator();
	    int index = 0;

	   
	    while (it.hasNext()) {
	        Entry<K, V> currentEntry = it.next();

	        if (currentEntry.getKey().equals(key)) {
	            V oldValue = currentEntry.getValue();
	           ((UpdateEntry<K,V>)currentEntry).setValue(value);
	            return oldValue;
	        }

	        if (currentEntry.getKey().compareTo(key) > 0) {
	            break;
	        }

	        index++;
	    }

	    
	    entries.add(index, newEntry);
	    size++;
	    return null; 
	}
	
	public V remove(K key) {
	    Iterator<Entry<K, V>> it = entries.iterator();
	    int index = 0;
	    
	    while (it.hasNext()) {
	        Entry<K, V> entry = it.next();

	        if (entry.getKey().equals(key)) {
	            V oldValue = entry.getValue(); 
	            entries.remove(index);                
	            size--;                      
	            return oldValue;              
	        }
	        index++;
	    }

	    return null; 
	}

	public Iterator<Entry<K, V>> iterator() {
		return entries.iterator();
	}

	public Entry<K, V> minEntry() throws EmptyDictionaryException {
	    if (isEmpty()) {
	        throw new EmptyDictionaryException();
	    }
	    
	    return entries.get(0);
	}

	public Entry<K, V> maxEntry() throws EmptyDictionaryException {
	    if (isEmpty()) {
	        throw new EmptyDictionaryException();
	    }

	    return  entries.get(size-1);
	}


}
