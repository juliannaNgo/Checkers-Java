//Entry.java by Julianna Ngo
//Last updated Dec 26, 2019
import java.util.Map;


public class Entry<K, V> implements Map.Entry<K, V> {

	/**
	 * This entry's key
	 */
	private final K key;
	
	/**
	 * This entry's value
	 */
	private V val;
	
	/**
	 * Constructs an empty Entry
	 * @param key the entry's assigned key, value the entry's assigned value
	 */
	public Entry(K key, V value){
		this.key = key;
		this.val = value;
	}
	
	public Entry() {
		this.key = null;
		this.val = null;
	}
	
	/**
	 * Returns this entry's key
	 */
	@Override
	public K getKey() {
		return this.key;
	}

	/**
	 * Returns this entry's value
	 */
	@Override
	public V getValue() {
		return this.val;
	}

	/**
	 * Updates this entry's value and returns the previous value
	 * @param value the new value
	 */
	@Override
	public V setValue(V value) {
		V old = this.val;
		this.val = value;
		return old;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entry other = (Entry) obj;
		if (this.key != other.key)
			return false;
		if (this.val != other.val)
			return false;
		return true;
	}
	
	public String toString() {
		return "("+this.key+", "+this.val+")";
	}

}
