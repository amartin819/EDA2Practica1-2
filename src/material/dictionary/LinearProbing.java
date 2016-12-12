package material.dictionary;

/**
 * Linear probing method used by HashDictionary
 * @author jvelez
 * @param <K>
 * @param <V>
 */
public class LinearProbing <K, V> implements ProbingMethod <K, V> {
    private Entry<K, V> [] bucket;
    private Entry<K, V> AVAILABLE;
    private K key;
    private int cicleDetector;
    private int nextSlot;
    private int currentSlot;

    @Override
    public ProbingMethod <K, V> setBucket(Entry<K, V>[] bucket) {
        this.bucket = bucket;
        return this;
    }

    @Override
    public ProbingMethod <K, V> setAVAILABLE(Entry<K, V> AVAILABLE) {
        this.AVAILABLE = AVAILABLE;
        return this;
    }    
    
    @Override
    public void find(final K key, final int hashValue) {
        this.key = key;
        this.cicleDetector = 0;
        this.nextSlot = hashValue;
    }
    
    @Override
    public int nextSlot() {
        while (cicleDetector<bucket.length) {
            Entry<K, V> e = bucket[nextSlot];
            currentSlot = nextSlot;
            nextSlot = (nextSlot + 1) % bucket.length; 
            cicleDetector++;
            if ((e == null) || key.equals(e.getKey()) || (e == AVAILABLE)) {
                return currentSlot;
            }
        }
        
        currentSlot = -1;
        return currentSlot;
    }  
            
    @Override
    public boolean newSlot() {
        return (bucket[currentSlot] == null);
    }

    @Override
    public boolean recycledSlot() {
        return (bucket[currentSlot] == AVAILABLE);
    }
}
