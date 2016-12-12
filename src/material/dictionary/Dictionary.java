package material.dictionary;

/**
 * An interface for a map which binds a key uniquely to a value.
 *
 * @author R. Cabido, A. Duarte, and J. Velez
 * @param <K> Key class
 * @param <V> Value class
 */
// A simple Map interface
public interface Dictionary<K, V> extends Iterable<Entry<K, V>> {

    /**
     * @return  the number of items in the map. 
     */
    public int size();

    /**
     * @return whether the map is empty.
     */
    public boolean isEmpty();

    /**
     * Adds the value of this
     * entry with the specified value. 
     * @param key
     * @param value
     */
    public void put(K key, V value);

    /**
     * Returns the value of the first entry containing the given key. 
     * Returns null if no such entry exists.
     * @param key used in the search
     * @return The first ocurrence of the entry. 
     */
    public V get(K key);

    /**
     * Returns an iterator which allows to visit all the entries containing the given key.
     * @param key used in the search
     * @return An iterator to the first ocurrence of the entry. 
     */
    public Iterable <V> getAll(K key);

    
    /**
     * If there is an entry with the specified key, removes this entry and
     * returns its value. Else, returns null.
     * @param key used in the search
     * @return removed value
     */
    public V remove(K key);

    /**
     * @return an iterable object containing all the keys in the map.
     */
    public Iterable<K> keys();

    /**
     * @return an iterable object containing all the values in the map.
     */
    public Iterable<V> values();

    /**
     * @return  an iterable object containing all the entries in the map.
     */
    public Iterable<Entry<K, V>> entries();
}
