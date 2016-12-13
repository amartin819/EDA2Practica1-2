/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material.dictionary;

/**
 *
 * @author jvelez
 */
public class DoubleHashProbing <K,V>  implements ProbingMethod<K,V>{
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
    /*NO CREO QUE ESTE BIEN PREGUNTAR A CRISTIAN*/
    public int nextSlot() {
        int p=0;
        int q = 3;
        int d = q - (nextSlot % q);
        
        while (cicleDetector<bucket.length) {
            Entry<K, V> e = bucket[nextSlot];
            currentSlot = nextSlot;
            nextSlot = (nextSlot +d*p) % bucket.length; 
            p++;
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
