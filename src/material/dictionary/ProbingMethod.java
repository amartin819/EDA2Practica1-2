package material.dictionary;

/**
 * Probing method used by HashDictionary
 * @param <K> Key
 * @param <V> Value
 * @author jvelez
 */
public interface ProbingMethod <K, V> {
    
    /**
     * Initialize the search of a slot for the key
     * @param key
     * @param hashValue 
     */
    void find(final K key, final int hashValue);    
    
    /**
     * Returns the next slot for the selected key or -1 if there aren't space
     * @return a number in the range [0,buckect.lenght()-1] or -1.
     */
    int nextSlot();

    /**
     * @return true if the current slot has been recycled.
     */
    boolean recycledSlot();

    /**
     * @return true if the current slot is new empty (has null value).
     */
    boolean newSlot();
    
    /**
     * set the bucket object used by the HashDictionary
     * @param bucket the bucket to set
     * @return this
     */    
    public ProbingMethod <K, V> setBucket(Entry<K, V>[] bucket);

    /**
     * set the AVAILABLE object used by the HashDictionary
     * @param AVAILABLE the AVAILABLE object used by the HashDictionary
     * @return this
     */
    public ProbingMethod <K, V> setAVAILABLE(Entry<K, V> AVAILABLE);
    
}
