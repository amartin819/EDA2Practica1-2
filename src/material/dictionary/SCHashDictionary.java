/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material.dictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jvelez
 */
public class SCHashDictionary<K,V> implements Dictionary<K,V>{
    
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
        private final HashEntry<T, LinkedList<HashEntry<T,U>>>[] bucket;

        public HashTableMapIterator(HashEntry<T, LinkedList<HashEntry<T,U>>>[] b, int numElems) {
            this.bucket = b; //añadir: para que no falle hasNext al comparar pos < bucket.length
            if (numElems == 0) {
                this.pos = bucket.length;
            } else {
                this.pos = 0;
                goToNextElement(0);
            }
        }

        private void goToNextElement(int start) {
            final int n = bucket.length;
            this.pos = start;
            while ((this.pos < n) && ((this.bucket[this.pos] == null))) {
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
               
                for(Entry<T,U> e : this.bucket[currentPos].value){
                    return e;
                }
            }
            throw new RuntimeException("The map has not more elements");
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
    
    private int n = 0; // number of entries in the dictionary
    private int capacity; // prime factor and capacity of bucket array
    private final HashEntry<K, LinkedList<HashEntry<K,V>>> [] bucket;// bucket array
        
    /**
     * Creates a hash table with the given prime factor and capacity.
     * @param cap initial capacity
     */
    protected SCHashDictionary(int cap) {
        this.n = 0;
        this.capacity = cap;
        this.bucket =  (HashEntry < K, LinkedList<HashEntry<K, V>>>[]) new HashEntry[capacity]; // safe cast
    }
    
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
        int i = this.hashValue(key);
        HashEntry<K,V> val = null;
        val.key = key;
        val.setValue(value);
        List<HashEntry<K,V>> v;
        
        if(bucket[i].equals(null)){
            v = new ArrayList<>();
            this.bucket[i] = (HashEntry<K, LinkedList<HashEntry<K, V>>>) v; // convert to proper entry
            n++;
        }else{
            v = bucket[i].value;           
            v.remove(key);
        }
        v.add(val);
    }

    @Override
    public V get(K key) {
        int i = this.hashValue(key);
        return (V) bucket[i].getValue();
    }

    @Override
    public Iterable<V> getAll(K key) {
        int i = this.hashValue(key);
        List<V> l = new LinkedList<>();
        
        for(HashEntry<K,V> e: bucket[i].value){
            if(e.key == key)
                l.add(e.value);
        }
        return l;
    }

    @Override
    public V remove(K key) {
        int i = this.hashValue(key);
        
        if(this.bucket[i]==null)
            return null; //nothing to remove
        
        V toReturn = (V) bucket[i].getValue();
        bucket[i].setValue(null);
        return toReturn;
    }

    @Override
    public Iterable<K> keys() {
        return new Iterable<K>(){
            @Override
            public Iterator<K> iterator(){
                return new HashTableMapKeyIterator<>(new HashTableMapIterator<>(bucket, n));
            }
        };
    }

    @Override
    public Iterable<V> values() {
        return new Iterable<V>(){
            @Override
            public Iterator<V> iterator(){
                return new HashTableMapValueIterator<>(new HashTableMapIterator<>(bucket, n));
            }
        };
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        return new Iterable<Entry<K,V>>(){
            @Override
            public Iterator<Entry<K,V>> iterator(){
                return new HashTableMapIterator<>(bucket,n);
            }
        };
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableMapIterator<>(this.bucket, this.n);
    }
    
    protected int hashValue(K key) {
        int prime = 109345121;
        return (int) (Math.abs(key.hashCode() % prime) % capacity);
    }
    
    /*protected void rehash() {
        //reservar un array del doble del tamaño
        //guardamos el antiguo un rato para copiarlo
        //recorrer con un iterador las entradas de mi array
        //recorrer las entradas de mi bucket antiguo
        //insertar en el nuevo bucket las entradas
        
        HashEntry<K,List<HashEntry<K,V>>>[] oldBucket = bucket;
        capacity*=2;
        this.bucket = (HashEntry<K,List<HashEntry<K,V>>>[]) new HashEntry<>[capacity];
        this.n = 0;
        
        for (int i=0; i<oldBucket.length; i++){
            if((oldBucket[i] != null))
                put(oldBucket[i].getKey(), oldBucket[i].value.get(i).getValue());
        }
        
    }*/
    
}
