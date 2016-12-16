/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material.dictionary;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jvelez
 */
public class SCHashDictionary  <K,V> implements Dictionary<K,V>{
/*****************************TODOS LOS METODOS CREADOS POR MI**********************************/
    
     /**
     * @param <T> Key type
     * @param <U> Value type
     *
     */
    private class HashEntry<T, U> implements Entry<T, U> {

        protected T key;
        protected U value;

        public HashEntry(T k, U v) {
            key = k;
            value = v;
        }

        @Override
        public U getValue() {
            return value;
        }

        @Override
        public T getKey() {
            return key;
        }

        public U setValue(U val) {
            U oldValue = value;
            value = val;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {

            if (o.getClass() != this.getClass()) {
                return false;
            }

            HashEntry<T, U> ent;
            try {
                ent = (HashEntry<T, U>) o;
            } catch (ClassCastException ex) {
                return false;
            }
            return (ent.getKey().equals(this.key))
                    && (ent.getValue().equals(this.value));
        }

        /**
         * Entry visualization.
         */
        @Override
        public String toString() {
            return "(" + key + "," + value + ")";
        }
    }

    /**
     * @author Juan David Quintana Perez, Daniel Arroyo Cortes
     */
    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {

        private int pos;
        private final HashEntry<T, List<U>>[] bucket;
        private Entry<T, U> AVAILABLE;

        public HashTableMapIterator(HashEntry<T, U>[] b, Entry<T, U> av, int numElems) {
            this.bucket = b; //añadir: para que no falle hasNext al comparar pos < bucket.length
            if (numElems == 0) {
                this.pos = bucket.length;
            } else {
                this.pos = 0;
                goToNextElement(0);
                this.AVAILABLE = av;
            }
        }

        private void goToNextElement(int start) {
            final int n = bucket.length;
            this.pos = start;
            while ((this.pos < n) && ((this.bucket[this.pos] == null) || (this.bucket[this.pos] == this.AVAILABLE))) {
                this.pos++;
            }
        }

        @Override
        public boolean hasNext() {
            return (this.pos < this.bucket.length);
        }

        @Override
        public Entry<T, U> next() {
            if (hasNext()) {
                int currentPos = this.pos;
                goToNextElement(this.pos + 1);
                return (Entry<T, U>) this.bucket[currentPos];
            } else {
                throw new RuntimeException("The map has not more elements");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {

        public HashTableMapIterator<T, U> it;

        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public T next() {
            return it.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {

        public HashTableMapIterator<T, U> it;

        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public U next() {
            return it.next().getValue();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    final private ProbingMethod probingMethod;
    
    private int n = 0; // number of entries in the dictionary
    private int prime, capacity; // prime factor and capacity of bucket array
    private long scale, shift; // the shift and scaling factors
    private HashEntry<K, V> [] bucket;// bucket array
    private final Entry<K, V> AVAILABLE = new HashEntry<>(null, null);

    /**
     * Creates a hash table with prime factor 109345121 and capacity 1000.
     */
    protected SCHashDictionary() {
        this(1000, new LinearProbing()); // reusing the constructor HashTableMap(int cap)
    }

    /**
     * Creates a hash table with prime factor 109345121 and capacity 1000.
     */
    protected SCHashDictionary(int cap) {
        this(cap, new LinearProbing()); // reusing the constructor HashTableMap(int cap)
    }

    /**
     * Creates a hash table with prime factor 109345121 and capacity 1000.
     * @param probingMethod
     */
    protected SCHashDictionary(ProbingMethod probingMethod) {
        this(1000, probingMethod);
    }
        
    /**
     * Creates a hash table with the given prime factor and capacity.
     * @param cap initial capacity
     * @param probingMethod
     */
    protected SCHashDictionary(int cap,ProbingMethod probingMethod) {
        this.n = 0;
        this.prime = 109345121;
        this.capacity = cap;
        this.bucket = (HashEntry<K, V>[]) new HashEntry[capacity]; // safe cast
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
        this.probingMethod = probingMethod;
        probingMethod.setAVAILABLE(AVAILABLE).setBucket(bucket);
    }
    
    /********************************************************************************************
     * ******************************************************************************************
     * ***************HASTA AQUI COPIADO Y PEGADO DEL OAHASHDICTIONARY***************************
     * ******************************************************************************************
     * ******************************************************************************************
     */
    
    
    
    @Override
    public int size() {
        return n;
    }

    @Override
    public boolean isEmpty() {
        return n==0;
    }

    @Override
    public void put(K key, V value) {
        probingMethod.find(key, hashValue(key));
        int i;
        
        do{
            i= probingMethod.nextSlot();
            if(i == -1){
                rehash();
                probingMethod.find(key, hashValue(key));
                i= probingMethod.nextSlot();
            }
        }while(!probingMethod.newSlot() || probingMethod.recycledSlot());
        
        if(bucket[i].equals(null)){
            List<Entry<K,V>> b = new LinkedList<>();
            bucket[i] = new HashEntry<>(key, value); // convert to proper entry
            b.add(bucket[i]);
        }else{
            List<Entry<K,V>> b = new LinkedList<>();
            bucket[i] = new HashEntry<>(key, value); // convert to proper entry            
            b.remove(key);
            b.add(bucket[i]);
        }
        n++;
    }

    @Override
    public V get(K key) {
        probingMethod.find(key, hashValue(key));
        int i = probingMethod.nextSlot();
        
        if((i == -1) || probingMethod.newSlot())
            return null; //there is not value for this key
        
        return bucket[i].getValue();
    }

    @Override
    public Iterable<V> getAll(K key) {
        probingMethod.find(key, hashValue(key));
        int i = probingMethod.nextSlot();
        List<V> l = new LinkedList<>();
        while(!probingMethod.newSlot()){
            l.add(bucket[i].getValue());
            i = probingMethod.nextSlot();
        }
        return l;
    }

    @Override
    public V remove(K key) {
        probingMethod.find(key, hashValue(key));
        int i = probingMethod.nextSlot();
        
        if(probingMethod.newSlot())
            return null; //nothing to remove
        
        V toReturn = bucket[i].getValue();
        bucket[i] = (HashEntry<K, V>) AVAILABLE;
        n--;
        return toReturn;
    }

    @Override
    public Iterable<K> keys() {
        return new Iterable<K>(){
            @Override
            public Iterator<K> iterator(){
                return new HashTableMapKeyIterator<>(new HashTableMapIterator<>(bucket, AVAILABLE, n));
            }
        };
    }

    @Override
    public Iterable<V> values() {
        return new Iterable<V>(){
            @Override
            public Iterator<V> iterator(){
                return new HashTableMapValueIterator<>(new HashTableMapIterator<>(bucket,AVAILABLE,n));
            }
        };
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        return new Iterable<Entry<K,V>>(){
            @Override
            public Iterator<Entry<K,V>> iterator(){
                return new HashTableMapIterator<>(bucket,AVAILABLE,n);
            }
        };
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableMapIterator<>(this.bucket, this.AVAILABLE, this.n);
    }
    
    protected int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }
    
    protected void rehash() {
        //reservar un array del doble del tamaño
        //guardamos el antiguo un rato para copiarlo
        //recorrer con un iterador las entradas de mi array
        //recorrer las entradas de mi bucket antiguo
        //insertar en el nuevo bucket las entradas
        
        HashEntry<K,V>[] oldBucket = bucket;
        capacity*=2;
        this.bucket = (HashEntry<K,V>[]) new HashEntry[capacity];
        this.n = 0;
        probingMethod.setAVAILABLE(AVAILABLE).setBucket(bucket);
        
        for (int i=0; i<oldBucket.length; i++){
            if((oldBucket[i] != null) && (oldBucket[i] != AVAILABLE))
                put(oldBucket[i].getKey(), oldBucket[i].getValue());
        }
        
    }
    
}
